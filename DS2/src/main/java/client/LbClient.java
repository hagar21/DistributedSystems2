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

public class LbClient {
    private static final Logger logger = Logger.getLogger(CityClient.class.getName());

    private final UberServiceGrpc.UberServiceBlockingStub blockingStub;
    private final UberServiceGrpc.UberServiceStub asyncStub;

    public LbClient(Channel channel) {
        blockingStub = UberServiceGrpc.newBlockingStub(channel);
        asyncStub = UberServiceGrpc.newStub(channel);
    }

    // Accept a user's request to join a ride and check if there is a relevant ride.
    public Ride cityRequestRide(CityRequest cityRequest) {

        Ride ride = noRide();

        try {
            ride = blockingStub.cityRequestRide(cityRequest);

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return ride;
    }

    public void CityRevertRequestRide(CityRevertRequest revertRequest) {

        try {
            blockingStub.cityRevertRequestRide(revertRequest);

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

}