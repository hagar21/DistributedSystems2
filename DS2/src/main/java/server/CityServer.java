package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import ZkService.ZkServiceImpl;
import ZkService.utils.ClusterInfo;
import org.I0Itec.zkclient.IZkChildListener;
import ZkService.Listeners.LeaderChangeListener;
import ZkService.Listeners.LiveNodeChangeListener;

import static ZkService.ZkService.ELECTION_NODE;
import static ZkService.ZkService.LIVE_NODES;
import static ZkService.utils.Host.getHostPostOfServer;

public class CityServer {
    private static final Logger logger = Logger.getLogger(CityServer.class.getName());

    private final int port;
    private final Server server;
    private ZkServiceImpl zkService;
    private String city;

    public CityServer(int port) throws IOException {
        this(ServerBuilder.forPort(port), port);
        System.out.println("CityServer c'tor1 called");
    }

    /**
     * Create a Uber server using serverBuilder as a base and features as data.
     */
    public CityServer(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        this.server = serverBuilder.addService(new CityService(port))
                .build();
        this.city = "mock";

        System.out.println("CityServer c'tor2 called");

        //ConnectToZk();
    }

    public void ConnectToZk() {
        try {

            this.zkService = new ZkServiceImpl("localhost:5050"); // one city

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
}