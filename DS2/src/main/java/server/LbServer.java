package server;

import ZkService.Listeners.LiveNodeChangeListener;
import ZkService.ZkServiceImpl;
import ZkService.utils.ClusterInfo;
import ch.qos.logback.classic.Level;
import client.CityClient;
import client.utils.LbShardConnections;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static ZkService.ZkService.LIVE_NODES;
import static server.utils.global.noRide;
import static server.utils.global.shardNames;

import generated.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;


public class LbServer extends UberServiceGrpc.UberServiceImplBase {

    private final Server server;
    private final ConcurrentMap<String, LbShardConnections> shardConnections =
            new ConcurrentHashMap<>();
    private ZkServiceImpl zkService;

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
            System.out.println("City Server service failed to start");
        }
    }

    LbServer(String hostList) { // port 8990
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
                zkService.registerChildrenChangeListener(LIVE_NODES + "/" + shardName, new LiveNodeChangeListener(this::updateShardMembers));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Startup failed!", e);
        }
    }

    private void updateShardMembers(/*String shard*/) {
        String shard = "A";
        List<String> liveNodes = ClusterInfo.getClusterInfo().getLiveNodes();

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
            CityClient client = new CityClient(channel);
            lbsc.AddToShard(client);
        }

        shardConnections.put(shard, lbsc);
    }

    private void updateShardMembers(String shard) {
        List<String> liveNodes = ClusterInfo.getClusterInfo().getLiveNodes();

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
            CityClient client = new CityClient(channel);
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

    private String MapCityToShard(String City) {
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

    // Accept a user's request to join a ride and check if there is a relevant ride.
    public Ride cityRequestRide(CityRequest cityRequest) {
        System.out.println("LB got cityRequest to dest city " + cityRequest.getDestCityName() + " sending city request");
        System.out.println("------------");

        String shard = MapCityToShard(cityRequest.getDestCityName());
        if (shard.equals("")) {
            System.out.println("error: LB got cityRequest to dest city " + cityRequest.getDestCityName() + " not in system");
            return noRide(); // Shai Ilegal ride - so it won't keep looking
        }

        updateShardMembers(shard);

        CityClient destService = shardConnections.get(shard).getNextService();
        return destService.cityRequestRide(cityRequest);
    }

    public void CityRevertRequestRide(CityRevertRequest revertRequest) {
        System.out.println("LB got CityRevertRequest to dest city " + revertRequest.getDestCityName() + " sending city request");
        System.out.println("------------");

        String shard = MapCityToShard(revertRequest.getDestCityName());
        if (shard.equals("")) {
            System.out.println("error: LB got cityRequest to dest city " + revertRequest.getDestCityName() + " not in system");
            return;
        }

        updateShardMembers(shard);

        CityClient destService = shardConnections.get(shard).getNextService();
        destService.cityRevertRequestRide(revertRequest);
    }


    public void start() throws IOException {
        try {
            server.start();
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
