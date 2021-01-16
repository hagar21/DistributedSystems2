package server;

import Rest.utils.RideAlreadyExistsException;
import ZkService.Listeners.LiveNodeChangeListener;
import ZkService.ZkServiceImpl;
import ZkService.utils.ClusterInfo;
import ch.qos.logback.classic.Level;
import client.ShardClient;
import client.utils.LbShardConnections;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static ZkService.ZkService.LIVE_NODES;
import static server.utils.global.noRide;
import static server.utils.global.shardNames;

import generated.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;
import server.utils.ShardRide;



public class LbServer extends UberServiceGrpc.UberServiceImplBase {

    private final Server server;
    private final ConcurrentMap<String, LbShardConnections> shardConnections =
            new ConcurrentHashMap<>();
    private ZkServiceImpl zkService;

    /*
    public static void main(String[] args) { // args example: localhost:2181 (zookeeper host)

        try {
            BasicConfigurator.configure();
            ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.INFO);

            if(args.length == 1) {
                LbServer server = new LbServer(args[0]);
                server.start();
                System.out.println("LB Server started on port 8990");
                server.blockUntilShutdown();
                ClusterInfo.getClusterInfo().setZKhost(zkServiceAPI);
            }
            else {
                throw new RuntimeException("Not enough arguments");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("City Server service failed to start");
        }
    }
    */

    public LbServer(String hostList) { // port 8990
        this.server = ServerBuilder.forPort(Integer.parseInt("8990"))
                .addService(this)
                .build();
        ConnectToZk(hostList);
    }

    private void ConnectToZk(String hostList) {
        try {
            this.zkService = new ZkServiceImpl(hostList);

            // create all parent nodes /election/city, /all_nodes/city, /live_nodes/city
            for (String shardName: shardNames)
            {
                zkService.createAllParentNodes(shardName);
                zkService.registerChildrenChangeListener(LIVE_NODES + "/" + shardName, new LiveNodeChangeListener());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Startup failed!", e);
        }
    }

/*    private Runnable updateShardMembers(String shard) {
        return () -> {
            List<String> liveNodes = zkService.getLiveNodes(shard);

            LbShardConnections lbsc = new LbShardConnections();

            if (shardConnections.containsKey(shard)) {
                int rrIdx = shardConnections.get(shard).rrIdx;
                lbsc.rrIdx = rrIdx; // to keep the round robin alive ;)
                shardConnections.get(shard).shardClients.clear();
                shardConnections.remove(shard);
            }

            // get new members connections
            for (String targetHost : liveNodes) {
                ManagedChannel channel = ManagedChannelBuilder.forTarget(targetHost).usePlaintext().build();
                ShardClient client = new ShardClient(channel);
                lbsc.AddToShard(client);
            }

            shardConnections.put(shard, lbsc);
        };
    }*/

    private void updateShardMembers(String shard) {
        List<String> liveNodes = zkService.getLiveNodes(shard);

        LbShardConnections lbsc = new LbShardConnections();

        if (shardConnections.containsKey(shard)) {
            lbsc.rrIdx = shardConnections.get(shard).rrIdx; // to keep the round robin alive ;)
            shardConnections.get(shard).shardClients.clear();
            shardConnections.remove(shard);
        }

        // get new members connections
        for (String targetHost : liveNodes) {
            ManagedChannel channel = ManagedChannelBuilder.forTarget(targetHost).usePlaintext().build();
            ShardClient client = new ShardClient(channel);
            lbsc.AddToShard(client);
        }

        shardConnections.put(shard, lbsc);
    }

    /* Shai delete
    public void addCityClient(ShardClient ShardClient) {
    // ask ZK for live nodes, and update everytime before sending a request
        if (ShardClient.getShard().equals("")) {
            System.out.println("error: LB AddCityClient to city" + ShardClient.getShard() + " not in system");
            return;
        }

        LbShardConnections lbsc = shardConnections.get(ShardClient.getShard());
        if (lbsc == null) {
            System.out.println("LB AddCityClient creating new LbShardConnections to shard " + ShardClient.getShard());
            lbsc = new LbShardConnections();
            shardConnections.put(ShardClient.getShard(), lbsc);
        }

        ManagedChannel channel = ManagedChannelBuilder.forTarget(ShardClient.getTargetHost()).usePlaintext().build();
        CityClient c = new CityClient(channel);
        lbsc.AddToShard(c);
    }

     */

    private static String MapCityToShard(String City) {
        switch (City) {
            case "A":
            case "B":
                return shardNames[0];
            case "C":
                return shardNames[1];
            default:
                System.out.println("No such city in system");
                return "";
        }
    }

    public List<Rest.entities.Ride> PostPathPlanningRequest(Rest.entities.CustomerRequest customerRequest) {
        System.out.println("LB server got postPathPlanningRequest request");
        System.out.println("-------------");

        String shard = MapCityToShard(customerRequest.getPath().get(0));
        if (shard.equals("")) {
            System.out.println("error: LB got CustomerRequest to src city " + customerRequest.getPath().get(0) + " not in system");
            return new ArrayList<>();
        }

        updateShardMembers(shard);

        ShardClient destService = shardConnections.get(shard).getNextService();

        return destService.postPathPlanningRequest(customerRequest);
    }

    public void PostRide(Rest.entities.Ride ride) {
        System.out.println("LB server got postRide request");
        System.out.println("-------------");

        String shard = MapCityToShard(ride.getStartingPosition());
        if (shard.equals("")) {
            System.out.println("error: LB got postRide from src city " + ride.getStartingPosition() + " not in system");
            return;
        }

        updateShardMembers(shard);

        ShardClient destService = shardConnections.get(shard).getNextService();
        destService.postRide(ride);
    }

    // Accept a user's request to join a ride and check if there is a relevant ride.
    public void cityRequestRide(CityRequest cityRequest, StreamObserver<Ride> responseObserver) {
        System.out.println("LB got cityRequest to dest city " + cityRequest.getDestCityName() + " sending city request");
        System.out.println("------------");

        String shard = MapCityToShard(cityRequest.getDestCityName());
        if (shard.equals("")) {
            System.out.println("error: LB got cityRequest to dest city " + cityRequest.getDestCityName() + " not in system");
            responseObserver.onNext(noRide());
            responseObserver.onCompleted();
            return ; // Shai Illegal ride - so it won't keep looking
        }

        updateShardMembers(shard);

        ShardClient destService = shardConnections.get(shard).getNextService();
        responseObserver.onNext(destService.cityRequestRide(cityRequest));
        responseObserver.onCompleted();
    }

    @Override
    public void cityRevertRequestRide(CityRevertRequest revertRequest, StreamObserver<Result> responseObserver) {
        System.out.println("LB got CityRevertRequest to dest city " + revertRequest.getDestCityName() + " sending city request");
        System.out.println("------------");

        String shard = MapCityToShard(revertRequest.getDestCityName());
        if (shard.equals("")) {
            System.out.println("error: LB got cityRequest to dest city " + revertRequest.getDestCityName() + " not in system");
            Result res = Result.newBuilder().setIsSuccess(false).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            return;
        }

        updateShardMembers(shard);

        ShardClient destService = shardConnections.get(shard).getNextService();
        destService.cityRevertRequestRide(revertRequest);

        Result res = Result.newBuilder().setIsSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }


    public void start() throws IOException {
        try {
            server.start();
            System.out.println("LB Server started on port 8990");
        }catch (Exception e){
            e.printStackTrace();
        }
        // logger.info("Server started, listening on " + HostName); shai comment
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    LbServer.this.stop();
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
}
