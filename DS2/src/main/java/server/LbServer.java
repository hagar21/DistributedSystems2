package server;

import client.CityClient;
import client.utils.LbShardConnections;
import generated.CityRequest;
import generated.CityRevertRequest;
import generated.Ride;
import generated.ShardClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static server.CityServer.*;
import static server.utils.global.noRide;

public class LbServer {

    private final ConcurrentMap<String, LbShardConnections> shardConnections =
            new ConcurrentHashMap<>();

    LbServer() {} //port 8990

    public void addCityClient(ShardClient ShardClient) {

        if (ShardClient.getShard().equals("")) {
            System.out.println("error: LB AddCityClient to city" + ShardClient.getShard() + " not in system");
            return;
        }

        LbShardConnections lbsc = shardConnections.get(ShardClient.getShard());
        if (lbsc == null) {
            System.out.println("LB AddCityClient creating new LbShardConnections to shard " + ShardClient.getShard());
            lbsc = new LbShardConnections();
            shardConnections.put(ShardClient.getShard(), lbsc);
        }

        ManagedChannel channel = ManagedChannelBuilder.forTarget(ShardClient.getTargetHost()).usePlaintext().build();
        CityClient c = new CityClient(channel);
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
