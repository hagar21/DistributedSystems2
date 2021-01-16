package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String target1 = "localhost:8200";
        ManagedChannel channel1 = ManagedChannelBuilder.forTarget(target1).usePlaintext().build();
        ShardClient clientA1 = new ShardClient(channel1);

        String target2 = "localhost:8201";
        ManagedChannel channel2 = ManagedChannelBuilder.forTarget(target2).usePlaintext().build();
        ShardClient clientB1 = new ShardClient(channel2);

        String target3 = "localhost:8202";
        ManagedChannel channel3 = ManagedChannelBuilder.forTarget(target3).usePlaintext().build();
        ShardClient clientA2 = new ShardClient(channel3);

        String target4 = "localhost:8203";
        ManagedChannel channel4 = ManagedChannelBuilder.forTarget(target3).usePlaintext().build();
        ShardClient clientB2 = new ShardClient(channel3);

        // Call server streaming call
        Rest.entities.Ride restRide1 = new Rest.entities.Ride(
                "shai",
                "porath",
                "000",
                "haifa",
                "monash",
                "1/1/21",
                4,
                5);

        Rest.entities.Ride restRide2 = new Rest.entities.Ride(
                "hagar",
                "sheffer",
                "111",
                "haifa",
                "karkur",
                "1/1/21",
                4,
                5);
        clientA1.postRide(restRide1);
        clientB1.postRide(restRide2);

        clientA2.snapshot();

        List<String> path = new ArrayList<String>();
        path.add("haifa");
        path.add("monash");
        path.add("karkur");


        Rest.entities.CustomerRequest customerRequest = new Rest.entities.CustomerRequest(
                "tal", path, "1/1/21");

        /*
        List<Rest.entities.Ride> ridesList = client3.postPathPlanningRequest(customerRequest);

        System.out.println("Path planning returned:");

        for(Rest.entities.Ride ride : ridesList) {
            System.out.println(ride.getFirstName() + " " + ride.getLastName());
        }

         */

        Rest.entities.Ride restRide3 = new Rest.entities.Ride(
                "tal",
                "gelbard",
                "222",
                "haifa",
                "karkur",
                "1/1/21",
                4,
                5);
        clientA2.postRide(restRide3);

        clientA1.snapshot();
        clientA2.snapshot();
        clientB1.snapshot();
        clientB2.snapshot();

        System.out.println("Waiting");
        Scanner sc= new Scanner(System.in);
        sc.next();
    }
}
