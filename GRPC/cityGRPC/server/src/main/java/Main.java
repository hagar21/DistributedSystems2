import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Main {
    public static void main(String[] args) throws Exception {
        // String target = "localhost:8980";
        // ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        // UberClient client = new UberClient(channel);

        UberServer server = new UberServer(8980);
        server.start();
        System.out.println("Server started");
        server.blockUntilShutdown();
    }
}
