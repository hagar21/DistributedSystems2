package ZkService;

import org.I0Itec.zkclient.IZkChildListener;

import java.util.List;

public interface ZkService {

    public static final String ELECTION_NODE = "/election";
    public static final String LIVE_NODES = "/liveNodes";
    public static final String COMMIT_BACKUP = "/commitBackup";
    public static final String RIDES = "/rides";
    public static final String CUSTOMER_REQUESTS = "/customerRequests";

    // shai - added lock
    public static final String DISTRUBUTED_LOCK = "/lock";

    void createAllParentNodes(String shard);

    // Leader election
    public void createNodeInElectionZnode(String hostData, String shard);
    public String getLeaderNodeData(String shard);

    // membership
    void addToLiveNodes(String nodeName, String data, String shard);
    public List<String> getLiveNodes(String shard);

    // Leader backup
    public void leaderCreateRideCommitBackup(String data, String shard);
    public void leaderDeleteRideCommitBackup(String shard);
    public boolean hasRideCommitBackupChild(String shard);

    public void leaderCreateCrCommitBackup(String data, String shard);
    public void leaderDeleteCrCommitBackup(String shard);
    public boolean hasCrCommitBackupChild(String shard);

    public String getZNodeData(String path);

    // Used to trace leader change + server died
    void registerChildrenChangeListener(String path, IZkChildListener iZkChildListener);
}
