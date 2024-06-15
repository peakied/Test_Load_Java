package protos;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: lookup.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LookupServiceGrpc {

  private LookupServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "protos.LookupService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<protos.Lookup.LookupReq,
      protos.Lookup.LookupRes> getLookupMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Lookup",
      requestType = protos.Lookup.LookupReq.class,
      responseType = protos.Lookup.LookupRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<protos.Lookup.LookupReq,
      protos.Lookup.LookupRes> getLookupMethod() {
    io.grpc.MethodDescriptor<protos.Lookup.LookupReq, protos.Lookup.LookupRes> getLookupMethod;
    if ((getLookupMethod = LookupServiceGrpc.getLookupMethod) == null) {
      synchronized (LookupServiceGrpc.class) {
        if ((getLookupMethod = LookupServiceGrpc.getLookupMethod) == null) {
          LookupServiceGrpc.getLookupMethod = getLookupMethod =
              io.grpc.MethodDescriptor.<protos.Lookup.LookupReq, protos.Lookup.LookupRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Lookup"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protos.Lookup.LookupReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  protos.Lookup.LookupRes.getDefaultInstance()))
              .setSchemaDescriptor(new LookupServiceMethodDescriptorSupplier("Lookup"))
              .build();
        }
      }
    }
    return getLookupMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LookupServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LookupServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LookupServiceStub>() {
        @java.lang.Override
        public LookupServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LookupServiceStub(channel, callOptions);
        }
      };
    return LookupServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LookupServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LookupServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LookupServiceBlockingStub>() {
        @java.lang.Override
        public LookupServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LookupServiceBlockingStub(channel, callOptions);
        }
      };
    return LookupServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LookupServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LookupServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LookupServiceFutureStub>() {
        @java.lang.Override
        public LookupServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LookupServiceFutureStub(channel, callOptions);
        }
      };
    return LookupServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void lookup(protos.Lookup.LookupReq request,
        io.grpc.stub.StreamObserver<protos.Lookup.LookupRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLookupMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service LookupService.
   */
  public static abstract class LookupServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return LookupServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service LookupService.
   */
  public static final class LookupServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LookupServiceStub> {
    private LookupServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LookupServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LookupServiceStub(channel, callOptions);
    }

    /**
     */
    public void lookup(protos.Lookup.LookupReq request,
        io.grpc.stub.StreamObserver<protos.Lookup.LookupRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLookupMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service LookupService.
   */
  public static final class LookupServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LookupServiceBlockingStub> {
    private LookupServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LookupServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LookupServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public protos.Lookup.LookupRes lookup(protos.Lookup.LookupReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLookupMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service LookupService.
   */
  public static final class LookupServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LookupServiceFutureStub> {
    private LookupServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LookupServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LookupServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<protos.Lookup.LookupRes> lookup(
        protos.Lookup.LookupReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLookupMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOOKUP = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOOKUP:
          serviceImpl.lookup((protos.Lookup.LookupReq) request,
              (io.grpc.stub.StreamObserver<protos.Lookup.LookupRes>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getLookupMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              protos.Lookup.LookupReq,
              protos.Lookup.LookupRes>(
                service, METHODID_LOOKUP)))
        .build();
  }

  private static abstract class LookupServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LookupServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return protos.Lookup.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LookupService");
    }
  }

  private static final class LookupServiceFileDescriptorSupplier
      extends LookupServiceBaseDescriptorSupplier {
    LookupServiceFileDescriptorSupplier() {}
  }

  private static final class LookupServiceMethodDescriptorSupplier
      extends LookupServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    LookupServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (LookupServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LookupServiceFileDescriptorSupplier())
              .addMethod(getLookupMethod())
              .build();
        }
      }
    }
    return result;
  }
}
