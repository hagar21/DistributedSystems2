package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        String target1 = "localhost:8200";
//        ManagedChannel channel1 = ManagedChannelBuilder.forTarget(target1).usePlaintext().build();
//        ShardClient clientA1 = new ShardClient(channel1);

        String target3 = "localhost:8200";
        ManagedChannel channel3 = ManagedChannelBuilder.forTarget(target3).usePlaintext().build();
        ShardClient clientA2 = new ShardClient(channel3);

//        String target2 = "localhost:8202";
//        ManagedChannel channel2 = ManagedChannelBuilder.forTarget(target2).usePlaintext().build();
//        ShardClient clientB1 = new ShardClient(channel2);

//        String target4 = "localhost:8203";
//        ManagedChannel channel4 = ManagedChannelBuilder.forTarget(target3).usePlaintext().build();
//        ShardClient clientB2 = new ShardClient(channel3);

        // Call server streaming call
        Rest.entities.Ride restRide1 = new Rest.entities.Ride(
                "shai",
                "porath",
                "000",
                "a1",
                "a2",
                "1/1/21",
                4,
                5);

        Rest.entities.Ride restRide2 = new Rest.entities.Ride(
                "hagar",
                "sheffer",
                "111",
                "a2",
                "b",
                "1/1/21",
                4,
                5);


        List<String> path1 = new ArrayList<String>();
        path1.add("a1");
        path1.add("a2");
        path1.add("b");

        Rest.entities.CustomerRequest customerRequest1 = new Rest.entities.CustomerRequest(
                "tal", path1, "1/1/21");

        List<String> path2 = new ArrayList<String>();
        path2.add("a1");
        path2.add("b");

        Rest.entities.CustomerRequest customerRequest2 = new Rest.entities.CustomerRequest(
                "yarin", path2, "1/1/21");

//        clientA1.postRide(restRide1);
//        clientB1.postRide(restRide2);

//        List<Rest.entities.Ride> ridesList = clientB2.postPathPlanningRequest(customerRequest1);

//        System.out.println("Path planning returned:");
//
//        for(Rest.entities.Ride ride : ridesList) {
//            System.out.println(ride.getFirstName() + " " + ride.getLastName());
//        }
//
//        ridesList = clientA1.postPathPlanningRequest(customerRequest2);
//
//        System.out.println("Path planning returned:");
//
//        for(Rest.entities.Ride ride : ridesList) {
//            System.out.println(ride.getFirstName() + " " + ride.getLastName());
//        }

//        clientB2.snapshot();

        clientA2.snapshot();

        System.out.println("Waiting");
        Scanner sc= new Scanner(System.in);
        sc.next();
    }
}
