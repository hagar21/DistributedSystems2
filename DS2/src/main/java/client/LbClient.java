package client;

import generated.UberServiceGrpc;

import io.grpc.Channel;
import io.grpc.StatusRuntimeException;

import java.util.logging.Logger;

import static server.utils.global.noRide;

import generated.*;

public class LbClient {
    private static final Logger logger = Logger.getLogger(ShardClient.class.getName());

    private final UberServiceGrpc.UberServiceBlockingStub blockingStub;
    private final UberServiceGrpc.UberServiceStub asyncStub;

    public LbClient(Channel channel) {
        blockingStub = UberServiceGrpc.newBlockingStub(channel);
        asyncStub = UberServiceGrpc.newStub(channel);
    }

    /*
    // LB func (REST->gRPC)
    public boolean postRide(Rest.entities.Ride ride) {

        try {
            Result result = blockingStub.lbPostRide(restToGrpcRide(ride));

            return result.getIsSuccess();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return false;
    }

    // LB func (REST->gRPC->REST)
    // Accept a user's request to join a ride and check if there is a relevant ride.
    public List<Rest.entities.Ride> postPathPlanningRequest(Rest.entities.CustomerRequest customerRequest) {
        System.out.println("lb client got PostPathPlanningRequest request");
        System.out.println("-------------");

        CustomerRequest request = CustomerRequest.newBuilder()
                .addAllPath(customerRequest.getPath())
                .setDate(customerRequest.getDepartureDate())
                .build();

        List<Rest.entities.Ride> rides = new ArrayList<>();
        Iterator<Ride> grpcRides;

        try {
            grpcRides = blockingStub.lbPostPathPlanningRequest(request);

            while(grpcRides.hasNext()) {
                Ride ride = grpcRides.next();
                rides.add(new Rest.entities.Ride(
                        ride.getFirstName(),
                        ride.getLastName(),
                        ride.getPhoneNum(),
                        ride.getSrcCity(),
                        ride.getDstCity(),
                        ride.getDate(),
                        ride.getOfferedPlaces(),
                        ride.getPd()));
            }
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return rides; /* shai still missing return empty in case of error
    }
    */


    public Ride reserveRide(Rout rout) {
        try {
            return blockingStub.reserveRide(rout);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return noRide();
    }


    /* Shai delete
    public void addCityClient(String shard, String targetHost) {

        ShardClient request = ShardClient.newBuilder()
                .setShard(shard)
                .setTargetHost(targetHost)
                .build();

        try {
            blockingStub.addCityClient(request);

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

     */

    // Accept a user's request to join a ride and check if there is a relevant ride.
    public Ride cityRequestRide(String shardName, Rout rout) {

        Ride ride = noRide();
        CityRequest cityRequest = CityRequest.newBuilder()
                .setDestCityName(shardName)
                .setRout(rout)
                .build();
        try {
            ride = blockingStub.cityRequestRide(cityRequest);

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return ride;
    }

    public Result CityRevertRequestRide(String destCityName, Ride ride) {

        CityRevertRequest revertRequest = CityRevertRequest.newBuilder()
                .setDestCityName(destCityName)
                .setRide(ride)
                .build();
        try {
            return blockingStub.cityRevertRequestRide(revertRequest);

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return Result.newBuilder().setIsSuccess(false).build();
    }

    public Ride restToGrpcRide(Rest.entities.Ride restRide) {
        Ride ride = Ride.newBuilder()
                .setFirstName(restRide.getFirstName())
                .setLastName(restRide.getLastName())
                .setPhoneNum(restRide.getPhoneNumber())
                .setSrcCity(restRide.getStartingPosition())
                .setDstCity(restRide.getEndingPosition())
                .setDate(restRide.getDepartureDate())
                .setOfferedPlaces(restRide.getVacancies())
                .setTakenPlaces(0)
                .setPd(restRide.getPd()).build();

        return ride;
    }
}
