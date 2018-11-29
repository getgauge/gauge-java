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
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.13.1)",
    comments = "Source: lsp.proto")
public final class lspServiceGrpc {

  private lspServiceGrpc() {}

  public static final String SERVICE_NAME = "gauge.messages.lspService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepNamesRequest,
      gauge.messages.Messages.StepNamesResponse> getGetStepNamesMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepNamesRequest,
      gauge.messages.Messages.StepNamesResponse> getGetStepNamesMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepNamesRequest, gauge.messages.Messages.StepNamesResponse> getGetStepNamesMethod;
    if ((getGetStepNamesMethod = lspServiceGrpc.getGetStepNamesMethod) == null) {
      synchronized (lspServiceGrpc.class) {
        if ((getGetStepNamesMethod = lspServiceGrpc.getGetStepNamesMethod) == null) {
          lspServiceGrpc.getGetStepNamesMethod = getGetStepNamesMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepNamesRequest, gauge.messages.Messages.StepNamesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.lspService", "GetStepNames"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepNamesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepNamesResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new lspServiceMethodDescriptorSupplier("GetStepNames"))
                  .build();
          }
        }
     }
     return getGetStepNamesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.CacheFileRequest,
      gauge.messages.Lsp.Empty> getCacheFileMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.CacheFileRequest,
      gauge.messages.Lsp.Empty> getCacheFileMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.CacheFileRequest, gauge.messages.Lsp.Empty> getCacheFileMethod;
    if ((getCacheFileMethod = lspServiceGrpc.getCacheFileMethod) == null) {
      synchronized (lspServiceGrpc.class) {
        if ((getCacheFileMethod = lspServiceGrpc.getCacheFileMethod) == null) {
          lspServiceGrpc.getCacheFileMethod = getCacheFileMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.CacheFileRequest, gauge.messages.Lsp.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.lspService", "CacheFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.CacheFileRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Lsp.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new lspServiceMethodDescriptorSupplier("CacheFile"))
                  .build();
          }
        }
     }
     return getCacheFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepPositionsRequest,
      gauge.messages.Messages.StepPositionsResponse> getGetStepPositionsMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepPositionsRequest,
      gauge.messages.Messages.StepPositionsResponse> getGetStepPositionsMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepPositionsRequest, gauge.messages.Messages.StepPositionsResponse> getGetStepPositionsMethod;
    if ((getGetStepPositionsMethod = lspServiceGrpc.getGetStepPositionsMethod) == null) {
      synchronized (lspServiceGrpc.class) {
        if ((getGetStepPositionsMethod = lspServiceGrpc.getGetStepPositionsMethod) == null) {
          lspServiceGrpc.getGetStepPositionsMethod = getGetStepPositionsMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepPositionsRequest, gauge.messages.Messages.StepPositionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.lspService", "GetStepPositions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepPositionsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepPositionsResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new lspServiceMethodDescriptorSupplier("GetStepPositions"))
                  .build();
          }
        }
     }
     return getGetStepPositionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Lsp.Empty,
      gauge.messages.Messages.ImplementationFileListResponse> getGetImplementationFilesMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Lsp.Empty,
      gauge.messages.Messages.ImplementationFileListResponse> getGetImplementationFilesMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Lsp.Empty, gauge.messages.Messages.ImplementationFileListResponse> getGetImplementationFilesMethod;
    if ((getGetImplementationFilesMethod = lspServiceGrpc.getGetImplementationFilesMethod) == null) {
      synchronized (lspServiceGrpc.class) {
        if ((getGetImplementationFilesMethod = lspServiceGrpc.getGetImplementationFilesMethod) == null) {
          lspServiceGrpc.getGetImplementationFilesMethod = getGetImplementationFilesMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Lsp.Empty, gauge.messages.Messages.ImplementationFileListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.lspService", "GetImplementationFiles"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Lsp.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ImplementationFileListResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new lspServiceMethodDescriptorSupplier("GetImplementationFiles"))
                  .build();
          }
        }
     }
     return getGetImplementationFilesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StubImplementationCodeRequest,
      gauge.messages.Messages.FileDiff> getImplementStubMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StubImplementationCodeRequest,
      gauge.messages.Messages.FileDiff> getImplementStubMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StubImplementationCodeRequest, gauge.messages.Messages.FileDiff> getImplementStubMethod;
    if ((getImplementStubMethod = lspServiceGrpc.getImplementStubMethod) == null) {
      synchronized (lspServiceGrpc.class) {
        if ((getImplementStubMethod = lspServiceGrpc.getImplementStubMethod) == null) {
          lspServiceGrpc.getImplementStubMethod = getImplementStubMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StubImplementationCodeRequest, gauge.messages.Messages.FileDiff>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.lspService", "ImplementStub"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StubImplementationCodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.FileDiff.getDefaultInstance()))
                  .setSchemaDescriptor(new lspServiceMethodDescriptorSupplier("ImplementStub"))
                  .build();
          }
        }
     }
     return getImplementStubMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepValidateRequest,
      gauge.messages.Messages.StepValidateResponse> getValidateStepMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepValidateRequest,
      gauge.messages.Messages.StepValidateResponse> getValidateStepMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepValidateRequest, gauge.messages.Messages.StepValidateResponse> getValidateStepMethod;
    if ((getValidateStepMethod = lspServiceGrpc.getValidateStepMethod) == null) {
      synchronized (lspServiceGrpc.class) {
        if ((getValidateStepMethod = lspServiceGrpc.getValidateStepMethod) == null) {
          lspServiceGrpc.getValidateStepMethod = getValidateStepMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepValidateRequest, gauge.messages.Messages.StepValidateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.lspService", "ValidateStep"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepValidateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepValidateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new lspServiceMethodDescriptorSupplier("ValidateStep"))
                  .build();
          }
        }
     }
     return getValidateStepMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.RefactorRequest,
      gauge.messages.Messages.RefactorResponse> getRefactorMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.RefactorRequest,
      gauge.messages.Messages.RefactorResponse> getRefactorMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.RefactorRequest, gauge.messages.Messages.RefactorResponse> getRefactorMethod;
    if ((getRefactorMethod = lspServiceGrpc.getRefactorMethod) == null) {
      synchronized (lspServiceGrpc.class) {
        if ((getRefactorMethod = lspServiceGrpc.getRefactorMethod) == null) {
          lspServiceGrpc.getRefactorMethod = getRefactorMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.RefactorRequest, gauge.messages.Messages.RefactorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.lspService", "Refactor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.RefactorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.RefactorResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new lspServiceMethodDescriptorSupplier("Refactor"))
                  .build();
          }
        }
     }
     return getRefactorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepNameRequest,
      gauge.messages.Messages.StepNameResponse> getGetStepNameMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepNameRequest,
      gauge.messages.Messages.StepNameResponse> getGetStepNameMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepNameRequest, gauge.messages.Messages.StepNameResponse> getGetStepNameMethod;
    if ((getGetStepNameMethod = lspServiceGrpc.getGetStepNameMethod) == null) {
      synchronized (lspServiceGrpc.class) {
        if ((getGetStepNameMethod = lspServiceGrpc.getGetStepNameMethod) == null) {
          lspServiceGrpc.getGetStepNameMethod = getGetStepNameMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepNameRequest, gauge.messages.Messages.StepNameResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.lspService", "GetStepName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepNameRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepNameResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new lspServiceMethodDescriptorSupplier("GetStepName"))
                  .build();
          }
        }
     }
     return getGetStepNameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Lsp.Empty,
      gauge.messages.Messages.ImplementationFileGlobPatternResponse> getGetGlobPatternsMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Lsp.Empty,
      gauge.messages.Messages.ImplementationFileGlobPatternResponse> getGetGlobPatternsMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Lsp.Empty, gauge.messages.Messages.ImplementationFileGlobPatternResponse> getGetGlobPatternsMethod;
    if ((getGetGlobPatternsMethod = lspServiceGrpc.getGetGlobPatternsMethod) == null) {
      synchronized (lspServiceGrpc.class) {
        if ((getGetGlobPatternsMethod = lspServiceGrpc.getGetGlobPatternsMethod) == null) {
          lspServiceGrpc.getGetGlobPatternsMethod = getGetGlobPatternsMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Lsp.Empty, gauge.messages.Messages.ImplementationFileGlobPatternResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.lspService", "GetGlobPatterns"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Lsp.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ImplementationFileGlobPatternResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new lspServiceMethodDescriptorSupplier("GetGlobPatterns"))
                  .build();
          }
        }
     }
     return getGetGlobPatternsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest,
      gauge.messages.Lsp.Empty> getKillProcessMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest,
      gauge.messages.Lsp.Empty> getKillProcessMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest, gauge.messages.Lsp.Empty> getKillProcessMethod;
    if ((getKillProcessMethod = lspServiceGrpc.getKillProcessMethod) == null) {
      synchronized (lspServiceGrpc.class) {
        if ((getKillProcessMethod = lspServiceGrpc.getKillProcessMethod) == null) {
          lspServiceGrpc.getKillProcessMethod = getKillProcessMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.KillProcessRequest, gauge.messages.Lsp.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.lspService", "KillProcess"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.KillProcessRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Lsp.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new lspServiceMethodDescriptorSupplier("KillProcess"))
                  .build();
          }
        }
     }
     return getKillProcessMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static lspServiceStub newStub(io.grpc.Channel channel) {
    return new lspServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static lspServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new lspServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static lspServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new lspServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class lspServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getStepNames(gauge.messages.Messages.StepNamesRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNamesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStepNamesMethod(), responseObserver);
    }

    /**
     */
    public void cacheFile(gauge.messages.Messages.CacheFileRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Lsp.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getCacheFileMethod(), responseObserver);
    }

    /**
     */
    public void getStepPositions(gauge.messages.Messages.StepPositionsRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepPositionsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStepPositionsMethod(), responseObserver);
    }

    /**
     */
    public void getImplementationFiles(gauge.messages.Lsp.Empty request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetImplementationFilesMethod(), responseObserver);
    }

    /**
     */
    public void implementStub(gauge.messages.Messages.StubImplementationCodeRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.FileDiff> responseObserver) {
      asyncUnimplementedUnaryCall(getImplementStubMethod(), responseObserver);
    }

    /**
     */
    public void validateStep(gauge.messages.Messages.StepValidateRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepValidateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getValidateStepMethod(), responseObserver);
    }

    /**
     */
    public void refactor(gauge.messages.Messages.RefactorRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.RefactorResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRefactorMethod(), responseObserver);
    }

    /**
     */
    public void getStepName(gauge.messages.Messages.StepNameRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNameResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStepNameMethod(), responseObserver);
    }

    /**
     */
    public void getGlobPatterns(gauge.messages.Lsp.Empty request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileGlobPatternResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetGlobPatternsMethod(), responseObserver);
    }

    /**
     */
    public void killProcess(gauge.messages.Messages.KillProcessRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Lsp.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getKillProcessMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetStepNamesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StepNamesRequest,
                gauge.messages.Messages.StepNamesResponse>(
                  this, METHODID_GET_STEP_NAMES)))
          .addMethod(
            getCacheFileMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.CacheFileRequest,
                gauge.messages.Lsp.Empty>(
                  this, METHODID_CACHE_FILE)))
          .addMethod(
            getGetStepPositionsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StepPositionsRequest,
                gauge.messages.Messages.StepPositionsResponse>(
                  this, METHODID_GET_STEP_POSITIONS)))
          .addMethod(
            getGetImplementationFilesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Lsp.Empty,
                gauge.messages.Messages.ImplementationFileListResponse>(
                  this, METHODID_GET_IMPLEMENTATION_FILES)))
          .addMethod(
            getImplementStubMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StubImplementationCodeRequest,
                gauge.messages.Messages.FileDiff>(
                  this, METHODID_IMPLEMENT_STUB)))
          .addMethod(
            getValidateStepMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StepValidateRequest,
                gauge.messages.Messages.StepValidateResponse>(
                  this, METHODID_VALIDATE_STEP)))
          .addMethod(
            getRefactorMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.RefactorRequest,
                gauge.messages.Messages.RefactorResponse>(
                  this, METHODID_REFACTOR)))
          .addMethod(
            getGetStepNameMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StepNameRequest,
                gauge.messages.Messages.StepNameResponse>(
                  this, METHODID_GET_STEP_NAME)))
          .addMethod(
            getGetGlobPatternsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Lsp.Empty,
                gauge.messages.Messages.ImplementationFileGlobPatternResponse>(
                  this, METHODID_GET_GLOB_PATTERNS)))
          .addMethod(
            getKillProcessMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.KillProcessRequest,
                gauge.messages.Lsp.Empty>(
                  this, METHODID_KILL_PROCESS)))
          .build();
    }
  }

  /**
   */
  public static final class lspServiceStub extends io.grpc.stub.AbstractStub<lspServiceStub> {
    private lspServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private lspServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected lspServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new lspServiceStub(channel, callOptions);
    }

    /**
     */
    public void getStepNames(gauge.messages.Messages.StepNamesRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNamesResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetStepNamesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void cacheFile(gauge.messages.Messages.CacheFileRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Lsp.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCacheFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStepPositions(gauge.messages.Messages.StepPositionsRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepPositionsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetStepPositionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getImplementationFiles(gauge.messages.Lsp.Empty request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetImplementationFilesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void implementStub(gauge.messages.Messages.StubImplementationCodeRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.FileDiff> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getImplementStubMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void validateStep(gauge.messages.Messages.StepValidateRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepValidateResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getValidateStepMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void refactor(gauge.messages.Messages.RefactorRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.RefactorResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRefactorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStepName(gauge.messages.Messages.StepNameRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNameResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetStepNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getGlobPatterns(gauge.messages.Lsp.Empty request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileGlobPatternResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetGlobPatternsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void killProcess(gauge.messages.Messages.KillProcessRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Lsp.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getKillProcessMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class lspServiceBlockingStub extends io.grpc.stub.AbstractStub<lspServiceBlockingStub> {
    private lspServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private lspServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected lspServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new lspServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public gauge.messages.Messages.StepNamesResponse getStepNames(gauge.messages.Messages.StepNamesRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetStepNamesMethod(), getCallOptions(), request);
    }

    /**
     */
    public gauge.messages.Lsp.Empty cacheFile(gauge.messages.Messages.CacheFileRequest request) {
      return blockingUnaryCall(
          getChannel(), getCacheFileMethod(), getCallOptions(), request);
    }

    /**
     */
    public gauge.messages.Messages.StepPositionsResponse getStepPositions(gauge.messages.Messages.StepPositionsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetStepPositionsMethod(), getCallOptions(), request);
    }

    /**
     */
    public gauge.messages.Messages.ImplementationFileListResponse getImplementationFiles(gauge.messages.Lsp.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetImplementationFilesMethod(), getCallOptions(), request);
    }

    /**
     */
    public gauge.messages.Messages.FileDiff implementStub(gauge.messages.Messages.StubImplementationCodeRequest request) {
      return blockingUnaryCall(
          getChannel(), getImplementStubMethod(), getCallOptions(), request);
    }

    /**
     */
    public gauge.messages.Messages.StepValidateResponse validateStep(gauge.messages.Messages.StepValidateRequest request) {
      return blockingUnaryCall(
          getChannel(), getValidateStepMethod(), getCallOptions(), request);
    }

    /**
     */
    public gauge.messages.Messages.RefactorResponse refactor(gauge.messages.Messages.RefactorRequest request) {
      return blockingUnaryCall(
          getChannel(), getRefactorMethod(), getCallOptions(), request);
    }

    /**
     */
    public gauge.messages.Messages.StepNameResponse getStepName(gauge.messages.Messages.StepNameRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetStepNameMethod(), getCallOptions(), request);
    }

    /**
     */
    public gauge.messages.Messages.ImplementationFileGlobPatternResponse getGlobPatterns(gauge.messages.Lsp.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetGlobPatternsMethod(), getCallOptions(), request);
    }

    /**
     */
    public gauge.messages.Lsp.Empty killProcess(gauge.messages.Messages.KillProcessRequest request) {
      return blockingUnaryCall(
          getChannel(), getKillProcessMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class lspServiceFutureStub extends io.grpc.stub.AbstractStub<lspServiceFutureStub> {
    private lspServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private lspServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected lspServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new lspServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.StepNamesResponse> getStepNames(
        gauge.messages.Messages.StepNamesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetStepNamesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Lsp.Empty> cacheFile(
        gauge.messages.Messages.CacheFileRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCacheFileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.StepPositionsResponse> getStepPositions(
        gauge.messages.Messages.StepPositionsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetStepPositionsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ImplementationFileListResponse> getImplementationFiles(
        gauge.messages.Lsp.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetImplementationFilesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.FileDiff> implementStub(
        gauge.messages.Messages.StubImplementationCodeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getImplementStubMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.StepValidateResponse> validateStep(
        gauge.messages.Messages.StepValidateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getValidateStepMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.RefactorResponse> refactor(
        gauge.messages.Messages.RefactorRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRefactorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.StepNameResponse> getStepName(
        gauge.messages.Messages.StepNameRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetStepNameMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ImplementationFileGlobPatternResponse> getGlobPatterns(
        gauge.messages.Lsp.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetGlobPatternsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Lsp.Empty> killProcess(
        gauge.messages.Messages.KillProcessRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getKillProcessMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_STEP_NAMES = 0;
  private static final int METHODID_CACHE_FILE = 1;
  private static final int METHODID_GET_STEP_POSITIONS = 2;
  private static final int METHODID_GET_IMPLEMENTATION_FILES = 3;
  private static final int METHODID_IMPLEMENT_STUB = 4;
  private static final int METHODID_VALIDATE_STEP = 5;
  private static final int METHODID_REFACTOR = 6;
  private static final int METHODID_GET_STEP_NAME = 7;
  private static final int METHODID_GET_GLOB_PATTERNS = 8;
  private static final int METHODID_KILL_PROCESS = 9;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final lspServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(lspServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STEP_NAMES:
          serviceImpl.getStepNames((gauge.messages.Messages.StepNamesRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNamesResponse>) responseObserver);
          break;
        case METHODID_CACHE_FILE:
          serviceImpl.cacheFile((gauge.messages.Messages.CacheFileRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Lsp.Empty>) responseObserver);
          break;
        case METHODID_GET_STEP_POSITIONS:
          serviceImpl.getStepPositions((gauge.messages.Messages.StepPositionsRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.StepPositionsResponse>) responseObserver);
          break;
        case METHODID_GET_IMPLEMENTATION_FILES:
          serviceImpl.getImplementationFiles((gauge.messages.Lsp.Empty) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileListResponse>) responseObserver);
          break;
        case METHODID_IMPLEMENT_STUB:
          serviceImpl.implementStub((gauge.messages.Messages.StubImplementationCodeRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.FileDiff>) responseObserver);
          break;
        case METHODID_VALIDATE_STEP:
          serviceImpl.validateStep((gauge.messages.Messages.StepValidateRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.StepValidateResponse>) responseObserver);
          break;
        case METHODID_REFACTOR:
          serviceImpl.refactor((gauge.messages.Messages.RefactorRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.RefactorResponse>) responseObserver);
          break;
        case METHODID_GET_STEP_NAME:
          serviceImpl.getStepName((gauge.messages.Messages.StepNameRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNameResponse>) responseObserver);
          break;
        case METHODID_GET_GLOB_PATTERNS:
          serviceImpl.getGlobPatterns((gauge.messages.Lsp.Empty) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileGlobPatternResponse>) responseObserver);
          break;
        case METHODID_KILL_PROCESS:
          serviceImpl.killProcess((gauge.messages.Messages.KillProcessRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Lsp.Empty>) responseObserver);
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

  private static abstract class lspServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    lspServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gauge.messages.Lsp.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("lspService");
    }
  }

  private static final class lspServiceFileDescriptorSupplier
      extends lspServiceBaseDescriptorSupplier {
    lspServiceFileDescriptorSupplier() {}
  }

  private static final class lspServiceMethodDescriptorSupplier
      extends lspServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    lspServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (lspServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new lspServiceFileDescriptorSupplier())
              .addMethod(getGetStepNamesMethod())
              .addMethod(getCacheFileMethod())
              .addMethod(getGetStepPositionsMethod())
              .addMethod(getGetImplementationFilesMethod())
              .addMethod(getImplementStubMethod())
              .addMethod(getValidateStepMethod())
              .addMethod(getRefactorMethod())
              .addMethod(getGetStepNameMethod())
              .addMethod(getGetGlobPatternsMethod())
              .addMethod(getKillProcessMethod())
              .build();
        }
      }
    }
    return result;
  }
}
