package server.utils;

import generated.*;

public class global {
    public static Ride noRide() {
        return Ride.newBuilder().setId("noRide").setFirstName("no").setLastName("ride").build();
    }

    public static String lbHostName = "localHost:8990";

    public static String zkHostName = "localhost:2181";

    public static String[] shardNames = { "shardA", "shardB", "shardC" };

    public static Point mapCityToLocation(String city) {
        switch(city) {
            case "a1":
                return new Point(0,0);
            case "a2":
                return new Point(0,1);
            case "a3":
                return new Point(0,2);
            case "b1":
                return new Point(1,0);
            case "b2":
                return new Point(2,0);
            case "c1":
                return new Point(1,1);
            case "c2":
                return new Point(2,2);
            default:
                return new Point(-1,-1);
        }
    }

    public static String MapCityToShard(String City) {
        switch (City) {
            case "a1":
            case "a2":
            case "a3":
                return shardNames[0];
            case "b1":
            case "b2":
                return shardNames[1];
            case "c1":
            case "c2":
                return shardNames[2];
            default:
                System.out.println("No such city in system");
                return "";
        }
    }
}
