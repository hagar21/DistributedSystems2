package server;

import client.CityClient;
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

    LbServer() {} //port 8990


    public void AddCityClient(String city, CityClient c) {

        String shard = MapCityToShard(city);
        if (shard.equals("")) {
            System.out.println("error: LB AddCityClient to city" + city + " not in system");
            return;
        }

        LbShardConnections lbsc = shardConnections.get(shard);
        if (lbsc == null) {
            System.out.println("LB AddCityClient creating new LbShardConnections to shard " + shard + " city " + city);
            lbsc = new LbShardConnections();
            shardConnections.put(shard, lbsc);
        }

        lbsc.AddToShard(c);
    }

    private String MapCityToShard(String City) {
        switch (City) {
            case "A":
            case "B":
                return "Shard1";
            case "C":
                return "Shard2";
            default:
                System.out.println("No such city in system");
                return "";
        }
    }

    // Accept a user's request to join a ride and check if there is a relevant ride.
    public Ride cityRequestRide(CityRequest cityRequest) {
        System.out.println("LB got cityRequest to dest city " + cityRequest.getDestCityName() + " sending city request");
        System.out.println("------------");

        String shard = MapCityToShard(cityRequest.getDestCityName());

        if (!shardConnections.containsKey(shard)) {
            System.out.println("error: LB got cityRequest to dest city " + cityRequest.getDestCityName() + " not in system");
            return noRide(); // Shai Ilegal ride - so it won't keep looking
        }

        CityClient destService = shardConnections.get(shard).getNextService();
        return destService.cityRequestRide(cityRequest);
    }

    public void CityRevertRequestRide(CityRevertRequest revertRequest) {
        System.out.println("LB got CityRevertRequest to dest city " + revertRequest.getDestCityName() + " sending city request");
        System.out.println("------------");

        String shard = MapCityToShard(revertRequest.getDestCityName());

        if (!shardConnections.containsKey(shard)) {
            System.out.println("error: LB got CityRevertRequest to dest city " + revertRequest.getDestCityName() + " not in system");
            return;
        }

        CityClient destService = shardConnections.get(shard).getNextService();
        destService.cityRevertRequestRide(revertRequest);
    }
}
