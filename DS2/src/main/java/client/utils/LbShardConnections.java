package client.utils;

import client.ShardClient;

import java.util.ArrayList;
import java.util.List;

public class LbShardConnections {
    public static List<ShardClient> shardClients;
    public static int rrIdx;

    public LbShardConnections() {
        shardClients = new ArrayList<>();
        rrIdx = 0;
    }

    public void AddToShard(ShardClient c) {
        shardClients.add(c);
    }

    public ShardClient getNextService() {
        return shardClients.get(rrIdx++ % shardClients.size());
    }
}
