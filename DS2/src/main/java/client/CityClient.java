package client;

import com.google.protobuf.Empty;
import generated.CustomerRequest;
import generated.Ride;
import generated.Rout;
import generated.UberServiceGrpc;
import generated.CityRequest;

import client.utils.*;

import Rest.utils.RideAlreadyExistsException;
import generated.*;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;
import server.CityServer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import static server.CityServer.*;

public class CityClient {
    private static final Logger logger = Logger.getLogger(CityClient.class.getName());

    private final UberServiceGrpc.UberServiceBlockingStub blockingStub;
    private final UberServiceGrpc.UberServiceStub asyncStub;

    public CityClient(Channel channel) {
        blockingStub = UberServiceGrpc.newBlockingStub(channel);
        asyncStub = UberServiceGrpc.newStub(channel);
    }

    public void postRide(Rest.entities.Ride restRide) {
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

        try {
            Result result = blockingStub.postRide(ride);
            if (!result.getIsSuccess())
            {
                throw new Rest.utils.RideAlreadyExistsException();
            }
            System.out.println("city client send post ride request");
            System.out.println("-------------");

        } catch (StatusRuntimeException | RideAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    // Accept a user's request to join a ride and check if there is a relevant ride.
    public List<Rest.entities.Ride> postPathPlanningRequest(Rest.entities.CustomerRequest customerRequest) {
        System.out.println("city client send PostPathPlanningRequest request");
        System.out.println("-------------");

        CustomerRequest request = CustomerRequest.newBuilder()
                .addAllPath(customerRequest.getPath())
                .setDate(customerRequest.getDepartureDate())
                .build();

        List<Rest.entities.Ride> restRides = new ArrayList<>();
        Iterator<Ride> grpcRides;

        try {
            grpcRides = blockingStub.postPathPlanningRequest(request);

            while(grpcRides.hasNext()) {
                Ride ride = grpcRides.next();
                restRides.add(new Rest.entities.Ride(
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

        return restRides; /* shai still missing return empty in case of error */
    }

    public Ride reserveRide(Rout rout) {
        try {
            return blockingStub.reserveRide(rout);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return noRide();
    }


    public void revertCommit(Ride ride) {
        try {
            blockingStub.revertCommit(ride);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    public void snapshot() {
        com.google.protobuf.Empty req = Empty.newBuilder().build();
        try {
            blockingStub.snapshot(req);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }
}
