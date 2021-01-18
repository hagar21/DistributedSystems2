package client.utils;

import client.ShardClient;
import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LbShardConnections {
    public List<ShardClient> shardClients;
    public int rrIdx;

    public LbShardConnections() {
        shardClients = new ArrayList<>();
        rrIdx = 0;
    }

    public void AddToShard(ShardClient c) {
        shardClients.add(c);
    }

    public boolean hasClients() {
        return shardClients.size() != 0;
    }

    public ShardClient getNextService() {
        return shardClients.get(rrIdx++ % (shardClients.size() != 0 ? shardClients.size() : 1));
    }
}
