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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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

    public boolean postRide(Ride ride) {
        try {
            Result result = blockingStub.postRide(ride);

            System.out.println("city client send post ride request");
            System.out.println("-------------");
            return result.getIsSuccess();

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean postCustomerRequest(CustomerRequest request) {
        try {
            Result result = blockingStub.postCustomerRequest(request);

            System.out.println("city client send post ride request");
            System.out.println("-------------");
            return result.getIsSuccess();

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void postRide(Rest.entities.Ride ride) {
        try {
            boolean result = postRide(restToGrpcRide(ride));

            if (!result) {
                throw new Rest.utils.RideAlreadyExistsException();
            }

        } catch (RideAlreadyExistsException e) {
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

    // Accept a user's request to join a ride and check if there is a relevant ride.
    public Ride cityRequestRide(CityRequest cityRequest) {
        System.out.println("LB got cityRequest to dest city " + cityRequest.getDestCityName() + " sending city request");
        System.out.println("------------");

        if (!CityConnections.containsKey(cityRequest.getDestCityName())) {
            System.out.println("error: LB got cityRequest to dest city " + cityRequest.getDestCityName() + " not in system");
            return noRide(); // Shai Ilegal ride - so it won't keep looking
        }

        // Shai should be done with lock
        CityServer destService = CityConnections.get(cityRequest.getDestCityName()).getNextService();
        return destService.cityRequestRide(cityRequest);
    }

    public void CityRevertRequestRide(CityRevertRequest revertRequest) {
        System.out.println("LB got CityRevertRequest to dest city " + revertRequest.getDestCityName() + " sending city request");
        System.out.println("------------");

        if (!CityConnections.containsKey(revertRequest.getDestCityName())) {
            System.out.println("error: LB got cityRequest to dest city " + revertRequest.getDestCityName() + " not in system");
        }

        // Shai should be done with lock
        CityServer destService = CityConnections.get(revertRequest.getDestCityName()).getNextService();
        destService.cityRevertRequestRide(revertRequest);
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
