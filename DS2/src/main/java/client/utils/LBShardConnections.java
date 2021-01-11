package client.utils;

import server.CityServer;

import java.util.ArrayList;
import java.util.List;

public class LBShardConnections {
    public static List<CityServer> shardServers;
    public static int rrIdx;

    LBShardConnections() {
        shardServers = new ArrayList<>();
        rrIdx = 0;
    }

    public CityServer getNextService() {
        return shardServers.get(rrIdx++ % shardServers.size());
    }
}
