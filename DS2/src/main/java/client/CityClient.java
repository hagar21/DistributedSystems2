package client;

import generated.CustomerRequest;
import generated.Ride;
import generated.Rout;
import generated.UberServiceGrpc;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;
import server.CityUtil;

import java.util.logging.Logger;

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
                .setPhoneNum(restRide.getVacancies()) /* shai change type */
                .setSrcCity(restRide.getStartingPosition())
                .setDstCity(restRide.getEndingPosition())
                .setDate(restRide.getDepartureDate())
                .setVacancies(restRide.getVacancies())
                .setPd(restRide.getPd()).build();
        try {
            blockingStub.postRide(ride);
            System.out.println("city client send post ride request");
            System.out.println("-------------");

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    // Accept a user's request to join a ride and check if there is a relevant ride.
    public void PostPathPlanningRequest(Rest.entities.CustomerRequest customerRequest) {
        // shai
    }

    public Ride reserveRide(Rout rout) {
        try {
            return blockingStub.reserveRide(rout);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return CityUtil.noRide();
    }


    public void revertCommit(Ride ride) {
        try {
            blockingStub.revertCommit(ride);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

    }
}
