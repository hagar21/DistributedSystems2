package server;

import ch.qos.logback.classic.Level;
import client.ShardClient;
import client.LbClient;
import generated.*;
import io.grpc.*;

import java.io.IOException;
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
import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;
import server.utils.*;
import server.utils.Point;

import static ZkService.ZkService.ELECTION_NODE;
import static ZkService.ZkService.LIVE_NODES;
import static ZkService.utils.Host.getIp;
import static server.utils.global.*;

public class ShardServer extends UberServiceGrpc.UberServiceImplBase {
    private static final Logger logger = Logger.getLogger(ShardServer.class.getName());

    // connection info
    private final Server server;
    private ZkServiceImpl zkService;
    public Boolean service_up;
    private final String shardName;
    private final String HostName;
    private final LbClient lb;
    private ConcurrentMap<Integer, ShardClient> shardMembers;
    private ShardClient leader;

    // db
    private final ConcurrentMap<String, Ride> rides =
            new ConcurrentHashMap<>();
    private final ConcurrentMap<String, CustomerRequest> customerRequests =
            new ConcurrentHashMap<>();

    public static void main(String[] args) { // args example: 8200 localhost:2181 "A" (port, zookeeper host, cityName)

        try {
            BasicConfigurator.configure();
            ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.INFO);

            if(args.length == 3) {
                ShardServer server = new ShardServer(args[0], args[1], args[2]);
                server.start();
                server.blockUntilShutdown();
                /* Shai remove
                ClusterInfo.getClusterInfo().setZKhost(zkServiceAPI);
                 */
            }
            else {
                throw new RuntimeException("Not enough arguments");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Shard Server service failed to start");
        }
    }


    /**
     * Create a Uber server using serverBuilder as a base and features as data.
     */
    public ShardServer(String port, String hostList, String shardName) {
        this.service_up = false;
        this.shardName = shardName;
        this.HostName = getIp()+":" + port;

        this.server = ServerBuilder.forPort(Integer.parseInt(port))
                .addService(this)
                .build();

        ManagedChannel channel = ManagedChannelBuilder.forTarget(lbHostName).usePlaintext().build();
        this.lb = new LbClient(channel);
        logger.info("City Server name " + shardName + " connected to LB");

        ConnectToZk(hostList, port);
        updateShardMembers();
        setLeader();
    }

    public void ConnectToZk(String hostList, String port) {
        try {

            this.zkService = new ZkServiceImpl(hostList);

            // create all parent nodes /election, /all_nodes, /live_nodes
            // Shai - not sure we need to create root
            zkService.createAllParentNodes("");

            // create all parent nodes /election/city, /live_nodes/city, /live_nodes/city
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
            zkService.createNodeInElectionZnode(getIp() + ":" + port, shardName);
            ClusterInfo.getClusterInfo().setLeader(zkService.getLeaderNodeData(shardName));

            // If will will support server coming back to life
            // syncDataFromLeader();

            // add child znode under /live_node, to tell other servers that this server is ready to serve
            // read request
            logger.info("ConnectToZk addToLiveNodes");
            zkService.addToLiveNodes(getIp() + ":" + port, "I am alive", shardName);

            // register watchers for leader change, live nodes change, all nodes change and zk session
            // state change
            zkService.registerChildrenChangeListener(ELECTION_NODE + "/" + shardName, new LeaderChangeListener(this::setLeader));
            zkService.registerChildrenChangeListener(LIVE_NODES + "/" + shardName, new LiveNodeChangeListener(this::updateShardMembers));

            ClusterInfo.getClusterInfo().setLiveNodes(zkService.getLiveNodes(shardName));
            ClusterInfo.getClusterInfo().setZkHost(zkService);

            logger.info("Finished ConnectToZk for city " + shardName + " host " + getIp() + ":" +port);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Startup failed!", e);
        }
    }

    public void setLeader() {
        String target = ClusterInfo.getClusterInfo().getLeader();
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        this.leader = new ShardClient(channel);
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
                    ShardServer.this.stop();
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

        if (ride.getSentByLeader()){
            Ride updatedRide = Ride.newBuilder(ride).setSentByLeader(false).build();
            rides.put(updatedRide.getId(), updatedRide);
            try {
                responseObserver.onNext(res.setIsSuccess(true).build());
                responseObserver.onCompleted();
            } catch (StatusRuntimeException e) {
                e.printStackTrace();
            }
            return;
        }

        String rideId = ride.getFirstName() + ride.getLastName() + ride.getDate() + ride.getSrcCity() + ride.getDstCity();

        if(rides.containsKey(rideId)) {
            try {
                responseObserver.onNext(res.setIsSuccess(false).build());
                responseObserver.onCompleted();

            } catch (StatusRuntimeException e) {
                e.printStackTrace();
            }
            return;
        }

        // this is a new ride, if it gets rollback we should delete it
        Ride updatedRide = Ride.newBuilder(ride).setId(rideId).setDelete(true).build();
        try {

            if(rideCommit(updatedRide)) {
                responseObserver.onNext(res.setIsSuccess(true).build());
            } else {
                responseObserver.onNext(res.setIsSuccess(false).build());
            }

            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void postCustomerRequest(CustomerRequest request, StreamObserver<Result> responseObserver) {
        System.out.println("City server got post customer request");
        System.out.println("-------------");

        if(request.getSentByLeader()) {
            customerRequests.put(request.getId(), request);
        }

        Result res = Result.newBuilder().setIsSuccess(true).build();
        try {
            responseObserver.onNext(res);
            responseObserver.onCompleted();

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postPathPlanningRequest(
            CustomerRequest request, StreamObserver<Ride> responseObserver) {
        try {
            System.out.println("City server got postPathPlanningRequest request");
            System.out.println("-------------");
            // map of (ride: shardName)
            Map<Ride, String> reservedRides = new HashMap<>();
            String src, dst;
            Rout.Builder routBuilder = Rout.newBuilder()
                    .setName(request.getName())
                    .setDate(request.getDate());
            Rout rout;

            String id = request.getName() + request.getPathList().toString() + request.getDate();
            CustomerRequest.Builder requestBuilder = CustomerRequest.newBuilder(request).setId(id);


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

                if(remoteRide.getRide().equals(noRide())) {
                    CustomerRequest updatedRequest = requestBuilder.build();
                    customerRequestCommit(updatedRequest);

                    revertPath(reservedRides);
                    responseObserver.onNext(noRide());
                    responseObserver.onCompleted();
                    return;
                }
                reservedRides.put(remoteRide.getRide(), remoteRide.getShardName());
            }

            // Entire path satisfied
            CustomerRequest updatedRequest = requestBuilder.addAllRides(reservedRides.keySet()).build();
            if(customerRequestCommit(updatedRequest)) {
                for (Ride ride : reservedRides.keySet()) {
                    responseObserver.onNext(ride);
                }
            }

            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reserveRide(Rout request, StreamObserver<Ride> responseObserver){
        try {
            Ride ride = getLocalMatchingRide(request);
            responseObserver.onNext(ride);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revertCommit(Ride request, StreamObserver<Result> responseObserver) {
        try {
            revertLocalCommit(request);
            Result res = Result.newBuilder().setIsSuccess(true).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomerRequest(CustomerRequest request, StreamObserver<Result> responseObserver) {
        try {
            if (request.getSentByLeader()) {
                customerRequests.remove(request.getId());
            }

            Result res = Result.newBuilder().setIsSuccess(true).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void snapshot(com.google.protobuf.Empty request,
                         StreamObserver<Result> responseObserver) {
        try {
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
            responseObserver.onNext(Result.newBuilder().setIsSuccess(true).build());
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    private Boolean isNodeLeader(){
        return zkService.getLeaderNodeData(shardName).equals(this.HostName);
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

        int numerator = Math.abs((rideDst.getX() - rideSrc.getX()) * (rideSrc.getY() - p0.getY()) - (rideSrc.getX()-p0.getX()) * (rideDst.getY() - rideSrc.getY()));
        double denominator = Math.sqrt(Math.pow(rideDst.getX() - rideSrc.getX(), 2)+ Math.pow(rideDst.getY() - rideSrc.getY(), 2));
        return (numerator / denominator) <= ride.getPd();
    }

    private boolean customerAlreadyInRide(Ride ride, String name) {
        for(int i = 0; i < ride.getCustomersCount(); i++) {
            if(name.equals(ride.getCustomers(i))) return true;
        }
        return false;
    }

    public boolean rideCommit(Ride ride) {

        if(isNodeLeader()) {
            // This node is the leader

            // phase 1
            Ride.Builder rideBuilder = Ride.newBuilder(ride)
                    .setSentByLeader(true);

            Ride existingRide = rides.get(ride.getId());
            if (existingRide != null && existingRide.getTakenPlaces() == ride.getTakenPlaces() ) {
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
            // shardMembers = updateShardMembers();
            Ride commitRide = rideBuilder.build();

            for(ShardClient shard : shardMembers.values()) {
                boolean isSuccess = shard.postRide(commitRide);
                if (isSuccess) {
                    acceptedCounter++;
                }
            }

            if(acceptedCounter < shardMembers.size()) {
                rollbackRide(commitRide);
                return false;
            }

            Ride save = rideBuilder.setSentByLeader(false).build();
            rides.put(save.getId(), save);
            return true;

        } else {
            // This node is not the leader

            // setLeader();
            return leader.postRide(ride);
        }
    }

    public boolean customerRequestCommit(CustomerRequest request) {
        if(isNodeLeader()) {
            // This node is the leader

            int acceptedCounter = 0;

            CustomerRequest commitRequest = CustomerRequest.newBuilder(request)
                    .setSentByLeader(true)
                    .build();

            // shardMembers = updateShardMembers();

            for(ShardClient shard : shardMembers.values()) {
                boolean isSuccess = shard.postCustomerRequest(commitRequest);
                if (isSuccess) {
                    acceptedCounter++;
                }
            }

            if(acceptedCounter < shardMembers.size()) {
                rollbackCustomerRequest(commitRequest);
                return false;
            }

            customerRequests.put(commitRequest.getId(), commitRequest);
            return true;

        } else {
            // This node is not the leader

            // setLeader();
            return leader.postCustomerRequest(request);
        }
    }

    private void rollbackCustomerRequest(CustomerRequest request) {
        // shardMembers = updateShardMembers();

        for(ShardClient shard : shardMembers.values()) {
            boolean isSuccess = shard.deleteCustomerRequest(request);
            if (!isSuccess) {
                logger.info("rollback failed");
            }
        }
    }

    private void rollbackRide(Ride ride) {
        // shardMembers = updateShardMembers();

        for(ShardClient shard : shardMembers.values()) {
            boolean isSuccess = shard.revertCommit(ride);
            if (!isSuccess) {
                logger.info("rollback failed");
            }
        }
    }

    public void updateShardMembers() {

        List<String> liveNodes = ClusterInfo.getClusterInfo().getLiveNodes();
        liveNodes.remove(HostName);

        ConcurrentHashMap<Integer, ShardClient> shards = new ConcurrentHashMap<>();

        // get new members connections
        int i = 0;
        for (String targetHost : liveNodes) {
            ManagedChannel channel = ManagedChannelBuilder.forTarget(targetHost).usePlaintext().build();
            ShardClient client = new ShardClient(channel);
            shards.put(i++, client);
        }
        // delete all previous stubs
        // shardMembers.clear();
        shardMembers = shards;
    }

    private Ride getLocalMatchingRide(Rout rout) {
        for (Ride ride : this.rides.values()) {
            if (isMatch(ride, rout)) {

                Ride updatedRide = Ride.newBuilder(ride)
                        .setTakenPlaces(ride.getTakenPlaces() + 1)
                        .addCustomers(rout.getName())
                        .setDelete(false).build();
                if (rideCommit(updatedRide)) {
                    return updatedRide;
                }
            }
        }
        return noRide();
    }

    private ShardRide getRemoteMatchingRide(Rout rout) {
        for(String shard : shardNames){
            Ride ride = lb.cityRequestRide(shard, rout);
            if(!ride.equals(noRide())) {
                return new ShardRide(shard, ride);
            }

        }
        return new ShardRide("", noRide());
    }

    private void revertLocalCommit(Ride ride) {

        if (ride.getSentByLeader()) {
            // This is a rollback of a post ride request
            if(ride.getDelete()) {
                rides.remove(ride.getId());
            } else {
                Ride updatedRide = Ride.newBuilder(ride).setSentByLeader(false).build();
                rides.put(updatedRide.getId(), updatedRide);
            }

            return;
        }

        rideCommit(ride);
    }

    private void revertPath(Map<Ride, String> reservedRides){
        for (Map.Entry<Ride, String> entry : reservedRides.entrySet()) {

            List<String> customers = entry.getKey().getCustomersList();
            customers.remove(customers.size() - 1);

            Ride updatedRide = Ride.newBuilder(entry.getKey())
                    .setTakenPlaces(entry.getKey().getTakenPlaces() - 1)
                    .addAllCustomers(customers)
                    .setDelete(false)
                    .build();

            if (entry.getValue().equals(this.shardName)) {
                revertLocalCommit(updatedRide);
            } else {
                lb.CityRevertRequestRide(entry.getValue(), updatedRide);
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

        System.out.println("\nDate: " + req.getDate());

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