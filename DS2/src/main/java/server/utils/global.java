package server.utils;

import generated.*;

public class global {
    public static Ride noRide() {
        return Ride.newBuilder().setId("noRide").setFirstName("no").setLastName("ride").build();
    }

    public static String lbHostName = "localHost:8990";

    public static String[] shardNames = { "shardA", "shardB", "shardC" };
}
