package client.utils;

import client.CityClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LbShardConnections {
    public static List<CityClient> shardClients;
    public static int rrIdx;

    public LbShardConnections() {
        shardClients = new ArrayList<>();
        rrIdx = 0;
    }

    public void AddToShard(CityClient c) {
        shardClients.add(c);
    }

    public CityClient getNextService() {
        return shardClients.get(rrIdx++ % shardClients.size());
    }
}
