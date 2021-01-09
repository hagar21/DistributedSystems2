package ZkService;

import org.I0Itec.zkclient.IZkChildListener;

import java.util.List;

public interface ZkService {

    public static final String ELECTION_NODE = "/election";
    public static final String LIVE_NODES = "/liveNodes";
    public static final String ALL_NODES = "/allNodes";

    // shai - added lock
    public static final String DISTRUBUTED_LOCK = "/lock";

    void createAllParentNodes(String city);

    // Leader election
    public void createNodeInElectionZnode(String hostData, String city);
    public String getLeaderNodeData(String city);

    // membership
    void addToLiveNodes(String nodeName, String data, String city);
    public List<String> getLiveNodes(String city);

    public String getZNodeData(String path);

    // Used to trace leader change + server died
    void registerChildrenChangeListener(String path, IZkChildListener iZkChildListener);
}
