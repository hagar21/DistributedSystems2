package server.utils;

import generated.*;

public class ShardRide {
    String shardName;
    Ride ride;

    public ShardRide(String s, Ride r) {
        shardName = s;
        ride = r;
    }

    public Ride getRide() { return ride; }
    public String getShardName() { return shardName; }
}
