package server;

import ZkService.ZkServiceImpl;
import client.CityClient;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import generated.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static server.CityUtil.*;

public class CityService extends UberServiceGrpc.UberServiceImplBase {


    public int port;

    public static ZkServiceImpl zkService;

    public CityService(String port, String shardName, ZkServiceImpl zk) {

        zkService = zk;

        try {
            HostName = HostIP.getIp()+":" + port;
        }
    }


}
