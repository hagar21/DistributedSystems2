package generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * Interface exported by the server.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.33.0)",
    comments = "Source: city.proto")
public final class UberServiceGrpc {

  private UberServiceGrpc() {}

  public static final String SERVICE_NAME = "UberService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<generated.Ride,
      generated.Result> getPostRideMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostRide",
      requestType = generated.Ride.class,
      responseType = generated.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.Ride,
      generated.Result> getPostRideMethod() {
    io.grpc.MethodDescriptor<generated.Ride, generated.Result> getPostRideMethod;
    if ((getPostRideMethod = UberServiceGrpc.getPostRideMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getPostRideMethod = UberServiceGrpc.getPostRideMethod) == null) {
          UberServiceGrpc.getPostRideMethod = getPostRideMethod =
              io.grpc.MethodDescriptor.<generated.Ride, generated.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PostRide"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Ride.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Result.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("PostRide"))
              .build();
        }
      }
    }
    return getPostRideMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.CustomerRequest,
      generated.Result> getPostCustomerRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostCustomerRequest",
      requestType = generated.CustomerRequest.class,
      responseType = generated.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.CustomerRequest,
      generated.Result> getPostCustomerRequestMethod() {
    io.grpc.MethodDescriptor<generated.CustomerRequest, generated.Result> getPostCustomerRequestMethod;
    if ((getPostCustomerRequestMethod = UberServiceGrpc.getPostCustomerRequestMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getPostCustomerRequestMethod = UberServiceGrpc.getPostCustomerRequestMethod) == null) {
          UberServiceGrpc.getPostCustomerRequestMethod = getPostCustomerRequestMethod =
              io.grpc.MethodDescriptor.<generated.CustomerRequest, generated.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PostCustomerRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.CustomerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Result.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("PostCustomerRequest"))
              .build();
        }
      }
    }
    return getPostCustomerRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.CustomerRequest,
      generated.Result> getPostPathPlanningRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostPathPlanningRequest",
      requestType = generated.CustomerRequest.class,
      responseType = generated.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<generated.CustomerRequest,
      generated.Result> getPostPathPlanningRequestMethod() {
    io.grpc.MethodDescriptor<generated.CustomerRequest, generated.Result> getPostPathPlanningRequestMethod;
    if ((getPostPathPlanningRequestMethod = UberServiceGrpc.getPostPathPlanningRequestMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getPostPathPlanningRequestMethod = UberServiceGrpc.getPostPathPlanningRequestMethod) == null) {
          UberServiceGrpc.getPostPathPlanningRequestMethod = getPostPathPlanningRequestMethod =
              io.grpc.MethodDescriptor.<generated.CustomerRequest, generated.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PostPathPlanningRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.CustomerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Result.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("PostPathPlanningRequest"))
              .build();
        }
      }
    }
    return getPostPathPlanningRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.Rout,
      generated.Ride> getGetExistingRidesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetExistingRides",
      requestType = generated.Rout.class,
      responseType = generated.Ride.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<generated.Rout,
      generated.Ride> getGetExistingRidesMethod() {
    io.grpc.MethodDescriptor<generated.Rout, generated.Ride> getGetExistingRidesMethod;
    if ((getGetExistingRidesMethod = UberServiceGrpc.getGetExistingRidesMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getGetExistingRidesMethod = UberServiceGrpc.getGetExistingRidesMethod) == null) {
          UberServiceGrpc.getGetExistingRidesMethod = getGetExistingRidesMethod =
              io.grpc.MethodDescriptor.<generated.Rout, generated.Ride>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetExistingRides"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Rout.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Ride.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("GetExistingRides"))
              .build();
        }
      }
    }
    return getGetExistingRidesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.CustomerRequest,
      generated.Result> getUpdateRideMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateRide",
      requestType = generated.CustomerRequest.class,
      responseType = generated.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.CustomerRequest,
      generated.Result> getUpdateRideMethod() {
    io.grpc.MethodDescriptor<generated.CustomerRequest, generated.Result> getUpdateRideMethod;
    if ((getUpdateRideMethod = UberServiceGrpc.getUpdateRideMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getUpdateRideMethod = UberServiceGrpc.getUpdateRideMethod) == null) {
          UberServiceGrpc.getUpdateRideMethod = getUpdateRideMethod =
              io.grpc.MethodDescriptor.<generated.CustomerRequest, generated.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateRide"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.CustomerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Result.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("UpdateRide"))
              .build();
        }
      }
    }
    return getUpdateRideMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.Ride,
      generated.Result> getInsertRideToDbMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "InsertRideToDb",
      requestType = generated.Ride.class,
      responseType = generated.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.Ride,
      generated.Result> getInsertRideToDbMethod() {
    io.grpc.MethodDescriptor<generated.Ride, generated.Result> getInsertRideToDbMethod;
    if ((getInsertRideToDbMethod = UberServiceGrpc.getInsertRideToDbMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getInsertRideToDbMethod = UberServiceGrpc.getInsertRideToDbMethod) == null) {
          UberServiceGrpc.getInsertRideToDbMethod = getInsertRideToDbMethod =
              io.grpc.MethodDescriptor.<generated.Ride, generated.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "InsertRideToDb"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Ride.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Result.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("InsertRideToDb"))
              .build();
        }
      }
    }
    return getInsertRideToDbMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UberServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UberServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UberServiceStub>() {
        @java.lang.Override
        public UberServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UberServiceStub(channel, callOptions);
        }
      };
    return UberServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UberServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UberServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UberServiceBlockingStub>() {
        @java.lang.Override
        public UberServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UberServiceBlockingStub(channel, callOptions);
        }
      };
    return UberServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UberServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UberServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UberServiceFutureStub>() {
        @java.lang.Override
        public UberServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UberServiceFutureStub(channel, callOptions);
        }
      };
    return UberServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static abstract class UberServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * </pre>
     */
    public void postRide(generated.Ride request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getPostRideMethod(), responseObserver);
    }

    /**
     * <pre>
     * Accept a user's request to join a ride and check if there is a relevant ride.
     * </pre>
     */
    public void postCustomerRequest(generated.CustomerRequest request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getPostCustomerRequestMethod(), responseObserver);
    }

    /**
     * <pre>
     * Accept a user's request to join a ride and check if there is a relevant ride.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<generated.CustomerRequest> postPathPlanningRequest(
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      return asyncUnimplementedStreamingCall(getPostPathPlanningRequestMethod(), responseObserver);
    }

    /**
     * <pre>
     * Accept a list of cities and return all rides contains all these cities.
     * </pre>
     */
    public void getExistingRides(generated.Rout request,
        io.grpc.stub.StreamObserver<generated.Ride> responseObserver) {
      asyncUnimplementedUnaryCall(getGetExistingRidesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Save customer request to db
     * If found a relevant ride, update it's current vacancies
     * </pre>
     */
    public void updateRide(generated.CustomerRequest request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateRideMethod(), responseObserver);
    }

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * </pre>
     */
    public void insertRideToDb(generated.Ride request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getInsertRideToDbMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPostRideMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.Ride,
                generated.Result>(
                  this, METHODID_POST_RIDE)))
          .addMethod(
            getPostCustomerRequestMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.CustomerRequest,
                generated.Result>(
                  this, METHODID_POST_CUSTOMER_REQUEST)))
          .addMethod(
            getPostPathPlanningRequestMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                generated.CustomerRequest,
                generated.Result>(
                  this, METHODID_POST_PATH_PLANNING_REQUEST)))
          .addMethod(
            getGetExistingRidesMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                generated.Rout,
                generated.Ride>(
                  this, METHODID_GET_EXISTING_RIDES)))
          .addMethod(
            getUpdateRideMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.CustomerRequest,
                generated.Result>(
                  this, METHODID_UPDATE_RIDE)))
          .addMethod(
            getInsertRideToDbMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.Ride,
                generated.Result>(
                  this, METHODID_INSERT_RIDE_TO_DB)))
          .build();
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class UberServiceStub extends io.grpc.stub.AbstractAsyncStub<UberServiceStub> {
    private UberServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UberServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UberServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * </pre>
     */
    public void postRide(generated.Ride request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostRideMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Accept a user's request to join a ride and check if there is a relevant ride.
     * </pre>
     */
    public void postCustomerRequest(generated.CustomerRequest request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostCustomerRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Accept a user's request to join a ride and check if there is a relevant ride.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<generated.CustomerRequest> postPathPlanningRequest(
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getPostPathPlanningRequestMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Accept a list of cities and return all rides contains all these cities.
     * </pre>
     */
    public void getExistingRides(generated.Rout request,
        io.grpc.stub.StreamObserver<generated.Ride> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetExistingRidesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Save customer request to db
     * If found a relevant ride, update it's current vacancies
     * </pre>
     */
    public void updateRide(generated.CustomerRequest request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateRideMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * </pre>
     */
    public void insertRideToDb(generated.Ride request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getInsertRideToDbMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class UberServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<UberServiceBlockingStub> {
    private UberServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UberServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UberServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * </pre>
     */
    public generated.Result postRide(generated.Ride request) {
      return blockingUnaryCall(
          getChannel(), getPostRideMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Accept a user's request to join a ride and check if there is a relevant ride.
     * </pre>
     */
    public generated.Result postCustomerRequest(generated.CustomerRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostCustomerRequestMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Accept a list of cities and return all rides contains all these cities.
     * </pre>
     */
    public java.util.Iterator<generated.Ride> getExistingRides(
        generated.Rout request) {
      return blockingServerStreamingCall(
          getChannel(), getGetExistingRidesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Save customer request to db
     * If found a relevant ride, update it's current vacancies
     * </pre>
     */
    public generated.Result updateRide(generated.CustomerRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateRideMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * </pre>
     */
    public generated.Result insertRideToDb(generated.Ride request) {
      return blockingUnaryCall(
          getChannel(), getInsertRideToDbMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class UberServiceFutureStub extends io.grpc.stub.AbstractFutureStub<UberServiceFutureStub> {
    private UberServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UberServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UberServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Result> postRide(
        generated.Ride request) {
      return futureUnaryCall(
          getChannel().newCall(getPostRideMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Accept a user's request to join a ride and check if there is a relevant ride.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Result> postCustomerRequest(
        generated.CustomerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostCustomerRequestMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Save customer request to db
     * If found a relevant ride, update it's current vacancies
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Result> updateRide(
        generated.CustomerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateRideMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Result> insertRideToDb(
        generated.Ride request) {
      return futureUnaryCall(
          getChannel().newCall(getInsertRideToDbMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_POST_RIDE = 0;
  private static final int METHODID_POST_CUSTOMER_REQUEST = 1;
  private static final int METHODID_GET_EXISTING_RIDES = 2;
  private static final int METHODID_UPDATE_RIDE = 3;
  private static final int METHODID_INSERT_RIDE_TO_DB = 4;
  private static final int METHODID_POST_PATH_PLANNING_REQUEST = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UberServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UberServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_POST_RIDE:
          serviceImpl.postRide((generated.Ride) request,
              (io.grpc.stub.StreamObserver<generated.Result>) responseObserver);
          break;
        case METHODID_POST_CUSTOMER_REQUEST:
          serviceImpl.postCustomerRequest((generated.CustomerRequest) request,
              (io.grpc.stub.StreamObserver<generated.Result>) responseObserver);
          break;
        case METHODID_GET_EXISTING_RIDES:
          serviceImpl.getExistingRides((generated.Rout) request,
              (io.grpc.stub.StreamObserver<generated.Ride>) responseObserver);
          break;
        case METHODID_UPDATE_RIDE:
          serviceImpl.updateRide((generated.CustomerRequest) request,
              (io.grpc.stub.StreamObserver<generated.Result>) responseObserver);
          break;
        case METHODID_INSERT_RIDE_TO_DB:
          serviceImpl.insertRideToDb((generated.Ride) request,
              (io.grpc.stub.StreamObserver<generated.Result>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_POST_PATH_PLANNING_REQUEST:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.postPathPlanningRequest(
              (io.grpc.stub.StreamObserver<generated.Result>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class UberServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UberServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return generated.CityProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UberService");
    }
  }

  private static final class UberServiceFileDescriptorSupplier
      extends UberServiceBaseDescriptorSupplier {
    UberServiceFileDescriptorSupplier() {}
  }

  private static final class UberServiceMethodDescriptorSupplier
      extends UberServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UberServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UberServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UberServiceFileDescriptorSupplier())
              .addMethod(getPostRideMethod())
              .addMethod(getPostCustomerRequestMethod())
              .addMethod(getPostPathPlanningRequestMethod())
              .addMethod(getGetExistingRidesMethod())
              .addMethod(getUpdateRideMethod())
              .addMethod(getInsertRideToDbMethod())
              .build();
        }
      }
    }
    return result;
  }
}
