package ZkService.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Host {

    private static String ipAdd = null;

    public static String getIp() {
        if (ipAdd != null) {
            return ipAdd;
        }
        try {
            ipAdd = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("failed to fetch Ip!", e);
        }
        return ipAdd;
    }

}
