package gauge.messages;

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
 * Reporter services is meant for documentation plugins
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.13.1)",
    comments = "Source: services.proto")
public final class DocumenterGrpc {

  private DocumenterGrpc() {}

  public static final String SERVICE_NAME = "gauge.messages.Documenter";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.SpecDetails,
      gauge.messages.Messages.Empty> getGenerateDocsMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SpecDetails,
      gauge.messages.Messages.Empty> getGenerateDocsMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SpecDetails, gauge.messages.Messages.Empty> getGenerateDocsMethod;
    if ((getGenerateDocsMethod = DocumenterGrpc.getGenerateDocsMethod) == null) {
      synchronized (DocumenterGrpc.class) {
        if ((getGenerateDocsMethod = DocumenterGrpc.getGenerateDocsMethod) == null) {
          DocumenterGrpc.getGenerateDocsMethod = getGenerateDocsMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SpecDetails, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Documenter", "GenerateDocs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.SpecDetails.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new DocumenterMethodDescriptorSupplier("GenerateDocs"))
                  .build();
          }
        }
     }
     return getGenerateDocsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest,
      gauge.messages.Messages.Empty> getKillMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest,
      gauge.messages.Messages.Empty> getKillMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest, gauge.messages.Messages.Empty> getKillMethod;
    if ((getKillMethod = DocumenterGrpc.getKillMethod) == null) {
      synchronized (DocumenterGrpc.class) {
        if ((getKillMethod = DocumenterGrpc.getKillMethod) == null) {
          DocumenterGrpc.getKillMethod = getKillMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.KillProcessRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Documenter", "Kill"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.KillProcessRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new DocumenterMethodDescriptorSupplier("Kill"))
                  .build();
          }
        }
     }
     return getKillMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DocumenterStub newStub(io.grpc.Channel channel) {
    return new DocumenterStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DocumenterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DocumenterBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DocumenterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DocumenterFutureStub(channel);
  }

  /**
   * <pre>
   * Reporter services is meant for documentation plugins
   * </pre>
   */
  public static abstract class DocumenterImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * GenerateDocs is a RPC tell plugin to generate docs from the spec details.
     * Accepts a SpecDetails message and returns a Empty message.
     * </pre>
     */
    public void generateDocs(gauge.messages.Messages.SpecDetails request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getGenerateDocsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Kill is a RPC tell plugin to stop grpc server and kill the plugin process.
     * Accepts a KillProcessRequest message and returns a Empty message.
     * </pre>
     */
    public void kill(gauge.messages.Messages.KillProcessRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getKillMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGenerateDocsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.SpecDetails,
                gauge.messages.Messages.Empty>(
                  this, METHODID_GENERATE_DOCS)))
          .addMethod(
            getKillMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.KillProcessRequest,
                gauge.messages.Messages.Empty>(
                  this, METHODID_KILL)))
          .build();
    }
  }

  /**
   * <pre>
   * Reporter services is meant for documentation plugins
   * </pre>
   */
  public static final class DocumenterStub extends io.grpc.stub.AbstractStub<DocumenterStub> {
    private DocumenterStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DocumenterStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DocumenterStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DocumenterStub(channel, callOptions);
    }

    /**
     * <pre>
     * GenerateDocs is a RPC tell plugin to generate docs from the spec details.
     * Accepts a SpecDetails message and returns a Empty message.
     * </pre>
     */
    public void generateDocs(gauge.messages.Messages.SpecDetails request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGenerateDocsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Kill is a RPC tell plugin to stop grpc server and kill the plugin process.
     * Accepts a KillProcessRequest message and returns a Empty message.
     * </pre>
     */
    public void kill(gauge.messages.Messages.KillProcessRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getKillMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Reporter services is meant for documentation plugins
   * </pre>
   */
  public static final class DocumenterBlockingStub extends io.grpc.stub.AbstractStub<DocumenterBlockingStub> {
    private DocumenterBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DocumenterBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DocumenterBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DocumenterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * GenerateDocs is a RPC tell plugin to generate docs from the spec details.
     * Accepts a SpecDetails message and returns a Empty message.
     * </pre>
     */
    public gauge.messages.Messages.Empty generateDocs(gauge.messages.Messages.SpecDetails request) {
      return blockingUnaryCall(
          getChannel(), getGenerateDocsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Kill is a RPC tell plugin to stop grpc server and kill the plugin process.
     * Accepts a KillProcessRequest message and returns a Empty message.
     * </pre>
     */
    public gauge.messages.Messages.Empty kill(gauge.messages.Messages.KillProcessRequest request) {
      return blockingUnaryCall(
          getChannel(), getKillMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Reporter services is meant for documentation plugins
   * </pre>
   */
  public static final class DocumenterFutureStub extends io.grpc.stub.AbstractStub<DocumenterFutureStub> {
    private DocumenterFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DocumenterFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DocumenterFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DocumenterFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * GenerateDocs is a RPC tell plugin to generate docs from the spec details.
     * Accepts a SpecDetails message and returns a Empty message.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> generateDocs(
        gauge.messages.Messages.SpecDetails request) {
      return futureUnaryCall(
          getChannel().newCall(getGenerateDocsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Kill is a RPC tell plugin to stop grpc server and kill the plugin process.
     * Accepts a KillProcessRequest message and returns a Empty message.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> kill(
        gauge.messages.Messages.KillProcessRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getKillMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GENERATE_DOCS = 0;
  private static final int METHODID_KILL = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DocumenterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DocumenterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GENERATE_DOCS:
          serviceImpl.generateDocs((gauge.messages.Messages.SpecDetails) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
          break;
        case METHODID_KILL:
          serviceImpl.kill((gauge.messages.Messages.KillProcessRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
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

  private static abstract class DocumenterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DocumenterBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gauge.messages.Services.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Documenter");
    }
  }

  private static final class DocumenterFileDescriptorSupplier
      extends DocumenterBaseDescriptorSupplier {
    DocumenterFileDescriptorSupplier() {}
  }

  private static final class DocumenterMethodDescriptorSupplier
      extends DocumenterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DocumenterMethodDescriptorSupplier(String methodName) {
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
      synchronized (DocumenterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DocumenterFileDescriptorSupplier())
              .addMethod(getGenerateDocsMethod())
              .addMethod(getKillMethod())
              .build();
        }
      }
    }
    return result;
  }
}
