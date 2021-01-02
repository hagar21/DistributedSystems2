package server;

public class DbMain {
    public static void main(String[] args) throws Exception {
        // String target = "localhost:8980";
        // ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        // UberClient client = new UberClient(channel);

        DbServer server = new DbServer(8980);
        server.start();
        System.out.println("DB server started");
        server.blockUntilShutdown();
    }
}
