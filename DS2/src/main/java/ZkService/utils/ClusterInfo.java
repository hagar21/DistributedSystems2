package ZkService.utils;

import java.util.ArrayList;
import java.util.List;

import ZkService.ZkServiceImpl;
import generated.CustomerRequest;
import generated.Ride;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ClusterInfo {

    private static ClusterInfo clusterInfo = new ClusterInfo();

    public static ClusterInfo getClusterInfo() {
        return clusterInfo;
    }

    /*
        Ephemeral znodes under LIVE_NODES
     */
    private ZkServiceImpl zkHost;

    public ZkServiceImpl getZkHost() {return zkHost;}

    private List<String> liveNodes = new ArrayList<>();

    private String leader;

    private Ride rideBroadcast;
    private CustomerRequest customerRequestBroadcast;

    public List<String> getLiveNodes() { return this.liveNodes; }
    public void setLiveNodes(List<String> ln) { this.liveNodes = ln; }

    public String getLeader() { return this.leader; }
    public void setLeader(String l) { this.leader = l; }
}
