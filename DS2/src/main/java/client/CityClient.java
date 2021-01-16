package client;

import com.google.protobuf.Empty;
import generated.*;
import generated.UberServiceGrpc;

import Rest.utils.RideAlreadyExistsException;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import static server.utils.global.noRide;

public class CityClient {
    private static final Logger logger = Logger.getLogger(CityClient.class.getName());

    private final UberServiceGrpc.UberServiceBlockingStub blockingStub;
    private final UberServiceGrpc.UberServiceStub asyncStub;

    public CityClient(Channel channel) {
        blockingStub = UberServiceGrpc.newBlockingStub(channel);
        asyncStub = UberServiceGrpc.newStub(channel);
    }

    // City func
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

    // City func
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

    // LB func (REST->gRPC)
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

    // LB func (REST->gRPC->REST)
    // Accept a user's request to join a ride and check if there is a relevant ride.
    public List<Ride> postPathPlanningRequest(CustomerRequest customerRequest) {
        System.out.println("city client send PostPathPlanningRequest request");
        System.out.println("-------------");

        Iterator<Ride> grpcRides;
        List<Ride> rides = new ArrayList<>();

        try {
            grpcRides = blockingStub.postPathPlanningRequest(customerRequest);

            while(grpcRides.hasNext()) {
                rides.add(grpcRides.next());
            }
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return rides; /* shai still missing return empty in case of error */
    }

    public Ride reserveRide(Rout rout) {
        try {
            return blockingStub.reserveRide(rout);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return noRide();
    }


    public boolean revertCommit(Ride ride) {
        try {
            blockingStub.revertCommit(ride);
            return true;
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            return false;
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

    // City2City request (gRPC)
    // Accept a user's request to join a ride and check if there is a relevant ride.
    public Ride cityRequestRide(CityRequest cityRequest) {

        try {
            return blockingStub.reserveRide(cityRequest.getRout());
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return noRide();
    }

    // City2City request (gRPC)
    public void cityRevertRequestRide(CityRevertRequest revertRequest) {
        try {
            blockingStub.revertCommit(revertRequest.getRide());
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
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

    public boolean deleteCustomerRequest(CustomerRequest request) {
        try {
            blockingStub.deleteCustomerRequest(request);
            return true;
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }
}
