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
    comments = "Source: scheme.proto")
public final class UberGrpc {

  private UberGrpc() {}

  public static final String SERVICE_NAME = "uber.Uber";

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
    if ((getPostRideMethod = UberGrpc.getPostRideMethod) == null) {
      synchronized (UberGrpc.class) {
        if ((getPostRideMethod = UberGrpc.getPostRideMethod) == null) {
          UberGrpc.getPostRideMethod = getPostRideMethod =
              io.grpc.MethodDescriptor.<generated.Ride, generated.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PostRide"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Ride.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  generated.Result.getDefaultInstance()))
              .setSchemaDescriptor(new UberMethodDescriptorSupplier("PostRide"))
              .build();
        }
      }
    }
    return getPostRideMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UberStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UberStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UberStub>() {
        @java.lang.Override
        public UberStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UberStub(channel, callOptions);
        }
      };
    return UberStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UberBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UberBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UberBlockingStub>() {
        @java.lang.Override
        public UberBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UberBlockingStub(channel, callOptions);
        }
      };
    return UberBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UberFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UberFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UberFutureStub>() {
        @java.lang.Override
        public UberFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UberFutureStub(channel, callOptions);
        }
      };
    return UberFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static abstract class UberImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * lb-&gt;cityS
     * </pre>
     */
    public void postRide(generated.Ride request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getPostRideMethod(), responseObserver);
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
          .build();
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class UberStub extends io.grpc.stub.AbstractAsyncStub<UberStub> {
    private UberStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UberStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UberStub(channel, callOptions);
    }

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * lb-&gt;cityS
     * </pre>
     */
    public void postRide(generated.Ride request,
        io.grpc.stub.StreamObserver<generated.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostRideMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class UberBlockingStub extends io.grpc.stub.AbstractBlockingStub<UberBlockingStub> {
    private UberBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UberBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UberBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * lb-&gt;cityS
     * </pre>
     */
    public generated.Result postRide(generated.Ride request) {
      return blockingUnaryCall(
          getChannel(), getPostRideMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class UberFutureStub extends io.grpc.stub.AbstractFutureStub<UberFutureStub> {
    private UberFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UberFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UberFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Accept a user's ride and save it in the DB.
     * lb-&gt;cityS
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<generated.Result> postRide(
        generated.Ride request) {
      return futureUnaryCall(
          getChannel().newCall(getPostRideMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_POST_RIDE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UberImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UberImplBase serviceImpl, int methodId) {
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

  private static abstract class UberBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UberBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return generated.UberProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Uber");
    }
  }

  private static final class UberFileDescriptorSupplier
      extends UberBaseDescriptorSupplier {
    UberFileDescriptorSupplier() {}
  }

  private static final class UberMethodDescriptorSupplier
      extends UberBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UberMethodDescriptorSupplier(String methodName) {
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
      synchronized (UberGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UberFileDescriptorSupplier())
              .addMethod(getPostRideMethod())
              .build();
        }
      }
    }
    return result;
  }
}
