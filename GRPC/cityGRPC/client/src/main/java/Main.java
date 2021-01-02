import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:8980";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        UberClient client = new UberClient(channel);
        // Call server streaming call

        client.postRide("shai", "porath", 123, "haifa", "monash", "1/1/21", 4, 5);
        // Call bi-directional call
        //client.routeChat();
        System.out.println("Waiting");
        Scanner sc= new Scanner(System.in);
        sc.next();
    }
}
