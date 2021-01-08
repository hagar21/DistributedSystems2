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

    public boolean equals(Point p1, Point p2){
        return p1.x == p2.x && p1.y == p2.y;
    }
}

public class CityUtil {
    public static int shardsNumber = 5;

    public static Point mapCityToLocation(String city) {
        switch(city) {
            case "haifa":
                return new Point(0,0);
            case "karkur":
                return new Point(0,1);
            case "monash":
                return new Point(0,2);
            default:
                return new Point(1,1);
        }
    }

    public static boolean isMatch(Ride ride, Rout rout) {
        if(!ride.getDate().equals(rout.getDate())) return false;
        if(ride.getOfferedPlaces() == ride.getTakenPlaces()) return false;

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

    public static Ride noRide() {
        return Ride.newBuilder().setId("noRide").build();
    }

    public static List<CityClient> initShards() {
        List<CityClient> shards = new ArrayList<>();
        for(int i = 0; i < shardsNumber; i++) {
            String target = "localhost:" + (8980 + i);
            ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
            CityClient client = new CityClient(channel);

            shards.add(client);
        }
        return Collections.unmodifiableList(shards);
    }

    public static boolean rideConsensus(Ride ride) { return true; }
    public static boolean customerConsensus(CustomerRequest req) { return true; }

    public static int getShardNumber() {
        return 1;
    }

    public static Ride getLocalMatchingRide(Rout rout) {
        for (Ride ride : CityService.rides.values()) {
            System.out.println("ride id : " + ride.getId());
            System.out.println("-------------");
            if (CityUtil.isMatch(ride, rout) && rideConsensus(ride)) {
                System.out.println("match");
                 {
                    Ride updatedRide = Ride.newBuilder(ride)
                            .setTakenPlaces(ride.getTakenPlaces() + 1).build();
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

    public static void revertPath(Map<Integer, Ride> reservedRides, List<CityClient> shards){
        for (Map.Entry<Integer, Ride> entry : reservedRides.entrySet()) {
            if (entry.getKey() == getShardNumber()) {
                revertLocalCommit(entry.getValue());
            } else {
                shards.get(entry.getKey()).revertCommit(entry.getValue());
            }
        }
    }

    public static void printRide(Ride ride){
        System.out.println("Name: " + ride.getFirstName() + ride.getLastName());
        System.out.println("Phone number: " + ride.getPhoneNum());
        System.out.println("Date: " + ride.getDate());
        System.out.println("Path: " + ride.getSrcCity() + "to " + ride.getDstCity());
        System.out.println("Offered places: " + ride.getOfferedPlaces());
        System.out.println("Taken places: " + ride.getTakenPlaces());
        System.out.println("-------------");
    }

    public static void printCustomerRequest(CustomerRequest req){
        System.out.println("Customer's id: " + req.getId());
        System.out.print("Path: ");
        for (String path : req.getPathList()) {
            System.out.print(" " + path);
        }

        System.out.println("Date: " + req.getDate());

        Descriptors.FieldDescriptor fd = req.getDescriptorForType().findFieldByName("rides");
        if(req.hasField(fd)) {
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
