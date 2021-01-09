package server;

import client.CityClient;
import com.google.protobuf.Descriptors;
import generated.CustomerRequest;
import generated.Ride;
import generated.Rout;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Point p){
        return this.x == p.x && this.y == p.y;
    }
}

public class CityUtil {
    public static int shardsNumber = 2;

    public static Point mapCityToLocation(String city) {
        switch(city) {
            case "haifa":
                return new Point(0,0);
            case "karkur":
                return new Point(0,1);
            case "monash":
                return new Point(1,0);
            default:
                return new Point(1,1);
        }
    }

    public static boolean isMatch(Ride ride, Rout rout) {
        if(!ride.getDate().equals(rout.getDate())) return false;
        if(ride.getOfferedPlaces() == ride.getTakenPlaces()) return false;
        if(customerAlreadyInRide(ride, rout.getName())) return false;

        Point rideSrc = mapCityToLocation(ride.getSrcCity());
        Point rideDst = mapCityToLocation(ride.getDstCity());
        Point reqSrc = mapCityToLocation(rout.getSrcCity());
        Point reqDst = mapCityToLocation(rout.getDstCity());

        Point p0;
        if(rideSrc.equals(reqSrc)) {
            p0 = mapCityToLocation(rout.getDstCity());
        } else if (rideDst.equals(reqDst)) {
            p0 = mapCityToLocation(rout.getSrcCity());
        } else {
            return false;
        }

        int numerator = Math.abs((rideDst.x - rideSrc.x) * (rideSrc.y - p0.y) - (rideSrc.x-p0.x) * (rideDst.y - rideSrc.y));
        double denominator = Math.sqrt(Math.pow(rideDst.x - rideSrc.x, 2)+ Math.pow(rideDst.y - rideSrc.y, 2));
        return (numerator / denominator) <= ride.getPd();
    }

    private static boolean customerAlreadyInRide(Ride ride, String name) {
        for(int i = 0; i < ride.getCustomersCount(); i++) {
            if(name.equals(ride.getCustomers(i))) return true;
        }
        return false;
    }

    public static Ride noRide() {
        return Ride.newBuilder().setId("noRide").setFirstName("no").setLastName("ride").build();
    }

    public static List<CityClient> initShards(int port) {
        List<CityClient> shards = new ArrayList<>();
//        for(int i = 0; i < shardsNumber; i++) {
//            String target = "localhost:" + (8980 + i);
//            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
//            CityClient client = new CityClient(channel);
//
//            shards.add(client);
//        }

        if (port == 8990) {
            String target = "localhost:8991";
            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
            CityClient client = new CityClient(channel);

            shards.add(client);

            String target1 = "localhost:8992";
            ManagedChannel channel1 = ManagedChannelBuilder.forTarget(target1).usePlaintext().build();
            CityClient client1 = new CityClient(channel1);

            shards.add(client1);
        }

        if (port == 8991) {
            String target = "localhost:8990";
            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
            CityClient client = new CityClient(channel);

            shards.add(client);

            String target1 = "localhost:8992";
            ManagedChannel channel1 = ManagedChannelBuilder.forTarget(target1).usePlaintext().build();
            CityClient client1 = new CityClient(channel1);

            shards.add(client1);
        }

        if (port == 8992) {
            String target = "localhost:8990";
            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
            CityClient client = new CityClient(channel);

            shards.add(client);

            String target1 = "localhost:8991";
            ManagedChannel channel1 = ManagedChannelBuilder.forTarget(target1).usePlaintext().build();
            CityClient client1 = new CityClient(channel1);

            shards.add(client1);
        }
        return shards;
    }

    public static boolean rideConsensus(Ride ride) { return true; }
    public static boolean customerConsensus(CustomerRequest req) { return true; }

    public static int getShardNumber() {
        return CityService.port;
    }

    public static Ride getLocalMatchingRide(Rout rout) {
        for (Ride ride : CityService.rides.values()) {
            if (CityUtil.isMatch(ride, rout) && rideConsensus(ride)) {
                 {
                    Ride updatedRide = Ride.newBuilder(ride)
                            .setTakenPlaces(ride.getTakenPlaces() + 1)
                            .addCustomers(rout.getName()).build();
                    CityService.rides.put(updatedRide.getId(), updatedRide);
                    return updatedRide;
                }
            }
        }
        return CityUtil.noRide();
    }

    public static ShardRide getRemoteMatchingRide(List<CityClient> shards, Rout rout) {
        int index = 0;
        for(CityClient client : shards){
            Ride ride = client.reserveRide(rout);
            if(!ride.equals(CityUtil.noRide())) {
                return new ShardRide(index, ride);
            }
            index++;
        }
        return new ShardRide(-1, CityUtil.noRide());
    }

    public static void revertLocalCommit(Ride ride) {
        Ride updatedRide = Ride.newBuilder(ride).setTakenPlaces(ride.getTakenPlaces() - 1).build();
        if(rideConsensus(updatedRide)) {
            CityService.rides.put(updatedRide.getId(), updatedRide);
        }
    }

    public static void revertPath(Map<Ride, Integer> reservedRides, List<CityClient> shards){
        for (Map.Entry<Ride, Integer> entry : reservedRides.entrySet()) {
            if (entry.getValue() == getShardNumber()) {
                revertLocalCommit(entry.getKey());
            } else {
                shards.get(entry.getValue()).revertCommit(entry.getKey());
            }
        }
    }

    public static void printRide(Ride ride){
        System.out.println("Name: " + ride.getFirstName() + " " + ride.getLastName());
        System.out.println("Phone number: " + ride.getPhoneNum());
        System.out.println("Date: " + ride.getDate());
        System.out.println("Path: " + ride.getSrcCity() + " to " + ride.getDstCity());
        System.out.println("Offered places: " + ride.getOfferedPlaces());
        System.out.println("Taken places: " + ride.getTakenPlaces());
        System.out.println("-------------");
    }

    public static void printCustomerRequest(CustomerRequest req){
        System.out.println("Name: " + req.getName());
        System.out.print("Path: ");
        for (String path : req.getPathList()) {
            System.out.print(" " + path);
        }

        System.out.println("Date: " + req.getDate());

        if(req.getRidesCount() > 0) {
            System.out.println("The request is satisfied by:");
            for(Ride ride : req.getRidesList()) {
                System.out.println(ride.getFirstName() + " " + ride.getLastName());
            }
        } else {
            System.out.println("The request couldn't be satisfied");
        }

        System.out.println("-------------");
    }
}
