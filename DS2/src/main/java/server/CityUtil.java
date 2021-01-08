package server;

import client.CityClient;
import generated.CustomerRequest;
import generated.Ride;
import generated.Rout;
import generated.UberServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

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
        return Ride.newBuilder().setId(-1).build();
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

    public static boolean consensus(Ride ride) {
        return true;
    }

    public static int getShardNumber() {
        return 1;
    }

    public static Ride getLocalMatchingRide(Rout rout) {
        for (Ride ride : CityService.rides.values()) {
            System.out.println("ride id : " + ride.getId());
            System.out.println("-------------");
            if (CityUtil.isMatch(ride, rout) && CityUtil.consensus(ride)) {
                System.out.println("match");
                 {
                    Ride updatedRide = Ride.newBuilder(ride)
                            .setVacancies(ride.getVacancies() - 1).build();
                     CityService.rides.put(updatedRide.getId(), updatedRide);
                     return updatedRide;
                }
            }
        }
        return CityUtil.noRide();
    }

    public static Ride getRemoteMatchingRide(List<CityClient> shards, Rout rout) {
        int index = 0;
        for(CityClient client : shards){
            Ride ride = client.reserveRide(rout);
            if(!ride.equals(CityUtil.noRide())) {
                return ride;
                // should also return index
            }
            index++;
        }
        return CityUtil.noRide();
    }
}
