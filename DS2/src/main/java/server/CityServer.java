package server;

import client.CityClient;
import generated.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import ZkService.ZkServiceImpl;
import ZkService.utils.ClusterInfo;
import io.grpc.stub.StreamObserver;
import org.I0Itec.zkclient.IZkChildListener;
import ZkService.Listeners.LeaderChangeListener;
import ZkService.Listeners.LiveNodeChangeListener;

import static ZkService.ZkService.ELECTION_NODE;
import static ZkService.ZkService.LIVE_NODES;
import static ZkService.utils.Host.getHostPostOfServer;
import static server.CityUtil.*;
import static server.CityUtil.getLocalMatchingRide;

public class CityServer extends UberServiceGrpc.UberServiceImplBase {
    private static final Logger logger = Logger.getLogger(CityServer.class.getName());

    // connection info
    private final Server server;
    private ZkServiceImpl zkService;
    public Boolean service_up;
    private String shardName;
    private String HostName;
    private final List<CityClient> shards;

    // db
    private final ConcurrentMap<String, Ride> rides =
            new ConcurrentHashMap<>();
    private final ConcurrentMap<String, CustomerRequest> customerRequests =
            new ConcurrentHashMap<>();

//    public CityServer(int port) throws IOException {
//        this(ServerBuilder.forPort(port), port);
//    }

    /**
     * Create a Uber server using serverBuilder as a base and features as data.
     */
    public CityServer(String port, String hostList, String shardName) {
        this.service_up = false;
        shards = new ArrayList<CityClient>();
        this.shardName = shardName;

        this.server = ServerBuilder.forPort(Integer.parseInt(port))
                .addService(this)
                .build();
//        HostName = HostIP.getIp()+":" + port;
        // ConnectToZk(hostList);
    }

    public void ConnectToZk(String hostList) {
        try {

            this.zkService = new ZkServiceImpl(hostList);

            // create all parent nodes /election, /all_nodes, /live_nodes
            // Shai - not sure we need to create root
            // zkService.createAllParentNodes();

            // create all parent nodes /election/city, /all_nodes/city, /live_nodes/city
            zkService.createAllParentNodes(city);

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
            zkService.createNodeInElectionZnode(getHostPostOfServer(), city);
            ClusterInfo.getClusterInfo().setLeader(zkService.getLeaderNodeData(city));

            // If will will support server coming back to life
            // syncDataFromLeader();

            // add child znode under /live_node, to tell other servers that this server is ready to serve
            // read request
            System.out.println("ConnectToZk addToLiveNodes");
            zkService.addToLiveNodes(getHostPostOfServer(), "I am alive", city);
            System.out.println("Shai check ip host wasn't null");
            ClusterInfo.getClusterInfo().getLiveNodes().clear();
            ClusterInfo.getClusterInfo().getLiveNodes().addAll(zkService.getLiveNodes(city));

            // register watchers for leader change, live nodes change, all nodes change and zk session
            // state change
            zkService.registerChildrenChangeListener(ELECTION_NODE + "/" + city, new LeaderChangeListener());
            zkService.registerChildrenChangeListener(LIVE_NODES + "/" + city, new LiveNodeChangeListener());

            System.out.println("Finished ConnectToZk for city " + city + " host " + getHostPostOfServer());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Startup failed!", e);
        }
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
        logger.info("Server started, listening on " + port);
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
    public void postRide(Ride request, StreamObserver<Result> responseObserver) {
        System.out.println("City server got post ride request");
        System.out.println("-------------");

        String rideId = request.getFirstName() + request.getLastName() + request.getDate() + request.getSrcCity() + request.getDstCity();
        System.out.println(rideId);

        Result.Builder res = Result.newBuilder();
        if(rides.containsKey(rideId)) {
            responseObserver.onNext(res.setIsSuccess(false).build());
            responseObserver.onCompleted();
        }

        Ride ride = Ride.newBuilder(request).setId(rideId).build();
        if(rideConsensus(ride)) {
            rides.put(ride.getId(), ride);
        }
        responseObserver.onNext(res.setIsSuccess(true).build());
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
            ShardRide remoteRide = getRemoteMatchingRide(shards, rout);

            if(remoteRide.ride.equals(noRide())) {
                revertPath(reservedRides);
                responseObserver.onNext(noRide());
                responseObserver.onCompleted();
                return;
            }
            reservedRides.put(remoteRide.ride, remoteRide.shardId);
        }

        // Entire path satisfied
        String id = request.getName() + request.getPathList().toString() + request.getDate();
        CustomerRequest updatedRequest = CustomerRequest.newBuilder(request).addAllRides(reservedRides.keySet()).setId(id).build();
        if(customerConsensus(updatedRequest)) {
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

    public Ride cityRequestRide(CityRequest cityRequest) {

        // test if with have a ride to go with it - only locally
        return getLocalMatchingRide(cityRequest.getRout());
    }

    private Boolean isNodeLeader(){
        return zkService.getLeaderNodeData(shardName).equals(this.HostName);
    }

    public static Point mapCityToLocation(String city) {
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

    public static boolean isMatch(Ride ride, Rout rout) {
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

    private static boolean customerAlreadyInRide(Ride ride, String name) {
        for(int i = 0; i < ride.getCustomersCount(); i++) {
            if(name.equals(ride.getCustomers(i))) return true;
        }
        return false;
    }

    public static Ride noRide() {
        return Ride.newBuilder().setId("noRide").setFirstName("no").setLastName("ride").build();
    }

    public static List<CityClient> initShards(int port) {
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

    public static boolean rideConsensus(Ride ride) { return true; }
    public static boolean customerConsensus(CustomerRequest req) { return true; }

    private Ride getLocalMatchingRide(Rout rout) {
        for (Ride ride : this.rides.values()) {
            if (isMatch(ride, rout) && rideConsensus(ride)) {
                {
                    Ride updatedRide = Ride.newBuilder(ride)
                            .setTakenPlaces(ride.getTakenPlaces() + 1)
                            .addCustomers(rout.getName()).build();
                    this.rides.put(updatedRide.getId(), updatedRide);
                    return updatedRide;
                }
            }
        }
        return noRide();
    }

    public static ShardRide getRemoteMatchingRide(List<CityClient> shards, Rout rout) {
        int index = 0;
        for(CityClient client : shards){
            /*
            Ride ride = client.reserveRide(rout);
            if(!ride.equals(noRide())) {
                return new ShardRide(index, ride);
            }
            index++;
            Shai - send request to LB
             */
        }
        return new ShardRide(-1, noRide());
    }

    public void revertLocalCommit(Ride ride) {
        Ride updatedRide = Ride.newBuilder(ride).setTakenPlaces(ride.getTakenPlaces() - 1).build();
        if(rideConsensus(updatedRide)) {
            this.rides.put(updatedRide.getId(), updatedRide);
        }
    }

    private void revertPath(Map<Ride, String> reservedRides){
        for (Map.Entry<Ride, String> entry : reservedRides.entrySet()) {
            if (entry.getValue() == this.shardName) {
                revertLocalCommit(entry.getKey());
            } else {
                shards.get(entry.getValue()).revertCommit(entry.getKey());
            }
        }
    }

    public static void printRide(Ride ride){
        System.out.println("Name: " + ride.getFirstName() + " " + ride.getLastName());
        System.out.println("Phone number: " + ride.getPhoneNum());
        System.out.println("Date: " + ride.getDate());
        System.out.println("Path: " + ride.getSrcCity() + " to " + ride.getDstCity());
        System.out.println("Offered places: " + ride.getOfferedPlaces());
        System.out.println("Taken places: " + ride.getTakenPlaces());
        System.out.println("-------------");
    }

    public static void printCustomerRequest(CustomerRequest req){
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