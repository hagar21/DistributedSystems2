package ZkService.Listeners;

import java.util.List;

import ZkService.utils.ClusterInfo;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;

@Slf4j
public class LiveNodeChangeListener implements IZkChildListener {

    private final Runnable callbackFunction;

    public LiveNodeChangeListener(Runnable callbackFunction) {
        this.callbackFunction = callbackFunction;
    }

    /**
     * - This method will be invoked for any change in /live_nodes children
     * - During registering this listener make sure you register with path /live_nodes
     * - after receiving notification it will update the local clusterInfo object
     *
     * @param parentPath this will be passed as /live_nodes
     * @param currentChildren new list of children that are present in /live_nodes, children's string alue is znode name which is set as server hostname
     */
    @Override
    public void handleChildChange(String parentPath, List<String> currentChildren) {
        //log.info("current live size: {}", currentChildren.size());
        System.out.println("before current live size: " + currentChildren.size());
        ClusterInfo.getClusterInfo().getLiveNodes().clear();
        ClusterInfo.getClusterInfo().getLiveNodes().addAll(currentChildren);
        System.out.println("after current live size: " + currentChildren.size());

        callbackFunction.run();
    }
}