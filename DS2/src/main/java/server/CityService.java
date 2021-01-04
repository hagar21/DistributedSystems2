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

        return new StreamObserver<CustomerRequest>() {
            @Override
            public void onNext(CustomerRequest req) {
                System.out.println("Request of " + req.getFirstName() + " " + req.getLastName());
                Rout rout = Rout.newBuilder()
                        .setDate(req.getDate())
                        .setDstCity(req.getDstCity())
                        .setSrcCity(req.getSrcCity()).build();

                // check for relevant rides in local db,
                // if there is no ride check in other cities
                List<Ride> rides = CityUtil.getExistingRides(rout);

                for (Ride ride : rides) {
                    System.out.println("ride id : " + ride.getId());
                    System.out.println("-------------");
                    if (CityUtil.isMatch(ride, req)) {
                        // get consensus to use this ride
                        // save to db the updated ride
                        responseObserver.onNext(ride);
                        return;
                    }
                }
                responseObserver.onNext(CityUtil.noRide());
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
}
