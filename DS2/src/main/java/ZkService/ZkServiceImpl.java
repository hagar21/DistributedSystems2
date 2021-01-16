package ZkService;

import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import ZkService.utils.StringSerializer;

@Slf4j
public class ZkServiceImpl implements ZkService{

    private final ZkClient zkClient;

    public ZkServiceImpl(String hostPort) {
        zkClient = new ZkClient(hostPort, 12000, 30000, new StringSerializer());
    }

    @Override
    public void createAllParentNodes(String shard) {
        if (!shard.equals("")) {
            shard = "/" + shard;
        }

        if (!zkClient.exists(ALL_NODES + shard)) {
            zkClient.create(ALL_NODES + shard, "all nodes are displayed here", CreateMode.PERSISTENT);
        }
        if (!zkClient.exists(LIVE_NODES + shard)) {
            zkClient.create(LIVE_NODES + shard, "all live nodes are displayed here", CreateMode.PERSISTENT);
        }
        if (!zkClient.exists(ELECTION_NODE + shard)) {
            zkClient.create(ELECTION_NODE + shard, "election node", CreateMode.PERSISTENT);
        }
    }

    @Override
    public void createNodeInElectionZnode(String hostData, String shard) {
        shard = "/" + shard;
        if (!zkClient.exists(ELECTION_NODE + shard)) {
            zkClient.create(ELECTION_NODE + shard, "election node", CreateMode.PERSISTENT);
        }
        zkClient.create(ELECTION_NODE + shard.concat("/node"), hostData, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    @Override
    public String getLeaderNodeData(String shard) {
        shard = "/" + shard;
        if (!zkClient.exists(ELECTION_NODE + shard)) {
            throw new RuntimeException("No node /election exists");
        }
        List<String> nodesInElection = zkClient.getChildren(ELECTION_NODE + shard);
        Collections.sort(nodesInElection);
        String masterZNode = nodesInElection.get(0);
        return getZNodeData(ELECTION_NODE + shard.concat("/").concat(masterZNode));
    }

    @Override
    public String getZNodeData(String path) {
        return zkClient.readData(path, null);
    }

    @Override
    public void addToLiveNodes(String nodeName, String data, String shard) {
        shard = "/" + shard;
        if (!zkClient.exists(LIVE_NODES + shard)) {
            zkClient.create(LIVE_NODES + shard, "all live nodes are displayed here", CreateMode.PERSISTENT);
        }
        String childNode = LIVE_NODES.concat(shard).concat("/").concat(nodeName);
        if (zkClient.exists(childNode)) {
            return;
        }
        zkClient.create(childNode, data, CreateMode.EPHEMERAL);
    }

    // maybe need all nodes as well
    @Override
    public List<String> getLiveNodes(String shard) {
        shard = "/" + shard;
        if (!zkClient.exists(LIVE_NODES + shard)) {
            throw new RuntimeException("No node /liveNodes exists");
        }
        return zkClient.getChildren(LIVE_NODES + shard);
    }

    @Override
    public void registerChildrenChangeListener(String path, IZkChildListener iZkChildListener) {
        zkClient.subscribeChildChanges(path, iZkChildListener);
    }
}


