package server;

public class CityMain3 {
    public static void main(String[] args) throws Exception {
        CityServer server = new CityServer(8992);
        server.start();
        System.out.println("Server started");
        server.blockUntilShutdown();
    }
}
