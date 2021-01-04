package client;

import generated.*;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;

import java.util.Iterator;
import java.util.logging.Logger;

public class DbClient {
    private static final Logger logger = Logger.getLogger(DbClient.class.getName());

    private final UberServiceGrpc.UberServiceBlockingStub blockingStub;
    private final UberServiceGrpc.UberServiceStub asyncStub;

    public DbClient(Channel channel) {
        blockingStub = UberServiceGrpc.newBlockingStub(channel);
        asyncStub = UberServiceGrpc.newStub(channel);
    }

    public void insertRideToDb(Ride ride) {
        try {
            blockingStub.insertRideToDb(ride);
            System.out.println("db client send post ride request");
            System.out.println("-------------");

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    public void getExistingRides(String date, String src, String dst) {
        Rout rout = Rout.newBuilder()
                .setDate(date)
                .setSrcCity(src)
                .setDstCity(dst).build();
        try {

            System.out.println("db client send getExistingRides request");
            System.out.println("-------------");

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }
}
