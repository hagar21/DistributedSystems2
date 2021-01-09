package server;

import generated.Ride;

public class ShardRide {
    int shardId;
    Ride ride;

    public ShardRide(int s, Ride r) {
        shardId = s;
        ride = r;
    }
}
