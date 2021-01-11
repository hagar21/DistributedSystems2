package client.utils;

import server.CityServer;

import java.util.ArrayList;
import java.util.List;

public class LBCityConnections {
    public static List<CityServer> cityServices;
    public static int rrIdx;

    LBCityConnections() {
        cityServices = new ArrayList<>();
        rrIdx = 0;
    }

    public CityServer getNextService() {
        return cityServices.get(rrIdx++);
    }
}
