package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:8990";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        CityClient client = new CityClient(channel);
        // Call server streaming call
        Rest.entities.Ride restRide = new Rest.entities.Ride("shai", "porath", "porath", "haifa", "monash", "1/1/21", 4, 5);
        client.postRide(restRide);
        // Call bi-directional call
        //client.routeChat();
        System.out.println("Waiting");
        Scanner sc= new Scanner(System.in);
        sc.next();
    }
}
