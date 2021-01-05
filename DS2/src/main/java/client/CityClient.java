package client;

import generated.Ride;
import generated.CustomerRequest;
import generated.UberServiceGrpc;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;

import java.util.logging.Logger;

public class CityClient {
    private static final Logger logger = Logger.getLogger(CityClient.class.getName());

    private final UberServiceGrpc.UberServiceBlockingStub blockingStub;
    private final UberServiceGrpc.UberServiceStub asyncStub;

    public CityClient(Channel channel) {
        blockingStub = UberServiceGrpc.newBlockingStub(channel);
        asyncStub = UberServiceGrpc.newStub(channel);
    }
//
//    /**
//     * Blocking server-streaming example. Calls listFeatures with a rectangle of interest. Prints each
//     * response feature as it arrives.
//     */
//    public void listFeatures(int lowLat, int lowLon, int hiLat, int hiLon) {
//        Rectangle request =
//                Rectangle.newBuilder()
//                        .setLo(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
//                        .setHi(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
//        Iterator<Feature> features;
//        try {
//            features = blockingStub.listFeatures(request);
//            for (int i = 1; features.hasNext(); i++) {
//                Feature feature = features.next();
//                System.out.println("Name : " + feature.getName()+"\nlocation : \n" + feature.getLocation());
//                System.out.println("-------------");
//            }
//        } catch (StatusRuntimeException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void routeChat(){
//        StreamObserver<RouteNote> requestObserver =
//                asyncStub.routeChat(new StreamObserver<RouteNote>() {
//                    @Override
//                    public void onNext(RouteNote note) {
//                        System.out.println(note.getMessage());
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        System.out.println(t.getMessage());
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("Client side stream completed");
//                    }
//                });
//
//        try {
//            RouteNote[] requests =
//                    {
//                            newNote("First message", 0, 0),
//                            newNote("Second message", 0, 1),
//                            newNote("Third message", 1, 0),
//                            newNote("Fourth message", 1, 1)
//                    };
//
//            for (RouteNote request : requests) {
//                requestObserver.onNext(request);
//            }
//        } catch (RuntimeException e) {
//            // Cancel RPC
//            requestObserver.onError(e);
//            e.printStackTrace();
//        }
//        // Mark the end of requests
//        requestObserver.onCompleted();
//    }
//
//    private RouteNote newNote(String message, int lat, int lon) {
//        return RouteNote.newBuilder().setMessage(message)
//                .setLocation(Point.newBuilder().setLatitude(lat).setLongitude(lon).build()).build();
//    }
//
//    public void insertRideToDb(Ride request) {
//        try {
//            blockingStub.insertRideToDb(request);
//            System.out.println("client send insert to db request");
//            System.out.println("-------------");
//
//        } catch (StatusRuntimeException e) {
//            e.printStackTrace();
//        }
//    }

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
        CustomerRequest request = CustomerRequest.newBuilder()
                .setFirstName(customerRequest.getFirstName())
                .setLastName(customerRequest.getLastName()).build();
        try {
            //blockingStub.PostPathPlanningRequest(request);
            System.out.println("city client send post ride request");
            System.out.println("-------------");

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }
}
