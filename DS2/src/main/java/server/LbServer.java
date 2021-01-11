package server;

import client.utils.LbShardConnections;
import generated.CityRequest;
import generated.CityRevertRequest;
import generated.Ride;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static server.CityServer.noRide;
import static server.CityServer.*;

public class LbServer {

    private final ConcurrentMap<String, LbShardConnections> shardConnections =
            new ConcurrentHashMap<>();

    private String MapCityToShard(String City) {
        switch (City) {
            case "A":
            case "B":
                return "Shard1";
            case "C":
                return "Shard"; //Shai - get list and use indexes
            default:
                System.out.println("No such city in system");
                return "";
        }
    }

    // Accept a user's request to join a ride and check if there is a relevant ride.
    public Ride cityRequestRide(CityRequest cityRequest) {
        System.out.println("LB got cityRequest to dest city " + cityRequest.getDestCityName() + " sending city request");
        System.out.println("------------");

        if (!shardConnections.containsKey(cityRequest.getDestCityName())) {
            System.out.println("error: LB got cityRequest to dest city " + cityRequest.getDestCityName() + " not in system");
            return noRide(); // Shai Ilegal ride - so it won't keep looking
        }

        CityServer destService = shardConnections.get(cityRequest.getDestCityName()).getNextService();
        return destService.cityRequestRide(cityRequest);
    }

    public void CityRevertRequestRide(CityRevertRequest revertRequest) {
        System.out.println("LB got CityRevertRequest to dest city " + revertRequest.getDestCityName() + " sending city request");
        System.out.println("------------");

        if (!shardConnections.containsKey(revertRequest.getDestCityName())) {
            System.out.println("error: LB got cityRequest to dest city " + revertRequest.getDestCityName() + " not in system");
        }

        CityServer destService = shardConnections.get(revertRequest.getDestCityName()).getNextService();
        destService.revertCommit(revertRequest.getRide());
    }
}
