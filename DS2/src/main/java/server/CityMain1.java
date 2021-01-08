package server;

public class CityMain1 {
    public static void main(String[] args) throws Exception {
        CityServer server = new CityServer(8990);
        server.start();
        System.out.println("Server started");
        server.blockUntilShutdown();
    }
}
