package gauge.messages;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class RunnerGrpc {

  private RunnerGrpc() {}

  public static final java.lang.String SERVICE_NAME = "gauge.messages.Runner";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepValidateRequest,
      gauge.messages.Messages.StepValidateResponse> getValidateStepMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidateStep",
      requestType = gauge.messages.Messages.StepValidateRequest.class,
      responseType = gauge.messages.Messages.StepValidateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepValidateRequest,
      gauge.messages.Messages.StepValidateResponse> getValidateStepMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepValidateRequest, gauge.messages.Messages.StepValidateResponse> getValidateStepMethod;
    if ((getValidateStepMethod = RunnerGrpc.getValidateStepMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getValidateStepMethod = RunnerGrpc.getValidateStepMethod) == null) {
          RunnerGrpc.getValidateStepMethod = getValidateStepMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepValidateRequest, gauge.messages.Messages.StepValidateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ValidateStep"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepValidateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepValidateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("ValidateStep"))
              .build();
        }
      }
    }
    return getValidateStepMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.SuiteDataStoreInitRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getInitializeSuiteDataStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "InitializeSuiteDataStore",
      requestType = gauge.messages.Messages.SuiteDataStoreInitRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SuiteDataStoreInitRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getInitializeSuiteDataStoreMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SuiteDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse> getInitializeSuiteDataStoreMethod;
    if ((getInitializeSuiteDataStoreMethod = RunnerGrpc.getInitializeSuiteDataStoreMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getInitializeSuiteDataStoreMethod = RunnerGrpc.getInitializeSuiteDataStoreMethod) == null) {
          RunnerGrpc.getInitializeSuiteDataStoreMethod = getInitializeSuiteDataStoreMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SuiteDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "InitializeSuiteDataStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.SuiteDataStoreInitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("InitializeSuiteDataStore"))
              .build();
        }
      }
    }
    return getInitializeSuiteDataStoreMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartExecutionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartExecution",
      requestType = gauge.messages.Messages.ExecutionStartingRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse> getStartExecutionMethod;
    if ((getStartExecutionMethod = RunnerGrpc.getStartExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getStartExecutionMethod = RunnerGrpc.getStartExecutionMethod) == null) {
          RunnerGrpc.getStartExecutionMethod = getStartExecutionMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StartExecution"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStartingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("StartExecution"))
              .build();
        }
      }
    }
    return getStartExecutionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.SpecDataStoreInitRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getInitializeSpecDataStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "InitializeSpecDataStore",
      requestType = gauge.messages.Messages.SpecDataStoreInitRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SpecDataStoreInitRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getInitializeSpecDataStoreMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SpecDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse> getInitializeSpecDataStoreMethod;
    if ((getInitializeSpecDataStoreMethod = RunnerGrpc.getInitializeSpecDataStoreMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getInitializeSpecDataStoreMethod = RunnerGrpc.getInitializeSpecDataStoreMethod) == null) {
          RunnerGrpc.getInitializeSpecDataStoreMethod = getInitializeSpecDataStoreMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SpecDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "InitializeSpecDataStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.SpecDataStoreInitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("InitializeSpecDataStore"))
              .build();
        }
      }
    }
    return getInitializeSpecDataStoreMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartSpecExecutionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartSpecExecution",
      requestType = gauge.messages.Messages.SpecExecutionStartingRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartSpecExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse> getStartSpecExecutionMethod;
    if ((getStartSpecExecutionMethod = RunnerGrpc.getStartSpecExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getStartSpecExecutionMethod = RunnerGrpc.getStartSpecExecutionMethod) == null) {
          RunnerGrpc.getStartSpecExecutionMethod = getStartSpecExecutionMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SpecExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StartSpecExecution"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.SpecExecutionStartingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("StartSpecExecution"))
              .build();
        }
      }
    }
    return getStartSpecExecutionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioDataStoreInitRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getInitializeScenarioDataStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "InitializeScenarioDataStore",
      requestType = gauge.messages.Messages.ScenarioDataStoreInitRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioDataStoreInitRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getInitializeScenarioDataStoreMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse> getInitializeScenarioDataStoreMethod;
    if ((getInitializeScenarioDataStoreMethod = RunnerGrpc.getInitializeScenarioDataStoreMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getInitializeScenarioDataStoreMethod = RunnerGrpc.getInitializeScenarioDataStoreMethod) == null) {
          RunnerGrpc.getInitializeScenarioDataStoreMethod = getInitializeScenarioDataStoreMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ScenarioDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "InitializeScenarioDataStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ScenarioDataStoreInitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("InitializeScenarioDataStore"))
              .build();
        }
      }
    }
    return getInitializeScenarioDataStoreMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartScenarioExecutionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartScenarioExecution",
      requestType = gauge.messages.Messages.ScenarioExecutionStartingRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartScenarioExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse> getStartScenarioExecutionMethod;
    if ((getStartScenarioExecutionMethod = RunnerGrpc.getStartScenarioExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getStartScenarioExecutionMethod = RunnerGrpc.getStartScenarioExecutionMethod) == null) {
          RunnerGrpc.getStartScenarioExecutionMethod = getStartScenarioExecutionMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ScenarioExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StartScenarioExecution"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ScenarioExecutionStartingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("StartScenarioExecution"))
              .build();
        }
      }
    }
    return getStartScenarioExecutionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartStepExecutionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartStepExecution",
      requestType = gauge.messages.Messages.StepExecutionStartingRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartStepExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse> getStartStepExecutionMethod;
    if ((getStartStepExecutionMethod = RunnerGrpc.getStartStepExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getStartStepExecutionMethod = RunnerGrpc.getStartStepExecutionMethod) == null) {
          RunnerGrpc.getStartStepExecutionMethod = getStartStepExecutionMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StartStepExecution"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepExecutionStartingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("StartStepExecution"))
              .build();
        }
      }
    }
    return getStartStepExecutionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.ExecuteStepRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getExecuteStepMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExecuteStep",
      requestType = gauge.messages.Messages.ExecuteStepRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ExecuteStepRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getExecuteStepMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ExecuteStepRequest, gauge.messages.Messages.ExecutionStatusResponse> getExecuteStepMethod;
    if ((getExecuteStepMethod = RunnerGrpc.getExecuteStepMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getExecuteStepMethod = RunnerGrpc.getExecuteStepMethod) == null) {
          RunnerGrpc.getExecuteStepMethod = getExecuteStepMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ExecuteStepRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ExecuteStep"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecuteStepRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("ExecuteStep"))
              .build();
        }
      }
    }
    return getExecuteStepMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishStepExecutionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FinishStepExecution",
      requestType = gauge.messages.Messages.StepExecutionEndingRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishStepExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse> getFinishStepExecutionMethod;
    if ((getFinishStepExecutionMethod = RunnerGrpc.getFinishStepExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getFinishStepExecutionMethod = RunnerGrpc.getFinishStepExecutionMethod) == null) {
          RunnerGrpc.getFinishStepExecutionMethod = getFinishStepExecutionMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FinishStepExecution"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepExecutionEndingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("FinishStepExecution"))
              .build();
        }
      }
    }
    return getFinishStepExecutionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishScenarioExecutionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FinishScenarioExecution",
      requestType = gauge.messages.Messages.ScenarioExecutionEndingRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishScenarioExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse> getFinishScenarioExecutionMethod;
    if ((getFinishScenarioExecutionMethod = RunnerGrpc.getFinishScenarioExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getFinishScenarioExecutionMethod = RunnerGrpc.getFinishScenarioExecutionMethod) == null) {
          RunnerGrpc.getFinishScenarioExecutionMethod = getFinishScenarioExecutionMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ScenarioExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FinishScenarioExecution"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ScenarioExecutionEndingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("FinishScenarioExecution"))
              .build();
        }
      }
    }
    return getFinishScenarioExecutionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishSpecExecutionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FinishSpecExecution",
      requestType = gauge.messages.Messages.SpecExecutionEndingRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishSpecExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse> getFinishSpecExecutionMethod;
    if ((getFinishSpecExecutionMethod = RunnerGrpc.getFinishSpecExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getFinishSpecExecutionMethod = RunnerGrpc.getFinishSpecExecutionMethod) == null) {
          RunnerGrpc.getFinishSpecExecutionMethod = getFinishSpecExecutionMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SpecExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FinishSpecExecution"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.SpecExecutionEndingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("FinishSpecExecution"))
              .build();
        }
      }
    }
    return getFinishSpecExecutionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishExecutionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FinishExecution",
      requestType = gauge.messages.Messages.ExecutionEndingRequest.class,
      responseType = gauge.messages.Messages.ExecutionStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse> getFinishExecutionMethod;
    if ((getFinishExecutionMethod = RunnerGrpc.getFinishExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getFinishExecutionMethod = RunnerGrpc.getFinishExecutionMethod) == null) {
          RunnerGrpc.getFinishExecutionMethod = getFinishExecutionMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FinishExecution"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionEndingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("FinishExecution"))
              .build();
        }
      }
    }
    return getFinishExecutionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.CacheFileRequest,
      gauge.messages.Messages.Empty> getCacheFileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CacheFile",
      requestType = gauge.messages.Messages.CacheFileRequest.class,
      responseType = gauge.messages.Messages.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.CacheFileRequest,
      gauge.messages.Messages.Empty> getCacheFileMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.CacheFileRequest, gauge.messages.Messages.Empty> getCacheFileMethod;
    if ((getCacheFileMethod = RunnerGrpc.getCacheFileMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getCacheFileMethod = RunnerGrpc.getCacheFileMethod) == null) {
          RunnerGrpc.getCacheFileMethod = getCacheFileMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.CacheFileRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CacheFile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.CacheFileRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("CacheFile"))
              .build();
        }
      }
    }
    return getCacheFileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepNameRequest,
      gauge.messages.Messages.StepNameResponse> getGetStepNameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStepName",
      requestType = gauge.messages.Messages.StepNameRequest.class,
      responseType = gauge.messages.Messages.StepNameResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepNameRequest,
      gauge.messages.Messages.StepNameResponse> getGetStepNameMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepNameRequest, gauge.messages.Messages.StepNameResponse> getGetStepNameMethod;
    if ((getGetStepNameMethod = RunnerGrpc.getGetStepNameMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getGetStepNameMethod = RunnerGrpc.getGetStepNameMethod) == null) {
          RunnerGrpc.getGetStepNameMethod = getGetStepNameMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepNameRequest, gauge.messages.Messages.StepNameResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStepName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepNameRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepNameResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("GetStepName"))
              .build();
        }
      }
    }
    return getGetStepNameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.Empty,
      gauge.messages.Messages.ImplementationFileGlobPatternResponse> getGetGlobPatternsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetGlobPatterns",
      requestType = gauge.messages.Messages.Empty.class,
      responseType = gauge.messages.Messages.ImplementationFileGlobPatternResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.Empty,
      gauge.messages.Messages.ImplementationFileGlobPatternResponse> getGetGlobPatternsMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.Empty, gauge.messages.Messages.ImplementationFileGlobPatternResponse> getGetGlobPatternsMethod;
    if ((getGetGlobPatternsMethod = RunnerGrpc.getGetGlobPatternsMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getGetGlobPatternsMethod = RunnerGrpc.getGetGlobPatternsMethod) == null) {
          RunnerGrpc.getGetGlobPatternsMethod = getGetGlobPatternsMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.Empty, gauge.messages.Messages.ImplementationFileGlobPatternResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetGlobPatterns"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ImplementationFileGlobPatternResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("GetGlobPatterns"))
              .build();
        }
      }
    }
    return getGetGlobPatternsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepNamesRequest,
      gauge.messages.Messages.StepNamesResponse> getGetStepNamesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStepNames",
      requestType = gauge.messages.Messages.StepNamesRequest.class,
      responseType = gauge.messages.Messages.StepNamesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepNamesRequest,
      gauge.messages.Messages.StepNamesResponse> getGetStepNamesMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepNamesRequest, gauge.messages.Messages.StepNamesResponse> getGetStepNamesMethod;
    if ((getGetStepNamesMethod = RunnerGrpc.getGetStepNamesMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getGetStepNamesMethod = RunnerGrpc.getGetStepNamesMethod) == null) {
          RunnerGrpc.getGetStepNamesMethod = getGetStepNamesMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepNamesRequest, gauge.messages.Messages.StepNamesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStepNames"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepNamesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepNamesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("GetStepNames"))
              .build();
        }
      }
    }
    return getGetStepNamesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepPositionsRequest,
      gauge.messages.Messages.StepPositionsResponse> getGetStepPositionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStepPositions",
      requestType = gauge.messages.Messages.StepPositionsRequest.class,
      responseType = gauge.messages.Messages.StepPositionsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepPositionsRequest,
      gauge.messages.Messages.StepPositionsResponse> getGetStepPositionsMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepPositionsRequest, gauge.messages.Messages.StepPositionsResponse> getGetStepPositionsMethod;
    if ((getGetStepPositionsMethod = RunnerGrpc.getGetStepPositionsMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getGetStepPositionsMethod = RunnerGrpc.getGetStepPositionsMethod) == null) {
          RunnerGrpc.getGetStepPositionsMethod = getGetStepPositionsMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepPositionsRequest, gauge.messages.Messages.StepPositionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStepPositions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepPositionsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepPositionsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("GetStepPositions"))
              .build();
        }
      }
    }
    return getGetStepPositionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.Empty,
      gauge.messages.Messages.ImplementationFileListResponse> getGetImplementationFilesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetImplementationFiles",
      requestType = gauge.messages.Messages.Empty.class,
      responseType = gauge.messages.Messages.ImplementationFileListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.Empty,
      gauge.messages.Messages.ImplementationFileListResponse> getGetImplementationFilesMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.Empty, gauge.messages.Messages.ImplementationFileListResponse> getGetImplementationFilesMethod;
    if ((getGetImplementationFilesMethod = RunnerGrpc.getGetImplementationFilesMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getGetImplementationFilesMethod = RunnerGrpc.getGetImplementationFilesMethod) == null) {
          RunnerGrpc.getGetImplementationFilesMethod = getGetImplementationFilesMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.Empty, gauge.messages.Messages.ImplementationFileListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetImplementationFiles"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ImplementationFileListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("GetImplementationFiles"))
              .build();
        }
      }
    }
    return getGetImplementationFilesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StubImplementationCodeRequest,
      gauge.messages.Messages.FileDiff> getImplementStubMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ImplementStub",
      requestType = gauge.messages.Messages.StubImplementationCodeRequest.class,
      responseType = gauge.messages.Messages.FileDiff.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StubImplementationCodeRequest,
      gauge.messages.Messages.FileDiff> getImplementStubMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StubImplementationCodeRequest, gauge.messages.Messages.FileDiff> getImplementStubMethod;
    if ((getImplementStubMethod = RunnerGrpc.getImplementStubMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getImplementStubMethod = RunnerGrpc.getImplementStubMethod) == null) {
          RunnerGrpc.getImplementStubMethod = getImplementStubMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StubImplementationCodeRequest, gauge.messages.Messages.FileDiff>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ImplementStub"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StubImplementationCodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.FileDiff.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("ImplementStub"))
              .build();
        }
      }
    }
    return getImplementStubMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.RefactorRequest,
      gauge.messages.Messages.RefactorResponse> getRefactorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Refactor",
      requestType = gauge.messages.Messages.RefactorRequest.class,
      responseType = gauge.messages.Messages.RefactorResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.RefactorRequest,
      gauge.messages.Messages.RefactorResponse> getRefactorMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.RefactorRequest, gauge.messages.Messages.RefactorResponse> getRefactorMethod;
    if ((getRefactorMethod = RunnerGrpc.getRefactorMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getRefactorMethod = RunnerGrpc.getRefactorMethod) == null) {
          RunnerGrpc.getRefactorMethod = getRefactorMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.RefactorRequest, gauge.messages.Messages.RefactorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Refactor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.RefactorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.RefactorResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("Refactor"))
              .build();
        }
      }
    }
    return getRefactorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest,
      gauge.messages.Messages.Empty> getKillMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Kill",
      requestType = gauge.messages.Messages.KillProcessRequest.class,
      responseType = gauge.messages.Messages.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest,
      gauge.messages.Messages.Empty> getKillMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest, gauge.messages.Messages.Empty> getKillMethod;
    if ((getKillMethod = RunnerGrpc.getKillMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getKillMethod = RunnerGrpc.getKillMethod) == null) {
          RunnerGrpc.getKillMethod = getKillMethod =
              io.grpc.MethodDescriptor.<gauge.messages.Messages.KillProcessRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Kill"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.KillProcessRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new RunnerMethodDescriptorSupplier("Kill"))
              .build();
        }
      }
    }
    return getKillMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RunnerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RunnerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RunnerStub>() {
        @java.lang.Override
        public RunnerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RunnerStub(channel, callOptions);
        }
      };
    return RunnerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RunnerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RunnerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RunnerBlockingStub>() {
        @java.lang.Override
        public RunnerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RunnerBlockingStub(channel, callOptions);
        }
      };
    return RunnerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RunnerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RunnerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RunnerFutureStub>() {
        @java.lang.Override
        public RunnerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RunnerFutureStub(channel, callOptions);
        }
      };
    return RunnerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * ValidateStep is a RPC to validate a given step.
     * Accepts a StepValidateRequest message and returns a StepValidateResponse message
     * </pre>
     */
    default void validateStep(gauge.messages.Messages.StepValidateRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepValidateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidateStepMethod(), responseObserver);
    }

    /**
     * <pre>
     * SuiteDataStoreInit is a RPC to initialize the suite level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void initializeSuiteDataStore(gauge.messages.Messages.SuiteDataStoreInitRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInitializeSuiteDataStoreMethod(), responseObserver);
    }

    /**
     * <pre>
     * ExecutionStarting is a RPC to tell runner to execute Suite level hooks.
     * Accepts a ExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void startExecution(gauge.messages.Messages.ExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStartExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * SpecDataStoreInit is a RPC to initialize the spec level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void initializeSpecDataStore(gauge.messages.Messages.SpecDataStoreInitRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInitializeSpecDataStoreMethod(), responseObserver);
    }

    /**
     * <pre>
     * SpecExecutionStarting is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void startSpecExecution(gauge.messages.Messages.SpecExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStartSpecExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * ScenarioDataStoreInit is a RPC to initialize the scenario level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void initializeScenarioDataStore(gauge.messages.Messages.ScenarioDataStoreInitRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInitializeScenarioDataStoreMethod(), responseObserver);
    }

    /**
     * <pre>
     * ScenarioExecutionStarting is a RPC to tell runner to execute scenario level hooks.
     * Accepts a ScenarioExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void startScenarioExecution(gauge.messages.Messages.ScenarioExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStartScenarioExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * StepExecutionStarting is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void startStepExecution(gauge.messages.Messages.StepExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStartStepExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * ExecuteStep is a RPC to tell runner to execute a step .
     * Accepts a ExecuteStepRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void executeStep(gauge.messages.Messages.ExecuteStepRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExecuteStepMethod(), responseObserver);
    }

    /**
     * <pre>
     * StepExecutionEnding is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void finishStepExecution(gauge.messages.Messages.StepExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFinishStepExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * ScenarioExecutionEnding is a RPC to tell runner to execute Scenario level hooks.
     * Accepts a ScenarioExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void finishScenarioExecution(gauge.messages.Messages.ScenarioExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFinishScenarioExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * SpecExecutionEnding is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void finishSpecExecution(gauge.messages.Messages.SpecExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFinishSpecExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * ExecutionEnding is a RPC to tell runner to execute suite level hooks.
     * Accepts a ExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    default void finishExecution(gauge.messages.Messages.ExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFinishExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * CacheFile is a RPC to tell runner to load/reload/unload a implementation file.
     * Accepts a CacheFileRequest message and returns a Empty message
     * </pre>
     */
    default void cacheFile(gauge.messages.Messages.CacheFileRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCacheFileMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetStepName is a RPC to get information about the given step.
     * Accepts a StepNameRequest message and returns a StepNameResponse message.
     * </pre>
     */
    default void getStepName(gauge.messages.Messages.StepNameRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNameResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStepNameMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetGlobPatterns is a RPC to get the file path pattern which needs to be cached.
     * Accepts a Empty message and returns a ImplementationFileGlobPatternResponse message.
     * </pre>
     */
    default void getGlobPatterns(gauge.messages.Messages.Empty request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileGlobPatternResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetGlobPatternsMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetStepNames is a RPC to get all the available steps from the runner.
     * Accepts a StepNamesRequest message and returns a StepNamesResponse
     * </pre>
     */
    default void getStepNames(gauge.messages.Messages.StepNamesRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNamesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStepNamesMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetStepPositions is a RPC to get positions of all available steps in a given file.
     * Accepts a StepPositionsRequest message and returns a StepPositionsResponse message
     * </pre>
     */
    default void getStepPositions(gauge.messages.Messages.StepPositionsRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepPositionsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStepPositionsMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetImplementationFiles is a RPC get all the existing implementation files.
     * Accepts a Empty and returns a ImplementationFileListResponse message.
     * </pre>
     */
    default void getImplementationFiles(gauge.messages.Messages.Empty request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetImplementationFilesMethod(), responseObserver);
    }

    /**
     * <pre>
     * ImplementStub is a RPC to to ask runner to add a given implementation to given file.
     * Accepts a StubImplementationCodeRequest and returns a FileDiff message.
     * </pre>
     */
    default void implementStub(gauge.messages.Messages.StubImplementationCodeRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.FileDiff> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getImplementStubMethod(), responseObserver);
    }

    /**
     * <pre>
     * Refactor is a RPC to refactor a given step in implementation file.
     * Accepts a RefactorRequest message and returns a RefactorResponse message.
     * </pre>
     */
    default void refactor(gauge.messages.Messages.RefactorRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.RefactorResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRefactorMethod(), responseObserver);
    }

    /**
     * <pre>
     * Kill is a RPC tell plugin to stop grpc server and kill the plugin process.
     * Accepts a KillProcessRequest message and returns a Empty message.
     * </pre>
     */
    default void kill(gauge.messages.Messages.KillProcessRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getKillMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Runner.
   */
  public static abstract class RunnerImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return RunnerGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Runner.
   */
  public static final class RunnerStub
      extends io.grpc.stub.AbstractAsyncStub<RunnerStub> {
    private RunnerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RunnerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RunnerStub(channel, callOptions);
    }

    /**
     * <pre>
     * ValidateStep is a RPC to validate a given step.
     * Accepts a StepValidateRequest message and returns a StepValidateResponse message
     * </pre>
     */
    public void validateStep(gauge.messages.Messages.StepValidateRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepValidateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidateStepMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * SuiteDataStoreInit is a RPC to initialize the suite level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void initializeSuiteDataStore(gauge.messages.Messages.SuiteDataStoreInitRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInitializeSuiteDataStoreMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ExecutionStarting is a RPC to tell runner to execute Suite level hooks.
     * Accepts a ExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void startExecution(gauge.messages.Messages.ExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStartExecutionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * SpecDataStoreInit is a RPC to initialize the spec level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void initializeSpecDataStore(gauge.messages.Messages.SpecDataStoreInitRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInitializeSpecDataStoreMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * SpecExecutionStarting is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void startSpecExecution(gauge.messages.Messages.SpecExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStartSpecExecutionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ScenarioDataStoreInit is a RPC to initialize the scenario level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void initializeScenarioDataStore(gauge.messages.Messages.ScenarioDataStoreInitRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInitializeScenarioDataStoreMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ScenarioExecutionStarting is a RPC to tell runner to execute scenario level hooks.
     * Accepts a ScenarioExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void startScenarioExecution(gauge.messages.Messages.ScenarioExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStartScenarioExecutionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * StepExecutionStarting is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void startStepExecution(gauge.messages.Messages.StepExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStartStepExecutionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ExecuteStep is a RPC to tell runner to execute a step .
     * Accepts a ExecuteStepRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void executeStep(gauge.messages.Messages.ExecuteStepRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExecuteStepMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * StepExecutionEnding is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void finishStepExecution(gauge.messages.Messages.StepExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFinishStepExecutionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ScenarioExecutionEnding is a RPC to tell runner to execute Scenario level hooks.
     * Accepts a ScenarioExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void finishScenarioExecution(gauge.messages.Messages.ScenarioExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFinishScenarioExecutionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * SpecExecutionEnding is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void finishSpecExecution(gauge.messages.Messages.SpecExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFinishSpecExecutionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ExecutionEnding is a RPC to tell runner to execute suite level hooks.
     * Accepts a ExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void finishExecution(gauge.messages.Messages.ExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFinishExecutionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * CacheFile is a RPC to tell runner to load/reload/unload a implementation file.
     * Accepts a CacheFileRequest message and returns a Empty message
     * </pre>
     */
    public void cacheFile(gauge.messages.Messages.CacheFileRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCacheFileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetStepName is a RPC to get information about the given step.
     * Accepts a StepNameRequest message and returns a StepNameResponse message.
     * </pre>
     */
    public void getStepName(gauge.messages.Messages.StepNameRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNameResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStepNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetGlobPatterns is a RPC to get the file path pattern which needs to be cached.
     * Accepts a Empty message and returns a ImplementationFileGlobPatternResponse message.
     * </pre>
     */
    public void getGlobPatterns(gauge.messages.Messages.Empty request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileGlobPatternResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetGlobPatternsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetStepNames is a RPC to get all the available steps from the runner.
     * Accepts a StepNamesRequest message and returns a StepNamesResponse
     * </pre>
     */
    public void getStepNames(gauge.messages.Messages.StepNamesRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNamesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStepNamesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetStepPositions is a RPC to get positions of all available steps in a given file.
     * Accepts a StepPositionsRequest message and returns a StepPositionsResponse message
     * </pre>
     */
    public void getStepPositions(gauge.messages.Messages.StepPositionsRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepPositionsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStepPositionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * GetImplementationFiles is a RPC get all the existing implementation files.
     * Accepts a Empty and returns a ImplementationFileListResponse message.
     * </pre>
     */
    public void getImplementationFiles(gauge.messages.Messages.Empty request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetImplementationFilesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ImplementStub is a RPC to to ask runner to add a given implementation to given file.
     * Accepts a StubImplementationCodeRequest and returns a FileDiff message.
     * </pre>
     */
    public void implementStub(gauge.messages.Messages.StubImplementationCodeRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.FileDiff> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getImplementStubMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Refactor is a RPC to refactor a given step in implementation file.
     * Accepts a RefactorRequest message and returns a RefactorResponse message.
     * </pre>
     */
    public void refactor(gauge.messages.Messages.RefactorRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.RefactorResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRefactorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Kill is a RPC tell plugin to stop grpc server and kill the plugin process.
     * Accepts a KillProcessRequest message and returns a Empty message.
     * </pre>
     */
    public void kill(gauge.messages.Messages.KillProcessRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getKillMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Runner.
   */
  public static final class RunnerBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<RunnerBlockingStub> {
    private RunnerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RunnerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RunnerBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * ValidateStep is a RPC to validate a given step.
     * Accepts a StepValidateRequest message and returns a StepValidateResponse message
     * </pre>
     */
    public gauge.messages.Messages.StepValidateResponse validateStep(gauge.messages.Messages.StepValidateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidateStepMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * SuiteDataStoreInit is a RPC to initialize the suite level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse initializeSuiteDataStore(gauge.messages.Messages.SuiteDataStoreInitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInitializeSuiteDataStoreMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ExecutionStarting is a RPC to tell runner to execute Suite level hooks.
     * Accepts a ExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse startExecution(gauge.messages.Messages.ExecutionStartingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStartExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * SpecDataStoreInit is a RPC to initialize the spec level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse initializeSpecDataStore(gauge.messages.Messages.SpecDataStoreInitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInitializeSpecDataStoreMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * SpecExecutionStarting is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse startSpecExecution(gauge.messages.Messages.SpecExecutionStartingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStartSpecExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ScenarioDataStoreInit is a RPC to initialize the scenario level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse initializeScenarioDataStore(gauge.messages.Messages.ScenarioDataStoreInitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInitializeScenarioDataStoreMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ScenarioExecutionStarting is a RPC to tell runner to execute scenario level hooks.
     * Accepts a ScenarioExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse startScenarioExecution(gauge.messages.Messages.ScenarioExecutionStartingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStartScenarioExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * StepExecutionStarting is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse startStepExecution(gauge.messages.Messages.StepExecutionStartingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStartStepExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ExecuteStep is a RPC to tell runner to execute a step .
     * Accepts a ExecuteStepRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse executeStep(gauge.messages.Messages.ExecuteStepRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExecuteStepMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * StepExecutionEnding is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse finishStepExecution(gauge.messages.Messages.StepExecutionEndingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFinishStepExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ScenarioExecutionEnding is a RPC to tell runner to execute Scenario level hooks.
     * Accepts a ScenarioExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse finishScenarioExecution(gauge.messages.Messages.ScenarioExecutionEndingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFinishScenarioExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * SpecExecutionEnding is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse finishSpecExecution(gauge.messages.Messages.SpecExecutionEndingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFinishSpecExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ExecutionEnding is a RPC to tell runner to execute suite level hooks.
     * Accepts a ExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse finishExecution(gauge.messages.Messages.ExecutionEndingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFinishExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * CacheFile is a RPC to tell runner to load/reload/unload a implementation file.
     * Accepts a CacheFileRequest message and returns a Empty message
     * </pre>
     */
    public gauge.messages.Messages.Empty cacheFile(gauge.messages.Messages.CacheFileRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCacheFileMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetStepName is a RPC to get information about the given step.
     * Accepts a StepNameRequest message and returns a StepNameResponse message.
     * </pre>
     */
    public gauge.messages.Messages.StepNameResponse getStepName(gauge.messages.Messages.StepNameRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStepNameMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetGlobPatterns is a RPC to get the file path pattern which needs to be cached.
     * Accepts a Empty message and returns a ImplementationFileGlobPatternResponse message.
     * </pre>
     */
    public gauge.messages.Messages.ImplementationFileGlobPatternResponse getGlobPatterns(gauge.messages.Messages.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetGlobPatternsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetStepNames is a RPC to get all the available steps from the runner.
     * Accepts a StepNamesRequest message and returns a StepNamesResponse
     * </pre>
     */
    public gauge.messages.Messages.StepNamesResponse getStepNames(gauge.messages.Messages.StepNamesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStepNamesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetStepPositions is a RPC to get positions of all available steps in a given file.
     * Accepts a StepPositionsRequest message and returns a StepPositionsResponse message
     * </pre>
     */
    public gauge.messages.Messages.StepPositionsResponse getStepPositions(gauge.messages.Messages.StepPositionsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStepPositionsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetImplementationFiles is a RPC get all the existing implementation files.
     * Accepts a Empty and returns a ImplementationFileListResponse message.
     * </pre>
     */
    public gauge.messages.Messages.ImplementationFileListResponse getImplementationFiles(gauge.messages.Messages.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetImplementationFilesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ImplementStub is a RPC to to ask runner to add a given implementation to given file.
     * Accepts a StubImplementationCodeRequest and returns a FileDiff message.
     * </pre>
     */
    public gauge.messages.Messages.FileDiff implementStub(gauge.messages.Messages.StubImplementationCodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getImplementStubMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Refactor is a RPC to refactor a given step in implementation file.
     * Accepts a RefactorRequest message and returns a RefactorResponse message.
     * </pre>
     */
    public gauge.messages.Messages.RefactorResponse refactor(gauge.messages.Messages.RefactorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRefactorMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Kill is a RPC tell plugin to stop grpc server and kill the plugin process.
     * Accepts a KillProcessRequest message and returns a Empty message.
     * </pre>
     */
    public gauge.messages.Messages.Empty kill(gauge.messages.Messages.KillProcessRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getKillMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Runner.
   */
  public static final class RunnerFutureStub
      extends io.grpc.stub.AbstractFutureStub<RunnerFutureStub> {
    private RunnerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RunnerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RunnerFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * ValidateStep is a RPC to validate a given step.
     * Accepts a StepValidateRequest message and returns a StepValidateResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.StepValidateResponse> validateStep(
        gauge.messages.Messages.StepValidateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidateStepMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * SuiteDataStoreInit is a RPC to initialize the suite level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> initializeSuiteDataStore(
        gauge.messages.Messages.SuiteDataStoreInitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInitializeSuiteDataStoreMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ExecutionStarting is a RPC to tell runner to execute Suite level hooks.
     * Accepts a ExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> startExecution(
        gauge.messages.Messages.ExecutionStartingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStartExecutionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * SpecDataStoreInit is a RPC to initialize the spec level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> initializeSpecDataStore(
        gauge.messages.Messages.SpecDataStoreInitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInitializeSpecDataStoreMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * SpecExecutionStarting is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> startSpecExecution(
        gauge.messages.Messages.SpecExecutionStartingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStartSpecExecutionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ScenarioDataStoreInit is a RPC to initialize the scenario level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> initializeScenarioDataStore(
        gauge.messages.Messages.ScenarioDataStoreInitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInitializeScenarioDataStoreMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ScenarioExecutionStarting is a RPC to tell runner to execute scenario level hooks.
     * Accepts a ScenarioExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> startScenarioExecution(
        gauge.messages.Messages.ScenarioExecutionStartingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStartScenarioExecutionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * StepExecutionStarting is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> startStepExecution(
        gauge.messages.Messages.StepExecutionStartingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStartStepExecutionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ExecuteStep is a RPC to tell runner to execute a step .
     * Accepts a ExecuteStepRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> executeStep(
        gauge.messages.Messages.ExecuteStepRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExecuteStepMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * StepExecutionEnding is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> finishStepExecution(
        gauge.messages.Messages.StepExecutionEndingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFinishStepExecutionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ScenarioExecutionEnding is a RPC to tell runner to execute Scenario level hooks.
     * Accepts a ScenarioExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> finishScenarioExecution(
        gauge.messages.Messages.ScenarioExecutionEndingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFinishScenarioExecutionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * SpecExecutionEnding is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> finishSpecExecution(
        gauge.messages.Messages.SpecExecutionEndingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFinishSpecExecutionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ExecutionEnding is a RPC to tell runner to execute suite level hooks.
     * Accepts a ExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ExecutionStatusResponse> finishExecution(
        gauge.messages.Messages.ExecutionEndingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFinishExecutionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * CacheFile is a RPC to tell runner to load/reload/unload a implementation file.
     * Accepts a CacheFileRequest message and returns a Empty message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> cacheFile(
        gauge.messages.Messages.CacheFileRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCacheFileMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetStepName is a RPC to get information about the given step.
     * Accepts a StepNameRequest message and returns a StepNameResponse message.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.StepNameResponse> getStepName(
        gauge.messages.Messages.StepNameRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStepNameMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetGlobPatterns is a RPC to get the file path pattern which needs to be cached.
     * Accepts a Empty message and returns a ImplementationFileGlobPatternResponse message.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ImplementationFileGlobPatternResponse> getGlobPatterns(
        gauge.messages.Messages.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetGlobPatternsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetStepNames is a RPC to get all the available steps from the runner.
     * Accepts a StepNamesRequest message and returns a StepNamesResponse
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.StepNamesResponse> getStepNames(
        gauge.messages.Messages.StepNamesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStepNamesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetStepPositions is a RPC to get positions of all available steps in a given file.
     * Accepts a StepPositionsRequest message and returns a StepPositionsResponse message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.StepPositionsResponse> getStepPositions(
        gauge.messages.Messages.StepPositionsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStepPositionsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * GetImplementationFiles is a RPC get all the existing implementation files.
     * Accepts a Empty and returns a ImplementationFileListResponse message.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.ImplementationFileListResponse> getImplementationFiles(
        gauge.messages.Messages.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetImplementationFilesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ImplementStub is a RPC to to ask runner to add a given implementation to given file.
     * Accepts a StubImplementationCodeRequest and returns a FileDiff message.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.FileDiff> implementStub(
        gauge.messages.Messages.StubImplementationCodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getImplementStubMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Refactor is a RPC to refactor a given step in implementation file.
     * Accepts a RefactorRequest message and returns a RefactorResponse message.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.RefactorResponse> refactor(
        gauge.messages.Messages.RefactorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRefactorMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Kill is a RPC tell plugin to stop grpc server and kill the plugin process.
     * Accepts a KillProcessRequest message and returns a Empty message.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> kill(
        gauge.messages.Messages.KillProcessRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getKillMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_VALIDATE_STEP = 0;
  private static final int METHODID_INITIALIZE_SUITE_DATA_STORE = 1;
  private static final int METHODID_START_EXECUTION = 2;
  private static final int METHODID_INITIALIZE_SPEC_DATA_STORE = 3;
  private static final int METHODID_START_SPEC_EXECUTION = 4;
  private static final int METHODID_INITIALIZE_SCENARIO_DATA_STORE = 5;
  private static final int METHODID_START_SCENARIO_EXECUTION = 6;
  private static final int METHODID_START_STEP_EXECUTION = 7;
  private static final int METHODID_EXECUTE_STEP = 8;
  private static final int METHODID_FINISH_STEP_EXECUTION = 9;
  private static final int METHODID_FINISH_SCENARIO_EXECUTION = 10;
  private static final int METHODID_FINISH_SPEC_EXECUTION = 11;
  private static final int METHODID_FINISH_EXECUTION = 12;
  private static final int METHODID_CACHE_FILE = 13;
  private static final int METHODID_GET_STEP_NAME = 14;
  private static final int METHODID_GET_GLOB_PATTERNS = 15;
  private static final int METHODID_GET_STEP_NAMES = 16;
  private static final int METHODID_GET_STEP_POSITIONS = 17;
  private static final int METHODID_GET_IMPLEMENTATION_FILES = 18;
  private static final int METHODID_IMPLEMENT_STUB = 19;
  private static final int METHODID_REFACTOR = 20;
  private static final int METHODID_KILL = 21;

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
        case METHODID_VALIDATE_STEP:
          serviceImpl.validateStep((gauge.messages.Messages.StepValidateRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.StepValidateResponse>) responseObserver);
          break;
        case METHODID_INITIALIZE_SUITE_DATA_STORE:
          serviceImpl.initializeSuiteDataStore((gauge.messages.Messages.SuiteDataStoreInitRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_START_EXECUTION:
          serviceImpl.startExecution((gauge.messages.Messages.ExecutionStartingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_INITIALIZE_SPEC_DATA_STORE:
          serviceImpl.initializeSpecDataStore((gauge.messages.Messages.SpecDataStoreInitRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_START_SPEC_EXECUTION:
          serviceImpl.startSpecExecution((gauge.messages.Messages.SpecExecutionStartingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_INITIALIZE_SCENARIO_DATA_STORE:
          serviceImpl.initializeScenarioDataStore((gauge.messages.Messages.ScenarioDataStoreInitRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_START_SCENARIO_EXECUTION:
          serviceImpl.startScenarioExecution((gauge.messages.Messages.ScenarioExecutionStartingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_START_STEP_EXECUTION:
          serviceImpl.startStepExecution((gauge.messages.Messages.StepExecutionStartingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_EXECUTE_STEP:
          serviceImpl.executeStep((gauge.messages.Messages.ExecuteStepRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_FINISH_STEP_EXECUTION:
          serviceImpl.finishStepExecution((gauge.messages.Messages.StepExecutionEndingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_FINISH_SCENARIO_EXECUTION:
          serviceImpl.finishScenarioExecution((gauge.messages.Messages.ScenarioExecutionEndingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_FINISH_SPEC_EXECUTION:
          serviceImpl.finishSpecExecution((gauge.messages.Messages.SpecExecutionEndingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_FINISH_EXECUTION:
          serviceImpl.finishExecution((gauge.messages.Messages.ExecutionEndingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse>) responseObserver);
          break;
        case METHODID_CACHE_FILE:
          serviceImpl.cacheFile((gauge.messages.Messages.CacheFileRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
          break;
        case METHODID_GET_STEP_NAME:
          serviceImpl.getStepName((gauge.messages.Messages.StepNameRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNameResponse>) responseObserver);
          break;
        case METHODID_GET_GLOB_PATTERNS:
          serviceImpl.getGlobPatterns((gauge.messages.Messages.Empty) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileGlobPatternResponse>) responseObserver);
          break;
        case METHODID_GET_STEP_NAMES:
          serviceImpl.getStepNames((gauge.messages.Messages.StepNamesRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNamesResponse>) responseObserver);
          break;
        case METHODID_GET_STEP_POSITIONS:
          serviceImpl.getStepPositions((gauge.messages.Messages.StepPositionsRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.StepPositionsResponse>) responseObserver);
          break;
        case METHODID_GET_IMPLEMENTATION_FILES:
          serviceImpl.getImplementationFiles((gauge.messages.Messages.Empty) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileListResponse>) responseObserver);
          break;
        case METHODID_IMPLEMENT_STUB:
          serviceImpl.implementStub((gauge.messages.Messages.StubImplementationCodeRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.FileDiff>) responseObserver);
          break;
        case METHODID_REFACTOR:
          serviceImpl.refactor((gauge.messages.Messages.RefactorRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.RefactorResponse>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getValidateStepMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.StepValidateRequest,
              gauge.messages.Messages.StepValidateResponse>(
                service, METHODID_VALIDATE_STEP)))
        .addMethod(
          getInitializeSuiteDataStoreMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.SuiteDataStoreInitRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_INITIALIZE_SUITE_DATA_STORE)))
        .addMethod(
          getStartExecutionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.ExecutionStartingRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_START_EXECUTION)))
        .addMethod(
          getInitializeSpecDataStoreMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.SpecDataStoreInitRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_INITIALIZE_SPEC_DATA_STORE)))
        .addMethod(
          getStartSpecExecutionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.SpecExecutionStartingRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_START_SPEC_EXECUTION)))
        .addMethod(
          getInitializeScenarioDataStoreMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.ScenarioDataStoreInitRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_INITIALIZE_SCENARIO_DATA_STORE)))
        .addMethod(
          getStartScenarioExecutionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.ScenarioExecutionStartingRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_START_SCENARIO_EXECUTION)))
        .addMethod(
          getStartStepExecutionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.StepExecutionStartingRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_START_STEP_EXECUTION)))
        .addMethod(
          getExecuteStepMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.ExecuteStepRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_EXECUTE_STEP)))
        .addMethod(
          getFinishStepExecutionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.StepExecutionEndingRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_FINISH_STEP_EXECUTION)))
        .addMethod(
          getFinishScenarioExecutionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.ScenarioExecutionEndingRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_FINISH_SCENARIO_EXECUTION)))
        .addMethod(
          getFinishSpecExecutionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.SpecExecutionEndingRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_FINISH_SPEC_EXECUTION)))
        .addMethod(
          getFinishExecutionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.ExecutionEndingRequest,
              gauge.messages.Messages.ExecutionStatusResponse>(
                service, METHODID_FINISH_EXECUTION)))
        .addMethod(
          getCacheFileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.CacheFileRequest,
              gauge.messages.Messages.Empty>(
                service, METHODID_CACHE_FILE)))
        .addMethod(
          getGetStepNameMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.StepNameRequest,
              gauge.messages.Messages.StepNameResponse>(
                service, METHODID_GET_STEP_NAME)))
        .addMethod(
          getGetGlobPatternsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.Empty,
              gauge.messages.Messages.ImplementationFileGlobPatternResponse>(
                service, METHODID_GET_GLOB_PATTERNS)))
        .addMethod(
          getGetStepNamesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.StepNamesRequest,
              gauge.messages.Messages.StepNamesResponse>(
                service, METHODID_GET_STEP_NAMES)))
        .addMethod(
          getGetStepPositionsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.StepPositionsRequest,
              gauge.messages.Messages.StepPositionsResponse>(
                service, METHODID_GET_STEP_POSITIONS)))
        .addMethod(
          getGetImplementationFilesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.Empty,
              gauge.messages.Messages.ImplementationFileListResponse>(
                service, METHODID_GET_IMPLEMENTATION_FILES)))
        .addMethod(
          getImplementStubMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.StubImplementationCodeRequest,
              gauge.messages.Messages.FileDiff>(
                service, METHODID_IMPLEMENT_STUB)))
        .addMethod(
          getRefactorMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.RefactorRequest,
              gauge.messages.Messages.RefactorResponse>(
                service, METHODID_REFACTOR)))
        .addMethod(
          getKillMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              gauge.messages.Messages.KillProcessRequest,
              gauge.messages.Messages.Empty>(
                service, METHODID_KILL)))
        .build();
  }

  private static abstract class RunnerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RunnerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gauge.messages.Services.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Runner");
    }
  }

  private static final class RunnerFileDescriptorSupplier
      extends RunnerBaseDescriptorSupplier {
    RunnerFileDescriptorSupplier() {}
  }

  private static final class RunnerMethodDescriptorSupplier
      extends RunnerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    RunnerMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (RunnerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RunnerFileDescriptorSupplier())
              .addMethod(getValidateStepMethod())
              .addMethod(getInitializeSuiteDataStoreMethod())
              .addMethod(getStartExecutionMethod())
              .addMethod(getInitializeSpecDataStoreMethod())
              .addMethod(getStartSpecExecutionMethod())
              .addMethod(getInitializeScenarioDataStoreMethod())
              .addMethod(getStartScenarioExecutionMethod())
              .addMethod(getStartStepExecutionMethod())
              .addMethod(getExecuteStepMethod())
              .addMethod(getFinishStepExecutionMethod())
              .addMethod(getFinishScenarioExecutionMethod())
              .addMethod(getFinishSpecExecutionMethod())
              .addMethod(getFinishExecutionMethod())
              .addMethod(getCacheFileMethod())
              .addMethod(getGetStepNameMethod())
              .addMethod(getGetGlobPatternsMethod())
              .addMethod(getGetStepNamesMethod())
              .addMethod(getGetStepPositionsMethod())
              .addMethod(getGetImplementationFilesMethod())
              .addMethod(getImplementStubMethod())
              .addMethod(getRefactorMethod())
              .addMethod(getKillMethod())
              .build();
        }
      }
    }
    return result;
  }
}
