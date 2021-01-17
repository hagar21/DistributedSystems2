package ZkService;

import org.I0Itec.zkclient.IZkChildListener;

import java.util.List;

public interface ZkService {

    public static final String ELECTION_NODE = "/election";
    public static final String LIVE_NODES = "/liveNodes";
    public static final String ATOMIC_BROADCAST = "/atomicBroadcast";
    public static final String APPROVE = "/approve";

    void createAllParentNodes(String shard);

    // Leader election
    public void createNodeInElectionZnode(String hostData, String shard);
    public String getLeaderNodeData(String shard);

    // membership
    void addToLiveNodes(String nodeName, String data, String shard);
    public List<String> getLiveNodes(String shard);

    // atomic broadcast
    public void leaderBroadcast(String data, String shard);
    public void leaderClearBroadcast(String shard);

    public String getZNodeData(String path);

    // Used to trace leader change + server died
    void registerChildrenChangeListener(String path, IZkChildListener iZkChildListener);
}
