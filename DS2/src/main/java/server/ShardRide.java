package server;

import generated.Ride;

public class ShardRide {
    String shardName;
    Ride ride;

    public ShardRide(String s, Ride r) {
        shardName = s;
        ride = r;
    }
}
