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
    public void postPathPlanningRequest(
            CustomerRequest request, StreamObserver<Ride> responseObserver) {

        // map of (shardId: rideId)
        Map<Integer, Integer> reservedRides = new HashMap<>();
        String src, dst;
        Rout rout;

        outerloop:
        for(int i = 0; i < request.getPathCount() - 1; i++) {
            src = request.getPath(i);
            dst = request.getPath(i+1);

            rout = Rout.newBuilder()
                    .setDate(request.getDate())
                    .setDstCity(src)
                    .setSrcCity(dst).build();
            // check for relevant rides in local db
            Ride foundRide = CityUtil.getLocalMatchingRide(rout);
            if (!foundRide.equals(CityUtil.noRide())) {
                reservedRides.put(CityUtil.getShardNumber(), foundRide.getId());
                continue;
            }

            // check in other shards
            for(CityClient client : shards){
                Ride ride = client.reserveRide(rout);
                if(!ride.equals(CityUtil.noRide())) {
                    Ride updatedRide = Ride.newBuilder(ride)
                            .setVacancies(ride.getVacancies() - 1).build();
                    rides.put(updatedRide.getId(), updatedRide);
                    reservedRides.put(CityUtil.getShardNumber(), updatedRide.getId());
                    break;
                }
            }
            responseObserver.onNext(CityUtil.noRide());
        }
                CityService.customerRequests.put(updatedRequest.getId(), updatedRequest);
    }
}
