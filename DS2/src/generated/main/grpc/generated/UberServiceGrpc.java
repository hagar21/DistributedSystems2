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
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.33.0)",
    comments = "Source: scheme.proto")
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
      generated.Ride> getPostPathPlanningRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostPathPlanningRequest",
      requestType = generated.CustomerRequest.class,
      responseType = generated.Ride.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<generated.CustomerRequest,
      generated.Ride> getPostPathPlanningRequestMethod() {
    io.grpc.MethodDescriptor<generated.CustomerRequest, generated.Ride> getPostPathPlanningRequestMethod;
    if ((getPostPathPlanningRequestMethod = UberServiceGrpc.getPostPathPlanningRequestMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getPostPathPlanningRequestMethod = UberServiceGrpc.getPostPathPlanningRequestMethod) == null) {
          UberServiceGrpc.getPostPathPlanningRequestMethod = getPostPathPlanningRequestMethod =
              io.grpc.MethodDescriptor.<generated.CustomerRequest, generated.Ride>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PostPathPlanningRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.CustomerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Ride.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("PostPathPlanningRequest"))
              .build();
        }
      }
    }
    return getPostPathPlanningRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.Rout,
      generated.Ride> getReserveRideMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReserveRide",
      requestType = generated.Rout.class,
      responseType = generated.Ride.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.Rout,
      generated.Ride> getReserveRideMethod() {
    io.grpc.MethodDescriptor<generated.Rout, generated.Ride> getReserveRideMethod;
    if ((getReserveRideMethod = UberServiceGrpc.getReserveRideMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getReserveRideMethod = UberServiceGrpc.getReserveRideMethod) == null) {
          UberServiceGrpc.getReserveRideMethod = getReserveRideMethod =
              io.grpc.MethodDescriptor.<generated.Rout, generated.Ride>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReserveRide"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Rout.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Ride.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("ReserveRide"))
              .build();
        }
      }
    }
    return getReserveRideMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.Ride,
      generated.Result> getRevertCommitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RevertCommit",
      requestType = generated.Ride.class,
      responseType = generated.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.Ride,
      generated.Result> getRevertCommitMethod() {
    io.grpc.MethodDescriptor<generated.Ride, generated.Result> getRevertCommitMethod;
    if ((getRevertCommitMethod = UberServiceGrpc.getRevertCommitMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getRevertCommitMethod = UberServiceGrpc.getRevertCommitMethod) == null) {
          UberServiceGrpc.getRevertCommitMethod = getRevertCommitMethod =
              io.grpc.MethodDescriptor.<generated.Ride, generated.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RevertCommit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Ride.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Result.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("RevertCommit"))
              .build();
        }
      }
    }
    return getRevertCommitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.google.protobuf.Empty> getSnapshotMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Snapshot",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.google.protobuf.Empty> getSnapshotMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.google.protobuf.Empty> getSnapshotMethod;
    if ((getSnapshotMethod = UberServiceGrpc.getSnapshotMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getSnapshotMethod = UberServiceGrpc.getSnapshotMethod) == null) {
          UberServiceGrpc.getSnapshotMethod = getSnapshotMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Snapshot"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("Snapshot"))
              .build();
        }
      }
    }
    return getSnapshotMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.CityRequest,
      generated.Ride> getCityRequestRideMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CityRequestRide",
      requestType = generated.CityRequest.class,
      responseType = generated.Ride.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.CityRequest,
      generated.Ride> getCityRequestRideMethod() {
    io.grpc.MethodDescriptor<generated.CityRequest, generated.Ride> getCityRequestRideMethod;
    if ((getCityRequestRideMethod = UberServiceGrpc.getCityRequestRideMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getCityRequestRideMethod = UberServiceGrpc.getCityRequestRideMethod) == null) {
          UberServiceGrpc.getCityRequestRideMethod = getCityRequestRideMethod =
              io.grpc.MethodDescriptor.<generated.CityRequest, generated.Ride>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CityRequestRide"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.CityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Ride.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("CityRequestRide"))
              .build();
        }
      }
    }
    return getCityRequestRideMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.CityRevertRequest,
      generated.Result> getCityRevertRequestRideMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CityRevertRequestRide",
      requestType = generated.CityRevertRequest.class,
      responseType = generated.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.CityRevertRequest,
      generated.Result> getCityRevertRequestRideMethod() {
    io.grpc.MethodDescriptor<generated.CityRevertRequest, generated.Result> getCityRevertRequestRideMethod;
    if ((getCityRevertRequestRideMethod = UberServiceGrpc.getCityRevertRequestRideMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getCityRevertRequestRideMethod = UberServiceGrpc.getCityRevertRequestRideMethod) == null) {
          UberServiceGrpc.getCityRevertRequestRideMethod = getCityRevertRequestRideMethod =
              io.grpc.MethodDescriptor.<generated.CityRevertRequest, generated.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CityRevertRequestRide"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.CityRevertRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Result.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("CityRevertRequestRide"))
              .build();
        }
      }
    }
    return getCityRevertRequestRideMethod;
  }

  private static volatile io.grpc.MethodDescriptor<generated.CustomerRequest,
      generated.Result> getDeleteCustomerRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteCustomerRequest",
      requestType = generated.CustomerRequest.class,
      responseType = generated.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<generated.CustomerRequest,
      generated.Result> getDeleteCustomerRequestMethod() {
    io.grpc.MethodDescriptor<generated.CustomerRequest, generated.Result> getDeleteCustomerRequestMethod;
    if ((getDeleteCustomerRequestMethod = UberServiceGrpc.getDeleteCustomerRequestMethod) == null) {
      synchronized (UberServiceGrpc.class) {
        if ((getDeleteCustomerRequestMethod = UberServiceGrpc.getDeleteCustomerRequestMethod) == null) {
          UberServiceGrpc.getDeleteCustomerRequestMethod = getDeleteCustomerRequestMethod =
              io.grpc.MethodDescriptor.<generated.CustomerRequest, generated.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteCustomerRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.CustomerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Result.getDefaultInstance()))
              .setSchemaDescriptor(new UberServiceMethodDescriptorSupplier("DeleteCustomerRequest"))
              .build();
        }
      }
    }
    return getDeleteCustomerRequestMethod;
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
     * Accept a handled customer request and save it in the DB.
     * </pre>
     */
    public void postCustomerRequest(generated.CustomerRequest request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getPostCustomerRequestMethod(), responseObserver);
    }

    /**
     * <pre>
     * Accept a user's request to join a ride and check if there are relevant rides.
     * </pre>
     */
    public void postPathPlanningRequest(generated.CustomerRequest request,
        io.grpc.stub.StreamObserver<generated.Ride> responseObserver) {
      asyncUnimplementedUnaryCall(getPostPathPlanningRequestMethod(), responseObserver);
    }

    /**
     * <pre>
     * Accept a user's request from other city
     * return a compatible ride and update it in local db if found, otherwise noRide.
     * </pre>
     */
    public void reserveRide(generated.Rout request,
        io.grpc.stub.StreamObserver<generated.Ride> responseObserver) {
      asyncUnimplementedUnaryCall(getReserveRideMethod(), responseObserver);
    }

    /**
     * <pre>
     * if part of the path couldn't be satisfied revert intermediate commits
     * </pre>
     */
    public void revertCommit(generated.Ride request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getRevertCommitMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns all Rides in the database
     * </pre>
     */
    public void snapshot(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getSnapshotMethod(), responseObserver);
    }

    /**
     * <pre>
     * City request Ride from different city
     * </pre>
     */
    public void cityRequestRide(generated.CityRequest request,
        io.grpc.stub.StreamObserver<generated.Ride> responseObserver) {
      asyncUnimplementedUnaryCall(getCityRequestRideMethod(), responseObserver);
    }

    /**
     * <pre>
     * City revert request Ride from different city
     * </pre>
     */
    public void cityRevertRequestRide(generated.CityRevertRequest request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getCityRevertRequestRideMethod(), responseObserver);
    }

    /**
     */
    public void deleteCustomerRequest(generated.CustomerRequest request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteCustomerRequestMethod(), responseObserver);
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
            asyncServerStreamingCall(
              new MethodHandlers<
                generated.CustomerRequest,
                generated.Ride>(
                  this, METHODID_POST_PATH_PLANNING_REQUEST)))
          .addMethod(
            getReserveRideMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.Rout,
                generated.Ride>(
                  this, METHODID_RESERVE_RIDE)))
          .addMethod(
            getRevertCommitMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.Ride,
                generated.Result>(
                  this, METHODID_REVERT_COMMIT)))
          .addMethod(
            getSnapshotMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.google.protobuf.Empty>(
                  this, METHODID_SNAPSHOT)))
          .addMethod(
            getCityRequestRideMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.CityRequest,
                generated.Ride>(
                  this, METHODID_CITY_REQUEST_RIDE)))
          .addMethod(
            getCityRevertRequestRideMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.CityRevertRequest,
                generated.Result>(
                  this, METHODID_CITY_REVERT_REQUEST_RIDE)))
          .addMethod(
            getDeleteCustomerRequestMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                generated.CustomerRequest,
                generated.Result>(
                  this, METHODID_DELETE_CUSTOMER_REQUEST)))
          .build();
    }
  }

  /**
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
     * Accept a handled customer request and save it in the DB.
     * </pre>
     */
    public void postCustomerRequest(generated.CustomerRequest request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostCustomerRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Accept a user's request to join a ride and check if there are relevant rides.
     * </pre>
     */
    public void postPathPlanningRequest(generated.CustomerRequest request,
        io.grpc.stub.StreamObserver<generated.Ride> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getPostPathPlanningRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Accept a user's request from other city
     * return a compatible ride and update it in local db if found, otherwise noRide.
     * </pre>
     */
    public void reserveRide(generated.Rout request,
        io.grpc.stub.StreamObserver<generated.Ride> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReserveRideMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * if part of the path couldn't be satisfied revert intermediate commits
     * </pre>
     */
    public void revertCommit(generated.Ride request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRevertCommitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns all Rides in the database
     * </pre>
     */
    public void snapshot(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSnapshotMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * City request Ride from different city
     * </pre>
     */
    public void cityRequestRide(generated.CityRequest request,
        io.grpc.stub.StreamObserver<generated.Ride> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCityRequestRideMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * City revert request Ride from different city
     * </pre>
     */
    public void cityRevertRequestRide(generated.CityRevertRequest request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCityRevertRequestRideMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteCustomerRequest(generated.CustomerRequest request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteCustomerRequestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
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
     * Accept a handled customer request and save it in the DB.
     * </pre>
     */
    public generated.Result postCustomerRequest(generated.CustomerRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostCustomerRequestMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Accept a user's request to join a ride and check if there are relevant rides.
     * </pre>
     */
    public java.util.Iterator<generated.Ride> postPathPlanningRequest(
        generated.CustomerRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getPostPathPlanningRequestMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Accept a user's request from other city
     * return a compatible ride and update it in local db if found, otherwise noRide.
     * </pre>
     */
    public generated.Ride reserveRide(generated.Rout request) {
      return blockingUnaryCall(
          getChannel(), getReserveRideMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * if part of the path couldn't be satisfied revert intermediate commits
     * </pre>
     */
    public generated.Result revertCommit(generated.Ride request) {
      return blockingUnaryCall(
          getChannel(), getRevertCommitMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns all Rides in the database
     * </pre>
     */
    public com.google.protobuf.Empty snapshot(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getSnapshotMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * City request Ride from different city
     * </pre>
     */
    public generated.Ride cityRequestRide(generated.CityRequest request) {
      return blockingUnaryCall(
          getChannel(), getCityRequestRideMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * City revert request Ride from different city
     * </pre>
     */
    public generated.Result cityRevertRequestRide(generated.CityRevertRequest request) {
      return blockingUnaryCall(
          getChannel(), getCityRevertRequestRideMethod(), getCallOptions(), request);
    }

    /**
     */
    public generated.Result deleteCustomerRequest(generated.CustomerRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteCustomerRequestMethod(), getCallOptions(), request);
    }
  }

  /**
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
     * Accept a handled customer request and save it in the DB.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Result> postCustomerRequest(
        generated.CustomerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostCustomerRequestMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Accept a user's request from other city
     * return a compatible ride and update it in local db if found, otherwise noRide.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Ride> reserveRide(
        generated.Rout request) {
      return futureUnaryCall(
          getChannel().newCall(getReserveRideMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * if part of the path couldn't be satisfied revert intermediate commits
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Result> revertCommit(
        generated.Ride request) {
      return futureUnaryCall(
          getChannel().newCall(getRevertCommitMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns all Rides in the database
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> snapshot(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getSnapshotMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * City request Ride from different city
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Ride> cityRequestRide(
        generated.CityRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCityRequestRideMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * City revert request Ride from different city
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Result> cityRevertRequestRide(
        generated.CityRevertRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCityRevertRequestRideMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Result> deleteCustomerRequest(
        generated.CustomerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteCustomerRequestMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_POST_RIDE = 0;
  private static final int METHODID_POST_CUSTOMER_REQUEST = 1;
  private static final int METHODID_POST_PATH_PLANNING_REQUEST = 2;
  private static final int METHODID_RESERVE_RIDE = 3;
  private static final int METHODID_REVERT_COMMIT = 4;
  private static final int METHODID_SNAPSHOT = 5;
  private static final int METHODID_CITY_REQUEST_RIDE = 6;
  private static final int METHODID_CITY_REVERT_REQUEST_RIDE = 7;
  private static final int METHODID_DELETE_CUSTOMER_REQUEST = 8;

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
        case METHODID_POST_PATH_PLANNING_REQUEST:
          serviceImpl.postPathPlanningRequest((generated.CustomerRequest) request,
              (io.grpc.stub.StreamObserver<generated.Ride>) responseObserver);
          break;
        case METHODID_RESERVE_RIDE:
          serviceImpl.reserveRide((generated.Rout) request,
              (io.grpc.stub.StreamObserver<generated.Ride>) responseObserver);
          break;
        case METHODID_REVERT_COMMIT:
          serviceImpl.revertCommit((generated.Ride) request,
              (io.grpc.stub.StreamObserver<generated.Result>) responseObserver);
          break;
        case METHODID_SNAPSHOT:
          serviceImpl.snapshot((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_CITY_REQUEST_RIDE:
          serviceImpl.cityRequestRide((generated.CityRequest) request,
              (io.grpc.stub.StreamObserver<generated.Ride>) responseObserver);
          break;
        case METHODID_CITY_REVERT_REQUEST_RIDE:
          serviceImpl.cityRevertRequestRide((generated.CityRevertRequest) request,
              (io.grpc.stub.StreamObserver<generated.Result>) responseObserver);
          break;
        case METHODID_DELETE_CUSTOMER_REQUEST:
          serviceImpl.deleteCustomerRequest((generated.CustomerRequest) request,
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
              .addMethod(getReserveRideMethod())
              .addMethod(getRevertCommitMethod())
              .addMethod(getSnapshotMethod())
              .addMethod(getCityRequestRideMethod())
              .addMethod(getCityRevertRequestRideMethod())
              .addMethod(getDeleteCustomerRequestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
