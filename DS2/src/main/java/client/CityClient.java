package client;

import generated.CustomerRequest;
import generated.Ride;
import generated.Rout;
import generated.UberServiceGrpc;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import server.CityUtil;

import java.util.ArrayList;
import java.util.List;
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
    public List<Rest.entities.Ride> PostPathPlanningRequest(Rest.entities.CustomerRequest customerRequest) {

        List<Rest.entities.Ride> rides = new ArrayList<Rest.entities.Ride>();

        CustomerRequest request = CustomerRequest.newBuilder()
                .setPath(customerRequest.getPath())
                .setDate(customerRequest.getDepartureDate()).build();

        try {
            asyncStub.postPathPlanningRequest(request, new StreamObserver<Ride>() {
                @Override
                public void onNext(Ride ride) {
                    if (!ride.isInitialized()) /* shai, if not found need to throw error */
                    {
                        return;
                    }

                    rides.add(new Rest.entities.Ride(
                            ride.getFirstName(),
                            ride.getLastName(),
                            ride.getPhoneNum(),
                            ride.getSrcCity(),
                            ride.getDstCity(),
                            ride.getDate(),
                            ride.getVacancies(),
                            ride.getPd()));
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println(t.getMessage());
                    System.out.println("No ride found, path planning failed"); /* shai not ride*/
                }

                @Override
                public void onCompleted() {
                    System.out.println("path planning complete for client");
                }
            });

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
