package server;

import client.DbClient;
import generated.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class CityService extends UberServiceGrpc.UberServiceImplBase {

    private final DbClient client;

    public CityService() {
        String target = "localhost:8980";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        DbClient client = new DbClient(channel);
        this.client = client;
        System.out.println("City server is up!");
        System.out.println("-------------");
    }

    // city server
    @Override
    public void postRide(Ride request, StreamObserver<Result> responseObserver) {
        // this.client.insertRideToDb(request);
        System.out.println("City server got post ride request");
        System.out.println("-------------");

        this.client.insertRideToDb(request);
        Result res = Result.newBuilder().setIsSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

//    // Accept a user's request to join a ride and check if there is a relevant ride.
//    // lb->cityS
    @Override
    public StreamObserver<CustomerRequest> postPathPlanningRequest(
            StreamObserver<Ride> responseObserver) {
        Iterator<Ride> rides = this.client.getExistingRides();
        while (rides.hasNext()) {
            Ride ride = rides.next();
            System.out.println("ride id : " + ride.getId());
            System.out.println("-------------");
        }

        return new StreamObserver<RouteNote>() {
            @Override
            public void onNext(RouteNote note) {
                System.out.println("Request of " + note.getMessage());
                RouteNote serverNote = RouteNote.newBuilder()
                        .setLocation(note.getLocation())
                        .setMessage("Respond to " + note.getMessage()).build();
                responseObserver.onNext(serverNote);
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                System.out.println("Server side stream completed");
                responseObserver.onCompleted();
            }
        };
    }
//
//    // Accept a user's request to join a ride and check if there is a relevant ride.
//    // lb->cityS
//    rpc PostPathPlanningRequest(stream CustomerRequest) returns (Result) {}
//
//    // Accept a list of cities and return all rides contains all these cities.
//    rpc GetExistingRides(Rout) returns (stream Ride) {}
//
//    // Update a ride's record to update current vacancies.
//    rpc UpdateRide(Ride) returns (Result) {}
//
//    // Accept a user's ride and save it in the DB.
//    rpc InsertRideToDb(Ride) returns (Result) {}
//
//    @Override
//    public void PostPathPlanningRequest(Ride request, StreamObserver<CustomerRequest> responseObserver) {
//        // Calculate the rectangle boundaries
//        int left = min(request.getLo().getLongitude(), request.getHi().getLongitude());
//        int right = max(request.getLo().getLongitude(), request.getHi().getLongitude());
//        int top = max(request.getLo().getLatitude(), request.getHi().getLatitude());
//        int bottom = min(request.getLo().getLatitude(), request.getHi().getLatitude());
//
//        for (Feature feature : features) {
//            int lat = feature.getLocation().getLatitude();
//            int lon = feature.getLocation().getLongitude();
//            // Check whether the feature is in the rectangle
//            if (lon >= left && lon <= right && lat >= bottom && lat <= top) {
//                responseObserver.onNext(feature);
//            }
//        }
//        responseObserver.onCompleted();
//    }
//
//    // Bidirectional streaming
//    @Override
//    public StreamObserver<RouteNote> routeChat(final StreamObserver<RouteNote> responseObserver) {
//        return new StreamObserver<RouteNote>() {
//            @Override
//            public void onNext(RouteNote note) {
//                System.out.println("Request of " + note.getMessage());
//                RouteNote serverNote = RouteNote.newBuilder()
//                        .setLocation(note.getLocation())
//                        .setMessage("Respond to " + note.getMessage()).build();
//                responseObserver.onNext(serverNote);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("Server side stream completed");
//                responseObserver.onCompleted();
//            }
//        };
}
