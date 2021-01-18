package ZkService.Listeners;

import ZkService.utils.ClusterInfo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;

import java.util.Collections;
import java.util.List;


@Slf4j
@Setter
public class AtomicBroadcastChangeListener implements IZkChildListener {

    private final Runnable callbackFunction;

    public AtomicBroadcastChangeListener(Runnable callbackFunction) {
        this.callbackFunction = callbackFunction;
    }

    @Override
    public void handleChildChange(String parentPath, List<String> currentChildren) {
        if (currentChildren.isEmpty()) {
            return; // Leader deleted its broadcast
        } else {

            System.out.println("Shai leaderBroadcasts: handleChildChange" + currentChildren.size()); // should be set to 1 always

            //get least sequenced znode
            Collections.sort(currentChildren);
            String leaderZNode = currentChildren.get(0);

            // once znode is fetched, fetch the znode data to get the hostname of new leader
            String broadcastData = ClusterInfo.getClusterInfo().getZkHost().getZNodeData(parentPath);
            //log.info("new leader is: {}", masterNode);
            System.out.println("new data (Ride|CustomerRequest) is: {}" + broadcastData);

            callbackFunction.run();
        }
    }
}
