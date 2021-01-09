package server;

import client.CityClient;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import generated.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static server.CityUtil.*;

public class CityService extends UberServiceGrpc.UberServiceImplBase {

    public static final ConcurrentMap<String, Ride> rides =
            new ConcurrentHashMap<>();
    public static final ConcurrentMap<String, CustomerRequest> customerRequests =
            new ConcurrentHashMap<>();
    private final List<CityClient> shards;
    public static int port;

    public CityService(int port) {
        shards = initShards(port);
        CityService.port = port;
    }

    @Override
    public void postRide(Ride request, StreamObserver<Result> responseObserver) {
        System.out.println("City server got post ride request");
        System.out.println("-------------");

        String rideId = request.getFirstName() + request.getLastName() + request.getDate() + request.getSrcCity() + request.getDstCity();
        System.out.println(rideId);

        Result.Builder res = Result.newBuilder();
        if(rides.containsKey(rideId)) {
            responseObserver.onNext(res.setIsSuccess(false).build());
            responseObserver.onCompleted();
        }

        Ride ride = Ride.newBuilder(request).setId(rideId).build();
        if(rideConsensus(ride)) {
            rides.put(ride.getId(), ride);
        }
        responseObserver.onNext(res.setIsSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void postPathPlanningRequest(
            CustomerRequest request, StreamObserver<Ride> responseObserver) {
        System.out.println("City server got postPathPlanningRequest request");
        System.out.println("-------------");
        // map of (ride: shardId)
        Map<Ride, Integer> reservedRides = new HashMap<>();
        String src, dst;
        Rout.Builder routBuilder = Rout.newBuilder()
                .setName(request.getName())
                .setDate(request.getDate());
        Rout rout;

        for(int i = 0; i < request.getPathCount() - 1; i++) {
            src = request.getPath(i);
            dst = request.getPath(i + 1);

            rout = routBuilder
                    .setSrcCity(src)
                    .setDstCity(dst).build();

            // check for relevant rides in local db
            Ride foundRide = getLocalMatchingRide(rout);
            if (!foundRide.equals(noRide())) {
                reservedRides.put(foundRide, getShardNumber());
                continue;
            }

            // check in other shards
            ShardRide remoteRide = getRemoteMatchingRide(shards, rout);

            if(remoteRide.ride.equals(noRide())) {
                revertPath(reservedRides, shards);
                responseObserver.onNext(noRide());
                responseObserver.onCompleted();
                return;
            }
            reservedRides.put(remoteRide.ride, remoteRide.shardId);
        }

        // Entire path satisfied
        String id = request.getName() + request.getPathList().toString() + request.getDate();
        CustomerRequest updatedRequest = CustomerRequest.newBuilder(request).addAllRides(reservedRides.keySet()).setId(id).build();
        if(customerConsensus(updatedRequest)) {
            customerRequests.put(updatedRequest.getId(), updatedRequest);
            for (Ride ride : reservedRides.keySet()) {
                responseObserver.onNext(ride);
            }
        }

        responseObserver.onCompleted();
    }

    @Override
    public void reserveRide(Rout request, StreamObserver<Ride> responseObserver){
        Ride ride = getLocalMatchingRide(request);
        responseObserver.onNext(ride);
        responseObserver.onCompleted();
    }

    @Override
    public void revertCommit(Ride request, StreamObserver<Result> responseObserver) {
        revertLocalCommit(request);
        Result res = Result.newBuilder().setIsSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void snapshot(com.google.protobuf.Empty request,
                         StreamObserver<com.google.protobuf.Empty> responseObserver) {

        System.out.println("Snapshot of published services:");
        System.out.println("--------------------------------");

        for (Ride ride : rides.values()) {
            printRide(ride);
        }

        System.out.println("Snapshot of requested services:");
        System.out.println("--------------------------------");

        for (CustomerRequest req : customerRequests.values()) {
            printCustomerRequest(req);
        }
        responseObserver.onCompleted();
    }
}
