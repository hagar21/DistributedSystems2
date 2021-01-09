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

@Slf4j
public class ZkServiceImpl implements ZkService{

    private ZkClient zkClient;

    @Override
    public void createAllParentNodes(String city) {
        city = "/" + city;
        if (!zkClient.exists(ALL_NODES + city)) {
            zkClient.create(ALL_NODES + city, "all nodes are displayed here", CreateMode.PERSISTENT);
        }
        if (!zkClient.exists(LIVE_NODES + city)) {
            zkClient.create(LIVE_NODES + city, "all live nodes are displayed here", CreateMode.PERSISTENT);
        }
        if (!zkClient.exists(ELECTION_NODE + city)) {
            zkClient.create(ELECTION_NODE + city, "election node", CreateMode.PERSISTENT);
        }
    }

    @Override
    public void createNodeInElectionZnode(String hostData, String city) {
        city = "/" + city;
        if (!zkClient.exists(ELECTION_NODE + city)) {
            zkClient.create(ELECTION_NODE + city, "election node", CreateMode.PERSISTENT);
        }
        zkClient.create(ELECTION_NODE + city.concat("/node"), hostData, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    @Override
    public String getLeaderNodeData(String city) {
        city = "/" + city;
        if (!zkClient.exists(ELECTION_NODE + city)) {
            throw new RuntimeException("No node /election exists");
        }
        List<String> nodesInElection = zkClient.getChildren(ELECTION_NODE + city);
        Collections.sort(nodesInElection);
        String masterZNode = nodesInElection.get(0);
        return getZNodeData(ELECTION_NODE + city.concat("/").concat(masterZNode));
    }

    @Override
    public String getZNodeData(String path) {
        return zkClient.readData(path, null);
    }

    @Override
    public void addToLiveNodes(String nodeName, String data, String city) {
        city = "/" + city;
        if (!zkClient.exists(LIVE_NODES + city)) {
            zkClient.create(LIVE_NODES + city, "all live nodes are displayed here", CreateMode.PERSISTENT);
        }
        String childNode = LIVE_NODES.concat(city).concat("/").concat(nodeName);
        if (zkClient.exists(childNode)) {
            return;
        }
        zkClient.create(childNode, data, CreateMode.EPHEMERAL);
    }

    // maybe need all nodes as well
    @Override
    public List<String> getLiveNodes(String city) {
        city = "/" + city;
        if (!zkClient.exists(LIVE_NODES + city)) {
            throw new RuntimeException("No node /liveNodes exists");
        }
        return zkClient.getChildren(LIVE_NODES + city);
    }

    @Override
    public void registerChildrenChangeWatcher(String path, IZkChildListener iZkChildListener) {
        zkClient.subscribeChildChanges(path, iZkChildListener);
    }
}


