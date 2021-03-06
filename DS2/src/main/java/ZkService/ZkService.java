package ZkService;

import org.I0Itec.zkclient.IZkChildListener;

import java.util.List;

public interface ZkService {

    public static final String ELECTION_NODE = "/election";
    public static final String LIVE_NODES = "/liveNodes";
    public static final String ATOMIC_BROADCAST = "/atomicBroadcast";
    public static final String RIDES = "/rides";
    public static final String CUSTOMER_REQUESTS = "/customerRequests";
    public static final String LB = "/lb";

    void createAllParentNodes(String shard);

    // Leader election
    public void createNodeInElectionZnode(String hostData, String shard);
    public String getLeaderNodeData(String shard);

    // membership
    void addToLiveNodes(String nodeName, String data, String shard);
    public List<String> getLiveNodes(String shard);

    // Leader backup
    public String leaderCreateRideBroadcast(String data, String shard);
    public void leaderDeleteRideBroadcast(String shard, String seq);
    public List<String> getRideBroadcastNodes(String shard);

    public String leaderCreateCrBroadcast(String data, String shard);
    public void leaderDeleteCrBroadcast(String shard, String seq);
    public List<String> getCrBroadcastNodes(String shard);

    public String getZNodeData(String path);

    // Used to trace leader change + server died
    void registerChildrenChangeListener(String path, IZkChildListener iZkChildListener);
}
