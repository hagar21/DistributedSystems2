package server;

import client.CityClient;
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

    public static final ConcurrentMap<Integer, Ride> rides =
            new ConcurrentHashMap<>();
    public static final ConcurrentMap<Integer, CustomerRequest> customerRequests =
            new ConcurrentHashMap<>();
    private final List<CityClient> shards;

    public CityService() {
        shards = CityUtil.initShards();
        System.out.println("City server is up!");
        System.out.println("-------------");
    }

    @Override
    public void postRide(Ride request, StreamObserver<Result> responseObserver) {
        System.out.println("City server got post ride request");
        System.out.println("-------------");

        rides.put(request.getId(), request);
        Result res = Result.newBuilder().setIsSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CustomerRequest> postPathPlanningRequest(
            StreamObserver<Ride> responseObserver) {

        return new StreamObserver<CustomerRequest>() {
            @Override
            public void onNext(CustomerRequest request) {
                System.out.println("Request of " + request.getFirstName() + " " + request.getLastName());
                Rout rout = Rout.newBuilder()
                        .setDate(request.getDate())
                        .setDstCity(request.getDstCity())
                        .setSrcCity(request.getSrcCity()).build();

                // check for relevant rides in local db
                for (Ride ride : CityService.rides.values()) {
                    System.out.println("ride id : " + ride.getId());
                    System.out.println("-------------");
                    if (CityUtil.isMatch(ride, request)) {
                        System.out.println("match");
                        // get consensus to use this ride
                        if (CityUtil.consensus()) {
                            Ride updatedRide = Ride.newBuilder()
                                    .setId(ride.getId())
                                    .setFirstName(ride.getFirstName())
                                    .setLastName(ride.getLastName())
                                    .setPhoneNum(ride.getPhoneNum())
                                    .setSrcCity(ride.getSrcCity())
                                    .setDstCity(ride.getDstCity())
                                    .setDate(ride.getDate())
                                    .setVacancies(ride.getVacancies() - 1)
                                    .setPd(ride.getPd()).build(); // shai - update pd
                            CityService.rides.put(updatedRide.getId(), updatedRide);

                            CustomerRequest updatedRequest = CustomerRequest.newBuilder()
                                    .setId(ride.getId())
                                    .setFirstName(ride.getFirstName())
                                    .setLastName(ride.getLastName())
                                    .setPhoneNum(ride.getPhoneNum())
                                    .setSrcCity(ride.getSrcCity())
                                    .setDstCity(ride.getDstCity())
                                    .setDate(ride.getDate())
                                    .setRideId(updatedRide.getId()).build();
                            CityService.customerRequests.put(updatedRequest.getId(), updatedRequest);

                            responseObserver.onNext(updatedRide);
                            return;
                        }
                    }
                }

                // ask other shards
                for(CityClient client : shards){
                    Ride ride = client.postCustomerRequest(request);
                    if(!ride.equals(CityUtil.noRide())) {
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
