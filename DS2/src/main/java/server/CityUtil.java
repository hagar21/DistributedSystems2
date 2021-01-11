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


}
