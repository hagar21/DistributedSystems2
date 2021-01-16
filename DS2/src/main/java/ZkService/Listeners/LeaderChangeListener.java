package ZkService.Listeners;


import java.util.Collections;
import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;

import ZkService.utils.ClusterInfo;
import ZkService.ZkService;
import server.CityServer;
import server.LbServer;

@Slf4j
@Setter
public class LeaderChangeListener implements IZkChildListener {

    private ZkService zkService;
    private final Runnable callbackFunction;

    public LeaderChangeListener(Runnable callbackFunction) {
        this.callbackFunction = callbackFunction;
    }

    /**
     * listens for deletion of sequential znode under /election znode and updates the
     * clusterinfo
     *
     * @param parentPath
     * @param currentChildren
     */
    @Override
    public void handleChildChange(String parentPath, List<String> currentChildren) {
        if (currentChildren.isEmpty()) {
            throw new RuntimeException("No node exists to select master!!");
        } else {
            //get least sequenced znode
            Collections.sort(currentChildren);
            String leaderZNode = currentChildren.get(0);

            // once znode is fetched, fetch the znode data to get the hostname of new leader
            String leaderNode = zkService.getZNodeData("/election".concat("/").concat(leaderZNode));
            //log.info("new leader is: {}", masterNode);
            System.out.println("new leader is: {}" + leaderNode);

            //update the cluster info with new leader
            ClusterInfo.getClusterInfo().setLeader(leaderNode);

            callbackFunction.run();

        }
    }
}

