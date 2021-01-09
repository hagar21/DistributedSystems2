package server;

public class CityMain2 {
    public static void main(String[] args) throws Exception {
        CityServer server = new CityServer(8991);
        server.start();
        System.out.println("Server started");
        server.blockUntilShutdown();
    }
}
