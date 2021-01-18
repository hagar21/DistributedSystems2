package server.utils;

import generated.*;

public class global {
    public static Ride noRide() {
        return Ride.newBuilder().setId("noRide").setFirstName("no").setLastName("ride").build();
    }

    public static String lbHostName = "localHost:8990";

    public static String zkHostName = "localhost:2181";

//    public static String[] shardNames = { "shardA", "shardB", "shardC" };
    public static String[] shardNames = { "shardA", "shardB" };

    public static Point mapCityToLocation(String city) {
        switch(city) {
            case "a1":
                return new Point(0,0);
            case "a2":
                return new Point(0,1);
            case "b":
                return new Point(1,0);
            case "c":
                return new Point(1,1);
            default:
                return new Point(-1,-1);
        }
    }

    public static String MapCityToShard(String City) {
        switch (City) {
            case "a1":
            case "a2":
                return shardNames[0];
            case "b":
                return shardNames[1];
//            case "c":
//                return shardNames[2];
            default:
                System.out.println("No such city in system");
                return "";
        }
    }
}
