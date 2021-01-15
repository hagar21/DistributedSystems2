package server;

import client.CityClient;
import generated.*;
import io.grpc.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import ZkService.ZkServiceImpl;
import ZkService.utils.ClusterInfo;
import io.grpc.stub.StreamObserver;
import ZkService.Listeners.LeaderChangeListener;
import ZkService.Listeners.LiveNodeChangeListener;

import static ZkService.ZkService.ELECTION_NODE;
import static ZkService.ZkService.LIVE_NODES;
import static ZkService.utils.Host.getHostPostOfServer;

public class CityServer extends UberServiceGrpc.UberServiceImplBase {
    private static final Logger logger = Logger.getLogger(CityServer.class.getName());

    // connection info
    private final Server server;
    private ZkServiceImpl zkService;
    public Boolean service_up;
    private String shardName;
    private String HostName;
    public static String[] shardNames = { "shardA", "shardB", "shardC" };
    private LbClient lb;
    private ConcurrentMap<Integer, CityClient> shardMembers;
    private CityClient leader;

    // db
    private final ConcurrentMap<String, Ride> rides =
            new ConcurrentHashMap<>();
    private final ConcurrentMap<String, CustomerRequest> customerRequests =
            new ConcurrentHashMap<>();

    /**
     * Create a Uber server using serverBuilder as a base and features as data.
     */
    public CityServer(String port, String hostList, String shardName) {
        this.service_up = false;
        this.shardName = shardName;

        this.server = ServerBuilder.forPort(Integer.parseInt(port))
                .addService(this)
                .build();
        // HostName = HostIP.getIp()+":" + port;
        ConnectToZk(hostList);
    }

    public void ConnectToZk(String hostList) {
        try {

            this.zkService = new ZkServiceImpl(hostList);

            // create all parent nodes /election, /all_nodes, /live_nodes
            // Shai - not sure we need to create root
            // zkService.createAllParentNodes();

            // create all parent nodes /election/city, /all_nodes/city, /live_nodes/city
            zkService.createAllParentNodes(shardName);

            /*
            // add this server to cluster by creating znode under /all_nodes, with name as "host:port"
            zkService.addToAllNodes(getHostPostOfServer(), "cluster node");
            ClusterInfo.getClusterInfo().getAllNodes().clear();
            ClusterInfo.getClusterInfo().getAllNodes().addAll(zkService.getAllNodes());

            // check which leader election algorithm(1 or 2) need is used
            String leaderElectionAlgo = System.getProperty("leader.algo");
            */

            // create ephemeral sequential znode in /election/city
            // then get children of  /election/city and fetch least sequenced znode, among children znodes
            System.out.println("ConnectToZk createNodeInElectionZnode");
            zkService.createNodeInElectionZnode(getHostPostOfServer(), shardName);
            ClusterInfo.getClusterInfo().setLeader(zkService.getLeaderNodeData(shardName));

            // If will will support server coming back to life
            // syncDataFromLeader();

            // add child znode under /live_node, to tell other servers that this server is ready to serve
            // read request
            System.out.println("ConnectToZk addToLiveNodes");
            zkService.addToLiveNodes(getHostPostOfServer(), "I am alive", shardName);
            System.out.println("Shai check ip host wasn't null");
            ClusterInfo.getClusterInfo().getLiveNodes().clear();
            ClusterInfo.getClusterInfo().getLiveNodes().addAll(zkService.getLiveNodes(shardName));

            // register watchers for leader change, live nodes change, all nodes change and zk session
            // state change
            zkService.registerChildrenChangeListener(ELECTION_NODE + "/" + shardName, new LeaderChangeListener());
            zkService.registerChildrenChangeListener(LIVE_NODES + "/" + shardName, new LiveNodeChangeListener());

            System.out.println("Finished ConnectToZk for city " + shardName + " host " + getHostPostOfServer());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Startup failed!", e);
        }
    }

    public void setLeader() {
        String target = ClusterInfo.getClusterInfo().getLeader();
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        this.leader = new CityClient(channel);
    }

    /**
     * Start serving requests.
     */
    public void start() throws IOException {
        try {
            server.start();
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("Server started, listening on " + HostName);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    CityServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    /**
     * Stop serving requests and shutdown resources.
     */
    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    @Override
    public void postRide(Ride ride, StreamObserver<Result> responseObserver) {
        System.out.println("City server got post ride request");
        System.out.println("-------------");

        Result.Builder res = Result.newBuilder();
        if (ride.getLeaderSent()) {
            rides.put(ride.getId(), ride);

            // Send ack to leader
            responseObserver.onNext(res.setIsSuccess(true).build());

        } else {
            String rideId = ride.getFirstName() + ride.getLastName() + ride.getDate() + ride.getSrcCity() + ride.getDstCity();

            if(rides.containsKey(rideId)) {
                responseObserver.onNext(res.setIsSuccess(false).build());
                responseObserver.onCompleted();
                return;
            }

            Ride updatedRide = Ride.newBuilder(ride).setId(rideId).build();
            if(ride2PhaseCommit(updatedRide)) {
                responseObserver.onNext(res.setIsSuccess(true).build());
            } else {
                responseObserver.onNext(res.setIsSuccess(false).build());
            }
        }
        responseObserver.onCompleted();


    }

    @Override
    public void postCustomerRequest(CustomerRequest request, StreamObserver<Result> responseObserver) {
        System.out.println("City server got post customer request");
        System.out.println("-------------");

        if(request.getLeaderSent()) {
            customerRequests.put(request.getId(), request);
        }

        Result res = Result.newBuilder().setIsSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void postPathPlanningRequest(
            CustomerRequest request, StreamObserver<Ride> responseObserver) {
        System.out.println("City server got postPathPlanningRequest request");
        System.out.println("-------------");
        // map of (ride: shardName)
        Map<Ride, String> reservedRides = new HashMap<>();
        String src, dst;
        Rout.Builder routBuilder = Rout.newBuilder()
                .setName(request.getName())
                .setDate(request.getDate());
        Rout rout;

        for(int i = 0; i < request.getPathCount() - 1; i++) {
            src = request.getPath(i);
            dst = request.getPath(i + 1);

            rout = routBuilder
                    .setSrcCity(src)
                    .setDstCity(dst).build();

            // check for relevant rides in local db
            Ride foundRide = getLocalMatchingRide(rout);
            if (!foundRide.equals(noRide())) {
                reservedRides.put(foundRide, this.shardName);
                continue;
            }

            // check in other shards
            ShardRide remoteRide = getRemoteMatchingRide(rout);

            if(remoteRide.ride.equals(noRide())) {
                revertPath(reservedRides);
                responseObserver.onNext(noRide());
                responseObserver.onCompleted();
                return;
            }
            reservedRides.put(remoteRide.ride, remoteRide.shardName);
        }

        // Entire path satisfied
        String id = request.getName() + request.getPathList().toString() + request.getDate();
        CustomerRequest updatedRequest = CustomerRequest.newBuilder(request).addAllRides(reservedRides.keySet()).setId(id).build();
        if(customerRequest2PhaseCommit(updatedRequest)) {
            customerRequests.put(updatedRequest.getId(), updatedRequest);
            for (Ride ride : reservedRides.keySet()) {
                responseObserver.onNext(ride);
            }
        }

        responseObserver.onCompleted();
    }

    @Override
    public void reserveRide(Rout request, StreamObserver<Ride> responseObserver){
        Ride ride = getLocalMatchingRide(request);
        responseObserver.onNext(ride);
        responseObserver.onCompleted();
    }

    @Override
    public void revertCommit(Ride request, StreamObserver<Result> responseObserver) {
        revertLocalCommit(request);
        Result res = Result.newBuilder().setIsSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void snapshot(com.google.protobuf.Empty request,
                         StreamObserver<com.google.protobuf.Empty> responseObserver) {

        System.out.println("Snapshot of published services:");
        System.out.println("--------------------------------");

        for (Ride ride : rides.values()) {
            printRide(ride);
        }

        System.out.println("Snapshot of requested services:");
        System.out.println("--------------------------------");

        for (CustomerRequest req : customerRequests.values()) {
            printCustomerRequest(req);
        }
        responseObserver.onCompleted();
    }

    private Boolean isNodeLeader(){
        return zkService.getLeaderNodeData(shardName).equals(this.HostName);
    }

    private Point mapCityToLocation(String city) {
        switch(city) {
            case "haifa":
                return new Point(0,0);
            case "karkur":
                return new Point(0,1);
            case "monash":
                return new Point(1,0);
            default:
                return new Point(1,1);
        }
    }

    private boolean isMatch(Ride ride, Rout rout) {
        if(!ride.getDate().equals(rout.getDate())) return false;
        if(ride.getOfferedPlaces() == ride.getTakenPlaces()) return false;
        if(customerAlreadyInRide(ride, rout.getName())) return false;

        Point rideSrc = mapCityToLocation(ride.getSrcCity());
        Point rideDst = mapCityToLocation(ride.getDstCity());
        Point reqSrc = mapCityToLocation(rout.getSrcCity());
        Point reqDst = mapCityToLocation(rout.getDstCity());

        Point p0;
        if(rideSrc.equals(reqSrc)) {
            p0 = mapCityToLocation(rout.getDstCity());
        } else if (rideDst.equals(reqDst)) {
            p0 = mapCityToLocation(rout.getSrcCity());
        } else {
            return false;
        }

        int numerator = Math.abs((rideDst.x - rideSrc.x) * (rideSrc.y - p0.y) - (rideSrc.x-p0.x) * (rideDst.y - rideSrc.y));
        double denominator = Math.sqrt(Math.pow(rideDst.x - rideSrc.x, 2)+ Math.pow(rideDst.y - rideSrc.y, 2));
        return (numerator / denominator) <= ride.getPd();
    }

    private boolean customerAlreadyInRide(Ride ride, String name) {
        for(int i = 0; i < ride.getCustomersCount(); i++) {
            if(name.equals(ride.getCustomers(i))) return true;
        }
        return false;
    }

    public static Ride noRide() {
        return Ride.newBuilder().setId("noRide").setFirstName("no").setLastName("ride").build();
    }

    private List<CityClient> initShards(int port) {
        List<CityClient> shards = new ArrayList<>();
//        for(int i = 0; i < shardsNumber; i++) {
//            String target = "localhost:" + (8980 + i);
//            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
//            CityClient client = new CityClient(channel);
//
//            shards.add(client);
//        }

        if (port == 8990) {
            String target = "localhost:8991";
            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
            CityClient client = new CityClient(channel);

            shards.add(client);

            String target1 = "localhost:8992";
            ManagedChannel channel1 = ManagedChannelBuilder.forTarget(target1).usePlaintext().build();
            CityClient client1 = new CityClient(channel1);

            shards.add(client1);
        }

        if (port == 8991) {
            String target = "localhost:8990";
            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
            CityClient client = new CityClient(channel);

            shards.add(client);

            String target1 = "localhost:8992";
            ManagedChannel channel1 = ManagedChannelBuilder.forTarget(target1).usePlaintext().build();
            CityClient client1 = new CityClient(channel1);

            shards.add(client1);
        }

        if (port == 8992) {
            String target = "localhost:8990";
            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
            CityClient client = new CityClient(channel);

            shards.add(client);

            String target1 = "localhost:8991";
            ManagedChannel channel1 = ManagedChannelBuilder.forTarget(target1).usePlaintext().build();
            CityClient client1 = new CityClient(channel1);

            shards.add(client1);
        }
        return shards;
    }

    public boolean ride2PhaseCommit(Ride ride) {

        if(isNodeLeader()) {
            // This node is the leader

            Date date = new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);

            Ride.Builder rideBuilder = Ride.newBuilder(ride)
                    .setLeaderSent(true)
                    .setTime(ts.toString());

            Ride existingRide = rides.get(ride.getId());
            if ( existingRide != null && existingRide.getTakenPlaces() == ride.getTakenPlaces() ) {
                // Trying to update a ride to an old value

                if (existingRide.getTakenPlaces() == existingRide.getOfferedPlaces()) {
                    // Last spot in the ride was already taken
                    return false;
                }

                rideBuilder
                        .setTakenPlaces(existingRide.getTakenPlaces() + 1)
                        .addAllCustomers(ride.getCustomersList())
                        .addCustomers(ride.getCustomers(ride.getCustomersCount() - 1));
            }

            int acceptedCounter = 0;
            shardMembers = updateShardMembers();
            Ride updatedRide = rideBuilder.build();

            for(CityClient shard : shardMembers.values()) {
                boolean isSuccess = shard.postRide(updatedRide);
                if (isSuccess) {
                    acceptedCounter++;
                }
            }

            if(acceptedCounter < shardMembers.size()) {
                // rollback
            }
        } else {
            // This node is not the leader

            setLeader();
            return leader.postRide(ride);
        }
        return false;
    }

    public boolean customerRequest2PhaseCommit(CustomerRequest request) {
        if(isNodeLeader()) {
            // This node is the leader

            int acceptedCounter = 0;
            Date date = new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);

            CustomerRequest updatedRequest = CustomerRequest.newBuilder(request)
                    .setLeaderSent(true)
                    .setTime(ts.toString())
                    .build();

            shardMembers = updateShardMembers();

            for(CityClient shard : shardMembers.values()) {
                boolean isSuccess = shard.postCustomerRequest(updatedRequest);
                if (isSuccess) {
                    acceptedCounter++;
                }
            }

            if(acceptedCounter < shardMembers.size()) {
                // rollback
            }
        } else {
            // This node is not the leader

            setLeader();
            return leader.postCustomerRequest(request);
        }
        return false;
    }

    private ConcurrentHashMap<Integer, CityClient> updateShardMembers() {

        List<String> liveNodes = ClusterInfo.getClusterInfo().getLiveNodes();
        liveNodes.remove(HostName);

        ConcurrentHashMap<Integer, CityClient> shards = new ConcurrentHashMap<>();

        // get new members connections
        int i = 0;
        for (String targetHost : liveNodes) {
            ManagedChannel channel = ManagedChannelBuilder.forTarget(targetHost).usePlaintext().build();
            CityClient client = new CityClient(channel);
            shards.put(i++, client);
        }
        // delete all previous stubs
        shardMembers.clear();
        return shards;
    }

    private Ride getLocalMatchingRide(Rout rout) {
        for (Ride ride : this.rides.values()) {
            if (isMatch(ride, rout)) {

                Ride updatedRide = Ride.newBuilder(ride)
                        .setTakenPlaces(ride.getTakenPlaces() + 1)
                        .addCustomers(rout.getName()).build();
                if (ride2PhaseCommit(updatedRide)) {
                    return updatedRide;
                }
            }
        }
        return noRide();
    }

    private ShardRide getRemoteMatchingRide(Rout rout) {
        for(String shard : shardNames){
            Ride ride = lb.reserveRide(shard, rout);
            if(!ride.equals(noRide())) {
                return new ShardRide(shard, ride);
            }

        }
        return new ShardRide("", noRide());
    }

    private void revertLocalCommit(Ride ride) {
        List<String> customers = ride.getCustomersList();
        customers.remove(customers.size() - 1);
        
        Ride updatedRide = Ride.newBuilder(ride)
                .setTakenPlaces(ride.getTakenPlaces() - 1)
                .addAllCustomers(customers)
                .build();
        if(ride2PhaseCommit(updatedRide)) {
            this.rides.put(updatedRide.getId(), updatedRide);
        }
    }

    private void revertPath(Map<Ride, String> reservedRides){
        for (Map.Entry<Ride, String> entry : reservedRides.entrySet()) {
            if (entry.getValue().equals(this.shardName)) {
                revertLocalCommit(entry.getKey());
            } else {
                lb.revertCommit(entry.getValue(), entry.getKey());
            }
        }
    }

    private void printRide(Ride ride){
        System.out.println("Name: " + ride.getFirstName() + " " + ride.getLastName());
        System.out.println("Phone number: " + ride.getPhoneNum());
        System.out.println("Date: " + ride.getDate());
        System.out.println("Path: " + ride.getSrcCity() + " to " + ride.getDstCity());
        System.out.println("Offered places: " + ride.getOfferedPlaces());
        System.out.println("Taken places: " + ride.getTakenPlaces());
        System.out.println("-------------");
    }

    private void printCustomerRequest(CustomerRequest req){
        System.out.println("Name: " + req.getName());
        System.out.print("Path: ");
        for (String path : req.getPathList()) {
            System.out.print(" " + path);
        }

        System.out.println("Date: " + req.getDate());

        if(req.getRidesCount() > 0) {
            System.out.println("The request is satisfied by:");
            for(Ride ride : req.getRidesList()) {
                System.out.println(ride.getFirstName() + " " + ride.getLastName());
            }
        } else {
            System.out.println("The request couldn't be satisfied");
        }

        System.out.println("-------------");
    }

}