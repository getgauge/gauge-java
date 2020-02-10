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
    comments = "Source: services.proto")
public final class RunnerGrpc {

  private RunnerGrpc() {}

  public static final String SERVICE_NAME = "gauge.messages.Runner";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepValidateRequest,
      gauge.messages.Messages.StepValidateResponse> getValidateStepMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepValidateRequest,
      gauge.messages.Messages.StepValidateResponse> getValidateStepMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepValidateRequest, gauge.messages.Messages.StepValidateResponse> getValidateStepMethod;
    if ((getValidateStepMethod = RunnerGrpc.getValidateStepMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getValidateStepMethod = RunnerGrpc.getValidateStepMethod) == null) {
          RunnerGrpc.getValidateStepMethod = getValidateStepMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepValidateRequest, gauge.messages.Messages.StepValidateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "ValidateStep"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SuiteDataStoreInitRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getInitializeSuiteDataStoreMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SuiteDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse> getInitializeSuiteDataStoreMethod;
    if ((getInitializeSuiteDataStoreMethod = RunnerGrpc.getInitializeSuiteDataStoreMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getInitializeSuiteDataStoreMethod = RunnerGrpc.getInitializeSuiteDataStoreMethod) == null) {
          RunnerGrpc.getInitializeSuiteDataStoreMethod = getInitializeSuiteDataStoreMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SuiteDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "InitializeSuiteDataStore"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse> getStartExecutionMethod;
    if ((getStartExecutionMethod = RunnerGrpc.getStartExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getStartExecutionMethod = RunnerGrpc.getStartExecutionMethod) == null) {
          RunnerGrpc.getStartExecutionMethod = getStartExecutionMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "StartExecution"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SpecDataStoreInitRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getInitializeSpecDataStoreMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SpecDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse> getInitializeSpecDataStoreMethod;
    if ((getInitializeSpecDataStoreMethod = RunnerGrpc.getInitializeSpecDataStoreMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getInitializeSpecDataStoreMethod = RunnerGrpc.getInitializeSpecDataStoreMethod) == null) {
          RunnerGrpc.getInitializeSpecDataStoreMethod = getInitializeSpecDataStoreMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SpecDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "InitializeSpecDataStore"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartSpecExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse> getStartSpecExecutionMethod;
    if ((getStartSpecExecutionMethod = RunnerGrpc.getStartSpecExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getStartSpecExecutionMethod = RunnerGrpc.getStartSpecExecutionMethod) == null) {
          RunnerGrpc.getStartSpecExecutionMethod = getStartSpecExecutionMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SpecExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "StartSpecExecution"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioDataStoreInitRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getInitializeScenarioDataStoreMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse> getInitializeScenarioDataStoreMethod;
    if ((getInitializeScenarioDataStoreMethod = RunnerGrpc.getInitializeScenarioDataStoreMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getInitializeScenarioDataStoreMethod = RunnerGrpc.getInitializeScenarioDataStoreMethod) == null) {
          RunnerGrpc.getInitializeScenarioDataStoreMethod = getInitializeScenarioDataStoreMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ScenarioDataStoreInitRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "InitializeScenarioDataStore"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartScenarioExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse> getStartScenarioExecutionMethod;
    if ((getStartScenarioExecutionMethod = RunnerGrpc.getStartScenarioExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getStartScenarioExecutionMethod = RunnerGrpc.getStartScenarioExecutionMethod) == null) {
          RunnerGrpc.getStartScenarioExecutionMethod = getStartScenarioExecutionMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ScenarioExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "StartScenarioExecution"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionStartingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getStartStepExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse> getStartStepExecutionMethod;
    if ((getStartStepExecutionMethod = RunnerGrpc.getStartStepExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getStartStepExecutionMethod = RunnerGrpc.getStartStepExecutionMethod) == null) {
          RunnerGrpc.getStartStepExecutionMethod = getStartStepExecutionMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepExecutionStartingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "StartStepExecution"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ExecuteStepRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getExecuteStepMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ExecuteStepRequest, gauge.messages.Messages.ExecutionStatusResponse> getExecuteStepMethod;
    if ((getExecuteStepMethod = RunnerGrpc.getExecuteStepMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getExecuteStepMethod = RunnerGrpc.getExecuteStepMethod) == null) {
          RunnerGrpc.getExecuteStepMethod = getExecuteStepMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ExecuteStepRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "ExecuteStep"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishStepExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse> getFinishStepExecutionMethod;
    if ((getFinishStepExecutionMethod = RunnerGrpc.getFinishStepExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getFinishStepExecutionMethod = RunnerGrpc.getFinishStepExecutionMethod) == null) {
          RunnerGrpc.getFinishStepExecutionMethod = getFinishStepExecutionMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "FinishStepExecution"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishScenarioExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse> getFinishScenarioExecutionMethod;
    if ((getFinishScenarioExecutionMethod = RunnerGrpc.getFinishScenarioExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getFinishScenarioExecutionMethod = RunnerGrpc.getFinishScenarioExecutionMethod) == null) {
          RunnerGrpc.getFinishScenarioExecutionMethod = getFinishScenarioExecutionMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ScenarioExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "FinishScenarioExecution"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishSpecExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse> getFinishSpecExecutionMethod;
    if ((getFinishSpecExecutionMethod = RunnerGrpc.getFinishSpecExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getFinishSpecExecutionMethod = RunnerGrpc.getFinishSpecExecutionMethod) == null) {
          RunnerGrpc.getFinishSpecExecutionMethod = getFinishSpecExecutionMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SpecExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "FinishSpecExecution"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionEndingRequest,
      gauge.messages.Messages.ExecutionStatusResponse> getFinishExecutionMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse> getFinishExecutionMethod;
    if ((getFinishExecutionMethod = RunnerGrpc.getFinishExecutionMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getFinishExecutionMethod = RunnerGrpc.getFinishExecutionMethod) == null) {
          RunnerGrpc.getFinishExecutionMethod = getFinishExecutionMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ExecutionEndingRequest, gauge.messages.Messages.ExecutionStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "FinishExecution"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.CacheFileRequest,
      gauge.messages.Messages.Empty> getCacheFileMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.CacheFileRequest, gauge.messages.Messages.Empty> getCacheFileMethod;
    if ((getCacheFileMethod = RunnerGrpc.getCacheFileMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getCacheFileMethod = RunnerGrpc.getCacheFileMethod) == null) {
          RunnerGrpc.getCacheFileMethod = getCacheFileMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.CacheFileRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "CacheFile"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepNameRequest,
      gauge.messages.Messages.StepNameResponse> getGetStepNameMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepNameRequest, gauge.messages.Messages.StepNameResponse> getGetStepNameMethod;
    if ((getGetStepNameMethod = RunnerGrpc.getGetStepNameMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getGetStepNameMethod = RunnerGrpc.getGetStepNameMethod) == null) {
          RunnerGrpc.getGetStepNameMethod = getGetStepNameMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepNameRequest, gauge.messages.Messages.StepNameResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "GetStepName"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.Empty,
      gauge.messages.Messages.ImplementationFileGlobPatternResponse> getGetGlobPatternsMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.Empty, gauge.messages.Messages.ImplementationFileGlobPatternResponse> getGetGlobPatternsMethod;
    if ((getGetGlobPatternsMethod = RunnerGrpc.getGetGlobPatternsMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getGetGlobPatternsMethod = RunnerGrpc.getGetGlobPatternsMethod) == null) {
          RunnerGrpc.getGetGlobPatternsMethod = getGetGlobPatternsMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.Empty, gauge.messages.Messages.ImplementationFileGlobPatternResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "GetGlobPatterns"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepNamesRequest,
      gauge.messages.Messages.StepNamesResponse> getGetStepNamesMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepNamesRequest, gauge.messages.Messages.StepNamesResponse> getGetStepNamesMethod;
    if ((getGetStepNamesMethod = RunnerGrpc.getGetStepNamesMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getGetStepNamesMethod = RunnerGrpc.getGetStepNamesMethod) == null) {
          RunnerGrpc.getGetStepNamesMethod = getGetStepNamesMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepNamesRequest, gauge.messages.Messages.StepNamesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "GetStepNames"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepPositionsRequest,
      gauge.messages.Messages.StepPositionsResponse> getGetStepPositionsMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepPositionsRequest, gauge.messages.Messages.StepPositionsResponse> getGetStepPositionsMethod;
    if ((getGetStepPositionsMethod = RunnerGrpc.getGetStepPositionsMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getGetStepPositionsMethod = RunnerGrpc.getGetStepPositionsMethod) == null) {
          RunnerGrpc.getGetStepPositionsMethod = getGetStepPositionsMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepPositionsRequest, gauge.messages.Messages.StepPositionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "GetStepPositions"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.Empty,
      gauge.messages.Messages.ImplementationFileListResponse> getGetImplementationFilesMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.Empty, gauge.messages.Messages.ImplementationFileListResponse> getGetImplementationFilesMethod;
    if ((getGetImplementationFilesMethod = RunnerGrpc.getGetImplementationFilesMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getGetImplementationFilesMethod = RunnerGrpc.getGetImplementationFilesMethod) == null) {
          RunnerGrpc.getGetImplementationFilesMethod = getGetImplementationFilesMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.Empty, gauge.messages.Messages.ImplementationFileListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "GetImplementationFiles"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StubImplementationCodeRequest,
      gauge.messages.Messages.FileDiff> getImplementStubMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StubImplementationCodeRequest, gauge.messages.Messages.FileDiff> getImplementStubMethod;
    if ((getImplementStubMethod = RunnerGrpc.getImplementStubMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getImplementStubMethod = RunnerGrpc.getImplementStubMethod) == null) {
          RunnerGrpc.getImplementStubMethod = getImplementStubMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StubImplementationCodeRequest, gauge.messages.Messages.FileDiff>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "ImplementStub"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.RefactorRequest,
      gauge.messages.Messages.RefactorResponse> getRefactorMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.RefactorRequest, gauge.messages.Messages.RefactorResponse> getRefactorMethod;
    if ((getRefactorMethod = RunnerGrpc.getRefactorMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getRefactorMethod = RunnerGrpc.getRefactorMethod) == null) {
          RunnerGrpc.getRefactorMethod = getRefactorMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.RefactorRequest, gauge.messages.Messages.RefactorResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "Refactor"))
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

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest,
      gauge.messages.Messages.Empty> getKillMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest, gauge.messages.Messages.Empty> getKillMethod;
    if ((getKillMethod = RunnerGrpc.getKillMethod) == null) {
      synchronized (RunnerGrpc.class) {
        if ((getKillMethod = RunnerGrpc.getKillMethod) == null) {
          RunnerGrpc.getKillMethod = getKillMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.KillProcessRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Runner", "Kill"))
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
    return new RunnerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RunnerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RunnerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RunnerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RunnerFutureStub(channel);
  }

  /**
   */
  public static abstract class RunnerImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * ValidateStep is a RPC to validate a given step.
     * Accepts a StepValidateRequest message and returns a StepValidateResponse message
     * </pre>
     */
    public void validateStep(gauge.messages.Messages.StepValidateRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepValidateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getValidateStepMethod(), responseObserver);
    }

    /**
     * <pre>
     * SuiteDataStoreInit is a RPC to initialize the suite level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void initializeSuiteDataStore(gauge.messages.Messages.SuiteDataStoreInitRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getInitializeSuiteDataStoreMethod(), responseObserver);
    }

    /**
     * <pre>
     * ExecutionStarting is a RPC to tell runner to execute Suite level hooks.
     * Accepts a ExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void startExecution(gauge.messages.Messages.ExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStartExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * SpecDataStoreInit is a RPC to initialize the spec level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void initializeSpecDataStore(gauge.messages.Messages.SpecDataStoreInitRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getInitializeSpecDataStoreMethod(), responseObserver);
    }

    /**
     * <pre>
     * SpecExecutionStarting is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void startSpecExecution(gauge.messages.Messages.SpecExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStartSpecExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * ScenarioDataStoreInit is a RPC to initialize the scenario level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void initializeScenarioDataStore(gauge.messages.Messages.ScenarioDataStoreInitRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getInitializeScenarioDataStoreMethod(), responseObserver);
    }

    /**
     * <pre>
     * ScenarioExecutionStarting is a RPC to tell runner to execute scenario level hooks.
     * Accepts a ScenarioExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void startScenarioExecution(gauge.messages.Messages.ScenarioExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStartScenarioExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * StepExecutionStarting is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void startStepExecution(gauge.messages.Messages.StepExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStartStepExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * ExecuteStep is a RPC to tell runner to execute a step .
     * Accepts a ExecuteStepRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void executeStep(gauge.messages.Messages.ExecuteStepRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getExecuteStepMethod(), responseObserver);
    }

    /**
     * <pre>
     * StepExecutionEnding is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void finishStepExecution(gauge.messages.Messages.StepExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getFinishStepExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * ScenarioExecutionEnding is a RPC to tell runner to execute Scenario level hooks.
     * Accepts a ScenarioExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void finishScenarioExecution(gauge.messages.Messages.ScenarioExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getFinishScenarioExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * SpecExecutionEnding is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void finishSpecExecution(gauge.messages.Messages.SpecExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getFinishSpecExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * ExecutionEnding is a RPC to tell runner to execute suite level hooks.
     * Accepts a ExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public void finishExecution(gauge.messages.Messages.ExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ExecutionStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getFinishExecutionMethod(), responseObserver);
    }

    /**
     * <pre>
     * CacheFile is a RPC to tell runner to load/reload/unload a implementation file.
     * Accepts a CacheFileRequest message and returns a Empty message
     * </pre>
     */
    public void cacheFile(gauge.messages.Messages.CacheFileRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getCacheFileMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetStepName is a RPC to get information about the given step.
     * Accepts a StepNameRequest message and returns a StepNameResponse message.
     * </pre>
     */
    public void getStepName(gauge.messages.Messages.StepNameRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNameResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStepNameMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetGlobPatterns is a RPC to get the file path pattern which needs to be cached.
     * Accepts a Empty message and returns a ImplementationFileGlobPatternResponse message.
     * </pre>
     */
    public void getGlobPatterns(gauge.messages.Messages.Empty request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileGlobPatternResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetGlobPatternsMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetStepNames is a RPC to get all the available steps from the runner.
     * Accepts a StepNamesRequest message and returns a StepNamesResponse
     * </pre>
     */
    public void getStepNames(gauge.messages.Messages.StepNamesRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepNamesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStepNamesMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetStepPositions is a RPC to get positions of all available steps in a given file.
     * Accepts a StepPositionsRequest message and returns a StepPositionsResponse message
     * </pre>
     */
    public void getStepPositions(gauge.messages.Messages.StepPositionsRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.StepPositionsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStepPositionsMethod(), responseObserver);
    }

    /**
     * <pre>
     * GetImplementationFiles is a RPC get all the existing implementation files.
     * Accepts a Empty and returns a ImplementationFileListResponse message.
     * </pre>
     */
    public void getImplementationFiles(gauge.messages.Messages.Empty request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.ImplementationFileListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetImplementationFilesMethod(), responseObserver);
    }

    /**
     * <pre>
     * ImplementStub is a RPC to to ask runner to add a given implementation to given file.
     * Accepts a StubImplementationCodeRequest and returns a FileDiff message.
     * </pre>
     */
    public void implementStub(gauge.messages.Messages.StubImplementationCodeRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.FileDiff> responseObserver) {
      asyncUnimplementedUnaryCall(getImplementStubMethod(), responseObserver);
    }

    /**
     * <pre>
     * Refactor is a RPC to refactor a given step in implementation file.
     * Accepts a RefactorRequest message and returns a RefactorResponse message.
     * </pre>
     */
    public void refactor(gauge.messages.Messages.RefactorRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.RefactorResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRefactorMethod(), responseObserver);
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
            getValidateStepMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StepValidateRequest,
                gauge.messages.Messages.StepValidateResponse>(
                  this, METHODID_VALIDATE_STEP)))
          .addMethod(
            getInitializeSuiteDataStoreMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.SuiteDataStoreInitRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_INITIALIZE_SUITE_DATA_STORE)))
          .addMethod(
            getStartExecutionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.ExecutionStartingRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_START_EXECUTION)))
          .addMethod(
            getInitializeSpecDataStoreMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.SpecDataStoreInitRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_INITIALIZE_SPEC_DATA_STORE)))
          .addMethod(
            getStartSpecExecutionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.SpecExecutionStartingRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_START_SPEC_EXECUTION)))
          .addMethod(
            getInitializeScenarioDataStoreMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.ScenarioDataStoreInitRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_INITIALIZE_SCENARIO_DATA_STORE)))
          .addMethod(
            getStartScenarioExecutionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.ScenarioExecutionStartingRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_START_SCENARIO_EXECUTION)))
          .addMethod(
            getStartStepExecutionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StepExecutionStartingRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_START_STEP_EXECUTION)))
          .addMethod(
            getExecuteStepMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.ExecuteStepRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_EXECUTE_STEP)))
          .addMethod(
            getFinishStepExecutionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StepExecutionEndingRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_FINISH_STEP_EXECUTION)))
          .addMethod(
            getFinishScenarioExecutionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.ScenarioExecutionEndingRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_FINISH_SCENARIO_EXECUTION)))
          .addMethod(
            getFinishSpecExecutionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.SpecExecutionEndingRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_FINISH_SPEC_EXECUTION)))
          .addMethod(
            getFinishExecutionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.ExecutionEndingRequest,
                gauge.messages.Messages.ExecutionStatusResponse>(
                  this, METHODID_FINISH_EXECUTION)))
          .addMethod(
            getCacheFileMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.CacheFileRequest,
                gauge.messages.Messages.Empty>(
                  this, METHODID_CACHE_FILE)))
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
                gauge.messages.Messages.Empty,
                gauge.messages.Messages.ImplementationFileGlobPatternResponse>(
                  this, METHODID_GET_GLOB_PATTERNS)))
          .addMethod(
            getGetStepNamesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StepNamesRequest,
                gauge.messages.Messages.StepNamesResponse>(
                  this, METHODID_GET_STEP_NAMES)))
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
                gauge.messages.Messages.Empty,
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
            getRefactorMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.RefactorRequest,
                gauge.messages.Messages.RefactorResponse>(
                  this, METHODID_REFACTOR)))
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
   */
  public static final class RunnerStub extends io.grpc.stub.AbstractStub<RunnerStub> {
    private RunnerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RunnerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RunnerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
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
      asyncUnaryCall(
          getChannel().newCall(getKillMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RunnerBlockingStub extends io.grpc.stub.AbstractStub<RunnerBlockingStub> {
    private RunnerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RunnerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RunnerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RunnerBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * ValidateStep is a RPC to validate a given step.
     * Accepts a StepValidateRequest message and returns a StepValidateResponse message
     * </pre>
     */
    public gauge.messages.Messages.StepValidateResponse validateStep(gauge.messages.Messages.StepValidateRequest request) {
      return blockingUnaryCall(
          getChannel(), getValidateStepMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * SuiteDataStoreInit is a RPC to initialize the suite level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse initializeSuiteDataStore(gauge.messages.Messages.SuiteDataStoreInitRequest request) {
      return blockingUnaryCall(
          getChannel(), getInitializeSuiteDataStoreMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ExecutionStarting is a RPC to tell runner to execute Suite level hooks.
     * Accepts a ExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse startExecution(gauge.messages.Messages.ExecutionStartingRequest request) {
      return blockingUnaryCall(
          getChannel(), getStartExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * SpecDataStoreInit is a RPC to initialize the spec level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse initializeSpecDataStore(gauge.messages.Messages.SpecDataStoreInitRequest request) {
      return blockingUnaryCall(
          getChannel(), getInitializeSpecDataStoreMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * SpecExecutionStarting is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse startSpecExecution(gauge.messages.Messages.SpecExecutionStartingRequest request) {
      return blockingUnaryCall(
          getChannel(), getStartSpecExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ScenarioDataStoreInit is a RPC to initialize the scenario level data store.
     * Accepts a Empty message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse initializeScenarioDataStore(gauge.messages.Messages.ScenarioDataStoreInitRequest request) {
      return blockingUnaryCall(
          getChannel(), getInitializeScenarioDataStoreMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ScenarioExecutionStarting is a RPC to tell runner to execute scenario level hooks.
     * Accepts a ScenarioExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse startScenarioExecution(gauge.messages.Messages.ScenarioExecutionStartingRequest request) {
      return blockingUnaryCall(
          getChannel(), getStartScenarioExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * StepExecutionStarting is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionStartingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse startStepExecution(gauge.messages.Messages.StepExecutionStartingRequest request) {
      return blockingUnaryCall(
          getChannel(), getStartStepExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ExecuteStep is a RPC to tell runner to execute a step .
     * Accepts a ExecuteStepRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse executeStep(gauge.messages.Messages.ExecuteStepRequest request) {
      return blockingUnaryCall(
          getChannel(), getExecuteStepMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * StepExecutionEnding is a RPC to tell runner to execute step level hooks.
     * Accepts a StepExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse finishStepExecution(gauge.messages.Messages.StepExecutionEndingRequest request) {
      return blockingUnaryCall(
          getChannel(), getFinishStepExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ScenarioExecutionEnding is a RPC to tell runner to execute Scenario level hooks.
     * Accepts a ScenarioExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse finishScenarioExecution(gauge.messages.Messages.ScenarioExecutionEndingRequest request) {
      return blockingUnaryCall(
          getChannel(), getFinishScenarioExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * SpecExecutionEnding is a RPC to tell runner to execute spec level hooks.
     * Accepts a SpecExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse finishSpecExecution(gauge.messages.Messages.SpecExecutionEndingRequest request) {
      return blockingUnaryCall(
          getChannel(), getFinishSpecExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ExecutionEnding is a RPC to tell runner to execute suite level hooks.
     * Accepts a ExecutionEndingRequest message and returns a ExecutionStatusResponse message
     * </pre>
     */
    public gauge.messages.Messages.ExecutionStatusResponse finishExecution(gauge.messages.Messages.ExecutionEndingRequest request) {
      return blockingUnaryCall(
          getChannel(), getFinishExecutionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * CacheFile is a RPC to tell runner to load/reload/unload a implementation file.
     * Accepts a CacheFileRequest message and returns a Empty message
     * </pre>
     */
    public gauge.messages.Messages.Empty cacheFile(gauge.messages.Messages.CacheFileRequest request) {
      return blockingUnaryCall(
          getChannel(), getCacheFileMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetStepName is a RPC to get information about the given step.
     * Accepts a StepNameRequest message and returns a StepNameResponse message.
     * </pre>
     */
    public gauge.messages.Messages.StepNameResponse getStepName(gauge.messages.Messages.StepNameRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetStepNameMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetGlobPatterns is a RPC to get the file path pattern which needs to be cached.
     * Accepts a Empty message and returns a ImplementationFileGlobPatternResponse message.
     * </pre>
     */
    public gauge.messages.Messages.ImplementationFileGlobPatternResponse getGlobPatterns(gauge.messages.Messages.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetGlobPatternsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetStepNames is a RPC to get all the available steps from the runner.
     * Accepts a StepNamesRequest message and returns a StepNamesResponse
     * </pre>
     */
    public gauge.messages.Messages.StepNamesResponse getStepNames(gauge.messages.Messages.StepNamesRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetStepNamesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetStepPositions is a RPC to get positions of all available steps in a given file.
     * Accepts a StepPositionsRequest message and returns a StepPositionsResponse message
     * </pre>
     */
    public gauge.messages.Messages.StepPositionsResponse getStepPositions(gauge.messages.Messages.StepPositionsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetStepPositionsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * GetImplementationFiles is a RPC get all the existing implementation files.
     * Accepts a Empty and returns a ImplementationFileListResponse message.
     * </pre>
     */
    public gauge.messages.Messages.ImplementationFileListResponse getImplementationFiles(gauge.messages.Messages.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetImplementationFilesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ImplementStub is a RPC to to ask runner to add a given implementation to given file.
     * Accepts a StubImplementationCodeRequest and returns a FileDiff message.
     * </pre>
     */
    public gauge.messages.Messages.FileDiff implementStub(gauge.messages.Messages.StubImplementationCodeRequest request) {
      return blockingUnaryCall(
          getChannel(), getImplementStubMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Refactor is a RPC to refactor a given step in implementation file.
     * Accepts a RefactorRequest message and returns a RefactorResponse message.
     * </pre>
     */
    public gauge.messages.Messages.RefactorResponse refactor(gauge.messages.Messages.RefactorRequest request) {
      return blockingUnaryCall(
          getChannel(), getRefactorMethod(), getCallOptions(), request);
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
   */
  public static final class RunnerFutureStub extends io.grpc.stub.AbstractStub<RunnerFutureStub> {
    private RunnerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RunnerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RunnerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
      return futureUnaryCall(
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
    private final RunnerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RunnerImplBase serviceImpl, int methodId) {
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
    private final String methodName;

    RunnerMethodDescriptorSupplier(String methodName) {
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
