package ZkService.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Host {

    private static String ipPort = null;

    public static String getHostPostOfServer() {
        if (ipPort != null) {
            return ipPort;
        }
        System.out.println("ip is null");
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("failed to fetch Ip!", e);
        }
        int port = Integer.parseInt(System.getProperty("server.port"));
        ipPort = ip.concat(":").concat(String.valueOf(port));
        return ipPort;
    }

}
