package server;

import generated.CustomerRequest;
import generated.Ride;
import generated.Rout;

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

    public static boolean isMatch(Ride ride, CustomerRequest req) {
        Point rideSrc = mapCityToLocation(ride.getSrcCity());
        Point rideDst = mapCityToLocation(ride.getDstCity());
        Point reqSrc = mapCityToLocation(req.getSrcCity());
        Point reqDst = mapCityToLocation(req.getDstCity());

        Point p0;
        if(rideSrc.equals(reqSrc)) {
            p0 = mapCityToLocation(req.getDstCity());
        } else if (rideDst.equals(reqDst)) {
            p0 = mapCityToLocation(req.getSrcCity());
        } else {
            return false;
        }

        int numerator = Math.abs((rideDst.x - rideSrc.x) * (rideSrc.y - p0.y) - (rideSrc.x-p0.x) * (rideDst.y - rideSrc.y));
        double denominator = Math.sqrt(Math.pow(rideDst.x - rideSrc.x, 2)+ Math.pow(rideDst.y - rideSrc.y, 2));
        return (numerator / denominator) <= ride.getPd();
    }

    public static List<Ride> getExistingRides(Rout rout) {
        // mock db
        return new ArrayList<Ride>() {
            {
                add(
                        Ride.newBuilder()
                        .setDate("1/1/21")
                        .setFirstName("a")
                        .setLastName("a")
                        .setPhoneNum(1)
                        .setPd(1)
                        .setVacancies(3)
                        .setSrcCity("haifa")
                        .setDstCity("karkur")
                        .setId(1)
                        .build()
                );
                add(
                        Ride.newBuilder()
                                .setDate("1/1/21")
                                .setFirstName("b")
                                .setLastName("b")
                                .setPhoneNum(2)
                                .setPd(1)
                                .setVacancies(3)
                                .setSrcCity("karkur")
                                .setDstCity("monash")
                                .setId(2)
                                .build()
                );
            }
        };
    }

    public static Ride noRide() {
        return Ride.newBuilder().setId(-1).build();
    }
}
