package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String target1 = "localhost:8990";
        ManagedChannel channel1 = ManagedChannelBuilder.forTarget(target1).usePlaintext().build();
        ShardClient client1 = new ShardClient(channel1);

        String target2 = "localhost:8991";
        ManagedChannel channel2 = ManagedChannelBuilder.forTarget(target2).usePlaintext().build();
        ShardClient client2 = new ShardClient(channel2);

        String target3 = "localhost:8992";
        ManagedChannel channel3 = ManagedChannelBuilder.forTarget(target3).usePlaintext().build();
        ShardClient client3 = new ShardClient(channel3);

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
        client1.postRide(restRide1);
        client2.postRide(restRide2);

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
        client3.postRide(restRide3);

        client1.snapshot();
        client2.snapshot();
        client3.snapshot();

        System.out.println("Waiting");
        Scanner sc= new Scanner(System.in);
        sc.next();
    }
}
