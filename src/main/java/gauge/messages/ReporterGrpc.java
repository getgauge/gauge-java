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
 * Reporter services is meant for reporting plugins, or others plugins which are interested the live events
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.13.1)",
    comments = "Source: services.proto")
public final class ReporterGrpc {

  private ReporterGrpc() {}

  public static final String SERVICE_NAME = "gauge.messages.Reporter";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionStartingRequest,
      gauge.messages.Messages.Empty> getNotifyExecutionStartingMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionStartingRequest,
      gauge.messages.Messages.Empty> getNotifyExecutionStartingMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionStartingRequest, gauge.messages.Messages.Empty> getNotifyExecutionStartingMethod;
    if ((getNotifyExecutionStartingMethod = ReporterGrpc.getNotifyExecutionStartingMethod) == null) {
      synchronized (ReporterGrpc.class) {
        if ((getNotifyExecutionStartingMethod = ReporterGrpc.getNotifyExecutionStartingMethod) == null) {
          ReporterGrpc.getNotifyExecutionStartingMethod = getNotifyExecutionStartingMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ExecutionStartingRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Reporter", "NotifyExecutionStarting"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionStartingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ReporterMethodDescriptorSupplier("NotifyExecutionStarting"))
                  .build();
          }
        }
     }
     return getNotifyExecutionStartingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionStartingRequest,
      gauge.messages.Messages.Empty> getNotifySpecExecutionStartingMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionStartingRequest,
      gauge.messages.Messages.Empty> getNotifySpecExecutionStartingMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionStartingRequest, gauge.messages.Messages.Empty> getNotifySpecExecutionStartingMethod;
    if ((getNotifySpecExecutionStartingMethod = ReporterGrpc.getNotifySpecExecutionStartingMethod) == null) {
      synchronized (ReporterGrpc.class) {
        if ((getNotifySpecExecutionStartingMethod = ReporterGrpc.getNotifySpecExecutionStartingMethod) == null) {
          ReporterGrpc.getNotifySpecExecutionStartingMethod = getNotifySpecExecutionStartingMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SpecExecutionStartingRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Reporter", "NotifySpecExecutionStarting"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.SpecExecutionStartingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ReporterMethodDescriptorSupplier("NotifySpecExecutionStarting"))
                  .build();
          }
        }
     }
     return getNotifySpecExecutionStartingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionStartingRequest,
      gauge.messages.Messages.Empty> getNotifyScenarioExecutionStartingMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionStartingRequest,
      gauge.messages.Messages.Empty> getNotifyScenarioExecutionStartingMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionStartingRequest, gauge.messages.Messages.Empty> getNotifyScenarioExecutionStartingMethod;
    if ((getNotifyScenarioExecutionStartingMethod = ReporterGrpc.getNotifyScenarioExecutionStartingMethod) == null) {
      synchronized (ReporterGrpc.class) {
        if ((getNotifyScenarioExecutionStartingMethod = ReporterGrpc.getNotifyScenarioExecutionStartingMethod) == null) {
          ReporterGrpc.getNotifyScenarioExecutionStartingMethod = getNotifyScenarioExecutionStartingMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ScenarioExecutionStartingRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Reporter", "NotifyScenarioExecutionStarting"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ScenarioExecutionStartingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ReporterMethodDescriptorSupplier("NotifyScenarioExecutionStarting"))
                  .build();
          }
        }
     }
     return getNotifyScenarioExecutionStartingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionStartingRequest,
      gauge.messages.Messages.Empty> getNotifyStepExecutionStartingMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionStartingRequest,
      gauge.messages.Messages.Empty> getNotifyStepExecutionStartingMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionStartingRequest, gauge.messages.Messages.Empty> getNotifyStepExecutionStartingMethod;
    if ((getNotifyStepExecutionStartingMethod = ReporterGrpc.getNotifyStepExecutionStartingMethod) == null) {
      synchronized (ReporterGrpc.class) {
        if ((getNotifyStepExecutionStartingMethod = ReporterGrpc.getNotifyStepExecutionStartingMethod) == null) {
          ReporterGrpc.getNotifyStepExecutionStartingMethod = getNotifyStepExecutionStartingMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepExecutionStartingRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Reporter", "NotifyStepExecutionStarting"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepExecutionStartingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ReporterMethodDescriptorSupplier("NotifyStepExecutionStarting"))
                  .build();
          }
        }
     }
     return getNotifyStepExecutionStartingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionEndingRequest,
      gauge.messages.Messages.Empty> getNotifyStepExecutionEndingMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionEndingRequest,
      gauge.messages.Messages.Empty> getNotifyStepExecutionEndingMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.StepExecutionEndingRequest, gauge.messages.Messages.Empty> getNotifyStepExecutionEndingMethod;
    if ((getNotifyStepExecutionEndingMethod = ReporterGrpc.getNotifyStepExecutionEndingMethod) == null) {
      synchronized (ReporterGrpc.class) {
        if ((getNotifyStepExecutionEndingMethod = ReporterGrpc.getNotifyStepExecutionEndingMethod) == null) {
          ReporterGrpc.getNotifyStepExecutionEndingMethod = getNotifyStepExecutionEndingMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.StepExecutionEndingRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Reporter", "NotifyStepExecutionEnding"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.StepExecutionEndingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ReporterMethodDescriptorSupplier("NotifyStepExecutionEnding"))
                  .build();
          }
        }
     }
     return getNotifyStepExecutionEndingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionEndingRequest,
      gauge.messages.Messages.Empty> getNotifyScenarioExecutionEndingMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionEndingRequest,
      gauge.messages.Messages.Empty> getNotifyScenarioExecutionEndingMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ScenarioExecutionEndingRequest, gauge.messages.Messages.Empty> getNotifyScenarioExecutionEndingMethod;
    if ((getNotifyScenarioExecutionEndingMethod = ReporterGrpc.getNotifyScenarioExecutionEndingMethod) == null) {
      synchronized (ReporterGrpc.class) {
        if ((getNotifyScenarioExecutionEndingMethod = ReporterGrpc.getNotifyScenarioExecutionEndingMethod) == null) {
          ReporterGrpc.getNotifyScenarioExecutionEndingMethod = getNotifyScenarioExecutionEndingMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ScenarioExecutionEndingRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Reporter", "NotifyScenarioExecutionEnding"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ScenarioExecutionEndingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ReporterMethodDescriptorSupplier("NotifyScenarioExecutionEnding"))
                  .build();
          }
        }
     }
     return getNotifyScenarioExecutionEndingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionEndingRequest,
      gauge.messages.Messages.Empty> getNotifySpecExecutionEndingMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionEndingRequest,
      gauge.messages.Messages.Empty> getNotifySpecExecutionEndingMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SpecExecutionEndingRequest, gauge.messages.Messages.Empty> getNotifySpecExecutionEndingMethod;
    if ((getNotifySpecExecutionEndingMethod = ReporterGrpc.getNotifySpecExecutionEndingMethod) == null) {
      synchronized (ReporterGrpc.class) {
        if ((getNotifySpecExecutionEndingMethod = ReporterGrpc.getNotifySpecExecutionEndingMethod) == null) {
          ReporterGrpc.getNotifySpecExecutionEndingMethod = getNotifySpecExecutionEndingMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SpecExecutionEndingRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Reporter", "NotifySpecExecutionEnding"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.SpecExecutionEndingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ReporterMethodDescriptorSupplier("NotifySpecExecutionEnding"))
                  .build();
          }
        }
     }
     return getNotifySpecExecutionEndingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionEndingRequest,
      gauge.messages.Messages.Empty> getNotifyExecutionEndingMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionEndingRequest,
      gauge.messages.Messages.Empty> getNotifyExecutionEndingMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.ExecutionEndingRequest, gauge.messages.Messages.Empty> getNotifyExecutionEndingMethod;
    if ((getNotifyExecutionEndingMethod = ReporterGrpc.getNotifyExecutionEndingMethod) == null) {
      synchronized (ReporterGrpc.class) {
        if ((getNotifyExecutionEndingMethod = ReporterGrpc.getNotifyExecutionEndingMethod) == null) {
          ReporterGrpc.getNotifyExecutionEndingMethod = getNotifyExecutionEndingMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.ExecutionEndingRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Reporter", "NotifyExecutionEnding"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.ExecutionEndingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ReporterMethodDescriptorSupplier("NotifyExecutionEnding"))
                  .build();
          }
        }
     }
     return getNotifyExecutionEndingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.SuiteExecutionResult,
      gauge.messages.Messages.Empty> getNotifySuiteResultMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.SuiteExecutionResult,
      gauge.messages.Messages.Empty> getNotifySuiteResultMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.SuiteExecutionResult, gauge.messages.Messages.Empty> getNotifySuiteResultMethod;
    if ((getNotifySuiteResultMethod = ReporterGrpc.getNotifySuiteResultMethod) == null) {
      synchronized (ReporterGrpc.class) {
        if ((getNotifySuiteResultMethod = ReporterGrpc.getNotifySuiteResultMethod) == null) {
          ReporterGrpc.getNotifySuiteResultMethod = getNotifySuiteResultMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.SuiteExecutionResult, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Reporter", "NotifySuiteResult"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.SuiteExecutionResult.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ReporterMethodDescriptorSupplier("NotifySuiteResult"))
                  .build();
          }
        }
     }
     return getNotifySuiteResultMethod;
  }

  private static volatile io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest,
      gauge.messages.Messages.Empty> getKillMethod;

  public static io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest,
      gauge.messages.Messages.Empty> getKillMethod() {
    io.grpc.MethodDescriptor<gauge.messages.Messages.KillProcessRequest, gauge.messages.Messages.Empty> getKillMethod;
    if ((getKillMethod = ReporterGrpc.getKillMethod) == null) {
      synchronized (ReporterGrpc.class) {
        if ((getKillMethod = ReporterGrpc.getKillMethod) == null) {
          ReporterGrpc.getKillMethod = getKillMethod = 
              io.grpc.MethodDescriptor.<gauge.messages.Messages.KillProcessRequest, gauge.messages.Messages.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gauge.messages.Reporter", "Kill"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.KillProcessRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gauge.messages.Messages.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ReporterMethodDescriptorSupplier("Kill"))
                  .build();
          }
        }
     }
     return getKillMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ReporterStub newStub(io.grpc.Channel channel) {
    return new ReporterStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ReporterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ReporterBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ReporterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ReporterFutureStub(channel);
  }

  /**
   * <pre>
   * Reporter services is meant for reporting plugins, or others plugins which are interested the live events
   * </pre>
   */
  public static abstract class ReporterImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * NotifyExecutionStarting is a RPC to tell plugins that the execution has started.
     * Accepts a ExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyExecutionStarting(gauge.messages.Messages.ExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getNotifyExecutionStartingMethod(), responseObserver);
    }

    /**
     * <pre>
     * NotifySpecExecutionStarting is a RPC to tell plugins that the specification execution has started.
     * Accepts a SpecExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifySpecExecutionStarting(gauge.messages.Messages.SpecExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getNotifySpecExecutionStartingMethod(), responseObserver);
    }

    /**
     * <pre>
     * NotifyScenarioExecutionStarting is a RPC to tell plugins that the scenario execution has started.
     * Accepts a ScenarioExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyScenarioExecutionStarting(gauge.messages.Messages.ScenarioExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getNotifyScenarioExecutionStartingMethod(), responseObserver);
    }

    /**
     * <pre>
     * NotifyStepExecutionStarting is a RPC to tell plugins that the step execution has started.
     * Accepts a StepExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyStepExecutionStarting(gauge.messages.Messages.StepExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getNotifyStepExecutionStartingMethod(), responseObserver);
    }

    /**
     * <pre>
     * NotifyStepExecutionEnding is a RPC to tell plugins that the step execution has finished.
     * Accepts a StepExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyStepExecutionEnding(gauge.messages.Messages.StepExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getNotifyStepExecutionEndingMethod(), responseObserver);
    }

    /**
     * <pre>
     * NotifyScenarioExecutionEnding is a RPC to tell plugins that the scenario execution has finished.
     * Accepts a ScenarioExecutionEndingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyScenarioExecutionEnding(gauge.messages.Messages.ScenarioExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getNotifyScenarioExecutionEndingMethod(), responseObserver);
    }

    /**
     * <pre>
     * NotifySpecExecutionEnding is a RPC to tell plugins that the specification execution has finished.
     * Accepts a SpecExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifySpecExecutionEnding(gauge.messages.Messages.SpecExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getNotifySpecExecutionEndingMethod(), responseObserver);
    }

    /**
     * <pre>
     * NotifyExecutionEnding is a RPC to tell plugins that the execution has finished.
     * Accepts a ExecutionEndingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyExecutionEnding(gauge.messages.Messages.ExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getNotifyExecutionEndingMethod(), responseObserver);
    }

    /**
     * <pre>
     * NotifySuiteResult is a RPC to tell about the end result of execution
     * Accepts a SuiteExecutionResult message and returns a Empty message.
     * </pre>
     */
    public void notifySuiteResult(gauge.messages.Messages.SuiteExecutionResult request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getNotifySuiteResultMethod(), responseObserver);
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
            getNotifyExecutionStartingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.ExecutionStartingRequest,
                gauge.messages.Messages.Empty>(
                  this, METHODID_NOTIFY_EXECUTION_STARTING)))
          .addMethod(
            getNotifySpecExecutionStartingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.SpecExecutionStartingRequest,
                gauge.messages.Messages.Empty>(
                  this, METHODID_NOTIFY_SPEC_EXECUTION_STARTING)))
          .addMethod(
            getNotifyScenarioExecutionStartingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.ScenarioExecutionStartingRequest,
                gauge.messages.Messages.Empty>(
                  this, METHODID_NOTIFY_SCENARIO_EXECUTION_STARTING)))
          .addMethod(
            getNotifyStepExecutionStartingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StepExecutionStartingRequest,
                gauge.messages.Messages.Empty>(
                  this, METHODID_NOTIFY_STEP_EXECUTION_STARTING)))
          .addMethod(
            getNotifyStepExecutionEndingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.StepExecutionEndingRequest,
                gauge.messages.Messages.Empty>(
                  this, METHODID_NOTIFY_STEP_EXECUTION_ENDING)))
          .addMethod(
            getNotifyScenarioExecutionEndingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.ScenarioExecutionEndingRequest,
                gauge.messages.Messages.Empty>(
                  this, METHODID_NOTIFY_SCENARIO_EXECUTION_ENDING)))
          .addMethod(
            getNotifySpecExecutionEndingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.SpecExecutionEndingRequest,
                gauge.messages.Messages.Empty>(
                  this, METHODID_NOTIFY_SPEC_EXECUTION_ENDING)))
          .addMethod(
            getNotifyExecutionEndingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.ExecutionEndingRequest,
                gauge.messages.Messages.Empty>(
                  this, METHODID_NOTIFY_EXECUTION_ENDING)))
          .addMethod(
            getNotifySuiteResultMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                gauge.messages.Messages.SuiteExecutionResult,
                gauge.messages.Messages.Empty>(
                  this, METHODID_NOTIFY_SUITE_RESULT)))
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
   * Reporter services is meant for reporting plugins, or others plugins which are interested the live events
   * </pre>
   */
  public static final class ReporterStub extends io.grpc.stub.AbstractStub<ReporterStub> {
    private ReporterStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ReporterStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReporterStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ReporterStub(channel, callOptions);
    }

    /**
     * <pre>
     * NotifyExecutionStarting is a RPC to tell plugins that the execution has started.
     * Accepts a ExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyExecutionStarting(gauge.messages.Messages.ExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNotifyExecutionStartingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * NotifySpecExecutionStarting is a RPC to tell plugins that the specification execution has started.
     * Accepts a SpecExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifySpecExecutionStarting(gauge.messages.Messages.SpecExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNotifySpecExecutionStartingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * NotifyScenarioExecutionStarting is a RPC to tell plugins that the scenario execution has started.
     * Accepts a ScenarioExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyScenarioExecutionStarting(gauge.messages.Messages.ScenarioExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNotifyScenarioExecutionStartingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * NotifyStepExecutionStarting is a RPC to tell plugins that the step execution has started.
     * Accepts a StepExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyStepExecutionStarting(gauge.messages.Messages.StepExecutionStartingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNotifyStepExecutionStartingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * NotifyStepExecutionEnding is a RPC to tell plugins that the step execution has finished.
     * Accepts a StepExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyStepExecutionEnding(gauge.messages.Messages.StepExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNotifyStepExecutionEndingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * NotifyScenarioExecutionEnding is a RPC to tell plugins that the scenario execution has finished.
     * Accepts a ScenarioExecutionEndingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyScenarioExecutionEnding(gauge.messages.Messages.ScenarioExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNotifyScenarioExecutionEndingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * NotifySpecExecutionEnding is a RPC to tell plugins that the specification execution has finished.
     * Accepts a SpecExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public void notifySpecExecutionEnding(gauge.messages.Messages.SpecExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNotifySpecExecutionEndingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * NotifyExecutionEnding is a RPC to tell plugins that the execution has finished.
     * Accepts a ExecutionEndingRequest message and returns a Empty message
     * </pre>
     */
    public void notifyExecutionEnding(gauge.messages.Messages.ExecutionEndingRequest request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNotifyExecutionEndingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * NotifySuiteResult is a RPC to tell about the end result of execution
     * Accepts a SuiteExecutionResult message and returns a Empty message.
     * </pre>
     */
    public void notifySuiteResult(gauge.messages.Messages.SuiteExecutionResult request,
        io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNotifySuiteResultMethod(), getCallOptions()), request, responseObserver);
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
   * Reporter services is meant for reporting plugins, or others plugins which are interested the live events
   * </pre>
   */
  public static final class ReporterBlockingStub extends io.grpc.stub.AbstractStub<ReporterBlockingStub> {
    private ReporterBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ReporterBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReporterBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ReporterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * NotifyExecutionStarting is a RPC to tell plugins that the execution has started.
     * Accepts a ExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public gauge.messages.Messages.Empty notifyExecutionStarting(gauge.messages.Messages.ExecutionStartingRequest request) {
      return blockingUnaryCall(
          getChannel(), getNotifyExecutionStartingMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * NotifySpecExecutionStarting is a RPC to tell plugins that the specification execution has started.
     * Accepts a SpecExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public gauge.messages.Messages.Empty notifySpecExecutionStarting(gauge.messages.Messages.SpecExecutionStartingRequest request) {
      return blockingUnaryCall(
          getChannel(), getNotifySpecExecutionStartingMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * NotifyScenarioExecutionStarting is a RPC to tell plugins that the scenario execution has started.
     * Accepts a ScenarioExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public gauge.messages.Messages.Empty notifyScenarioExecutionStarting(gauge.messages.Messages.ScenarioExecutionStartingRequest request) {
      return blockingUnaryCall(
          getChannel(), getNotifyScenarioExecutionStartingMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * NotifyStepExecutionStarting is a RPC to tell plugins that the step execution has started.
     * Accepts a StepExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public gauge.messages.Messages.Empty notifyStepExecutionStarting(gauge.messages.Messages.StepExecutionStartingRequest request) {
      return blockingUnaryCall(
          getChannel(), getNotifyStepExecutionStartingMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * NotifyStepExecutionEnding is a RPC to tell plugins that the step execution has finished.
     * Accepts a StepExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public gauge.messages.Messages.Empty notifyStepExecutionEnding(gauge.messages.Messages.StepExecutionEndingRequest request) {
      return blockingUnaryCall(
          getChannel(), getNotifyStepExecutionEndingMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * NotifyScenarioExecutionEnding is a RPC to tell plugins that the scenario execution has finished.
     * Accepts a ScenarioExecutionEndingRequest message and returns a Empty message
     * </pre>
     */
    public gauge.messages.Messages.Empty notifyScenarioExecutionEnding(gauge.messages.Messages.ScenarioExecutionEndingRequest request) {
      return blockingUnaryCall(
          getChannel(), getNotifyScenarioExecutionEndingMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * NotifySpecExecutionEnding is a RPC to tell plugins that the specification execution has finished.
     * Accepts a SpecExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public gauge.messages.Messages.Empty notifySpecExecutionEnding(gauge.messages.Messages.SpecExecutionEndingRequest request) {
      return blockingUnaryCall(
          getChannel(), getNotifySpecExecutionEndingMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * NotifyExecutionEnding is a RPC to tell plugins that the execution has finished.
     * Accepts a ExecutionEndingRequest message and returns a Empty message
     * </pre>
     */
    public gauge.messages.Messages.Empty notifyExecutionEnding(gauge.messages.Messages.ExecutionEndingRequest request) {
      return blockingUnaryCall(
          getChannel(), getNotifyExecutionEndingMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * NotifySuiteResult is a RPC to tell about the end result of execution
     * Accepts a SuiteExecutionResult message and returns a Empty message.
     * </pre>
     */
    public gauge.messages.Messages.Empty notifySuiteResult(gauge.messages.Messages.SuiteExecutionResult request) {
      return blockingUnaryCall(
          getChannel(), getNotifySuiteResultMethod(), getCallOptions(), request);
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
   * Reporter services is meant for reporting plugins, or others plugins which are interested the live events
   * </pre>
   */
  public static final class ReporterFutureStub extends io.grpc.stub.AbstractStub<ReporterFutureStub> {
    private ReporterFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ReporterFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReporterFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ReporterFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * NotifyExecutionStarting is a RPC to tell plugins that the execution has started.
     * Accepts a ExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> notifyExecutionStarting(
        gauge.messages.Messages.ExecutionStartingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNotifyExecutionStartingMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * NotifySpecExecutionStarting is a RPC to tell plugins that the specification execution has started.
     * Accepts a SpecExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> notifySpecExecutionStarting(
        gauge.messages.Messages.SpecExecutionStartingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNotifySpecExecutionStartingMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * NotifyScenarioExecutionStarting is a RPC to tell plugins that the scenario execution has started.
     * Accepts a ScenarioExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> notifyScenarioExecutionStarting(
        gauge.messages.Messages.ScenarioExecutionStartingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNotifyScenarioExecutionStartingMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * NotifyStepExecutionStarting is a RPC to tell plugins that the step execution has started.
     * Accepts a StepExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> notifyStepExecutionStarting(
        gauge.messages.Messages.StepExecutionStartingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNotifyStepExecutionStartingMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * NotifyStepExecutionEnding is a RPC to tell plugins that the step execution has finished.
     * Accepts a StepExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> notifyStepExecutionEnding(
        gauge.messages.Messages.StepExecutionEndingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNotifyStepExecutionEndingMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * NotifyScenarioExecutionEnding is a RPC to tell plugins that the scenario execution has finished.
     * Accepts a ScenarioExecutionEndingRequest message and returns a Empty message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> notifyScenarioExecutionEnding(
        gauge.messages.Messages.ScenarioExecutionEndingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNotifyScenarioExecutionEndingMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * NotifySpecExecutionEnding is a RPC to tell plugins that the specification execution has finished.
     * Accepts a SpecExecutionStartingRequest message and returns a Empty message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> notifySpecExecutionEnding(
        gauge.messages.Messages.SpecExecutionEndingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNotifySpecExecutionEndingMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * NotifyExecutionEnding is a RPC to tell plugins that the execution has finished.
     * Accepts a ExecutionEndingRequest message and returns a Empty message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> notifyExecutionEnding(
        gauge.messages.Messages.ExecutionEndingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNotifyExecutionEndingMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * NotifySuiteResult is a RPC to tell about the end result of execution
     * Accepts a SuiteExecutionResult message and returns a Empty message.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<gauge.messages.Messages.Empty> notifySuiteResult(
        gauge.messages.Messages.SuiteExecutionResult request) {
      return futureUnaryCall(
          getChannel().newCall(getNotifySuiteResultMethod(), getCallOptions()), request);
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

  private static final int METHODID_NOTIFY_EXECUTION_STARTING = 0;
  private static final int METHODID_NOTIFY_SPEC_EXECUTION_STARTING = 1;
  private static final int METHODID_NOTIFY_SCENARIO_EXECUTION_STARTING = 2;
  private static final int METHODID_NOTIFY_STEP_EXECUTION_STARTING = 3;
  private static final int METHODID_NOTIFY_STEP_EXECUTION_ENDING = 4;
  private static final int METHODID_NOTIFY_SCENARIO_EXECUTION_ENDING = 5;
  private static final int METHODID_NOTIFY_SPEC_EXECUTION_ENDING = 6;
  private static final int METHODID_NOTIFY_EXECUTION_ENDING = 7;
  private static final int METHODID_NOTIFY_SUITE_RESULT = 8;
  private static final int METHODID_KILL = 9;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ReporterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ReporterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_NOTIFY_EXECUTION_STARTING:
          serviceImpl.notifyExecutionStarting((gauge.messages.Messages.ExecutionStartingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
          break;
        case METHODID_NOTIFY_SPEC_EXECUTION_STARTING:
          serviceImpl.notifySpecExecutionStarting((gauge.messages.Messages.SpecExecutionStartingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
          break;
        case METHODID_NOTIFY_SCENARIO_EXECUTION_STARTING:
          serviceImpl.notifyScenarioExecutionStarting((gauge.messages.Messages.ScenarioExecutionStartingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
          break;
        case METHODID_NOTIFY_STEP_EXECUTION_STARTING:
          serviceImpl.notifyStepExecutionStarting((gauge.messages.Messages.StepExecutionStartingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
          break;
        case METHODID_NOTIFY_STEP_EXECUTION_ENDING:
          serviceImpl.notifyStepExecutionEnding((gauge.messages.Messages.StepExecutionEndingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
          break;
        case METHODID_NOTIFY_SCENARIO_EXECUTION_ENDING:
          serviceImpl.notifyScenarioExecutionEnding((gauge.messages.Messages.ScenarioExecutionEndingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
          break;
        case METHODID_NOTIFY_SPEC_EXECUTION_ENDING:
          serviceImpl.notifySpecExecutionEnding((gauge.messages.Messages.SpecExecutionEndingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
          break;
        case METHODID_NOTIFY_EXECUTION_ENDING:
          serviceImpl.notifyExecutionEnding((gauge.messages.Messages.ExecutionEndingRequest) request,
              (io.grpc.stub.StreamObserver<gauge.messages.Messages.Empty>) responseObserver);
          break;
        case METHODID_NOTIFY_SUITE_RESULT:
          serviceImpl.notifySuiteResult((gauge.messages.Messages.SuiteExecutionResult) request,
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

  private static abstract class ReporterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ReporterBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gauge.messages.Services.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Reporter");
    }
  }

  private static final class ReporterFileDescriptorSupplier
      extends ReporterBaseDescriptorSupplier {
    ReporterFileDescriptorSupplier() {}
  }

  private static final class ReporterMethodDescriptorSupplier
      extends ReporterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ReporterMethodDescriptorSupplier(String methodName) {
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
      synchronized (ReporterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ReporterFileDescriptorSupplier())
              .addMethod(getNotifyExecutionStartingMethod())
              .addMethod(getNotifySpecExecutionStartingMethod())
              .addMethod(getNotifyScenarioExecutionStartingMethod())
              .addMethod(getNotifyStepExecutionStartingMethod())
              .addMethod(getNotifyStepExecutionEndingMethod())
              .addMethod(getNotifyScenarioExecutionEndingMethod())
              .addMethod(getNotifySpecExecutionEndingMethod())
              .addMethod(getNotifyExecutionEndingMethod())
              .addMethod(getNotifySuiteResultMethod())
              .addMethod(getKillMethod())
              .build();
        }
      }
    }
    return result;
  }
}
