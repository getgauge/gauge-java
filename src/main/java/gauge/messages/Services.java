// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: services.proto
// Protobuf Java Version: 4.30.1

package gauge.messages;

public final class Services {
  private Services() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 30,
      /* patch= */ 1,
      /* suffix= */ "",
      Services.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016services.proto\022\016gauge.messages\032\016messag" +
      "es.proto2\347\022\n\006Runner\022Y\n\014ValidateStep\022#.ga" +
      "uge.messages.StepValidateRequest\032$.gauge" +
      ".messages.StepValidateResponse\022n\n\030Initia" +
      "lizeSuiteDataStore\022).gauge.messages.Suit" +
      "eDataStoreInitRequest\032\'.gauge.messages.E" +
      "xecutionStatusResponse\022c\n\016StartExecution" +
      "\022(.gauge.messages.ExecutionStartingReque" +
      "st\032\'.gauge.messages.ExecutionStatusRespo" +
      "nse\022l\n\027InitializeSpecDataStore\022(.gauge.m" +
      "essages.SpecDataStoreInitRequest\032\'.gauge" +
      ".messages.ExecutionStatusResponse\022k\n\022Sta" +
      "rtSpecExecution\022,.gauge.messages.SpecExe" +
      "cutionStartingRequest\032\'.gauge.messages.E" +
      "xecutionStatusResponse\022t\n\033InitializeScen" +
      "arioDataStore\022,.gauge.messages.ScenarioD" +
      "ataStoreInitRequest\032\'.gauge.messages.Exe" +
      "cutionStatusResponse\022s\n\026StartScenarioExe" +
      "cution\0220.gauge.messages.ScenarioExecutio" +
      "nStartingRequest\032\'.gauge.messages.Execut" +
      "ionStatusResponse\022k\n\022StartStepExecution\022" +
      ",.gauge.messages.StepExecutionStartingRe" +
      "quest\032\'.gauge.messages.ExecutionStatusRe" +
      "sponse\022Z\n\013ExecuteStep\022\".gauge.messages.E" +
      "xecuteStepRequest\032\'.gauge.messages.Execu" +
      "tionStatusResponse\022j\n\023FinishStepExecutio" +
      "n\022*.gauge.messages.StepExecutionEndingRe" +
      "quest\032\'.gauge.messages.ExecutionStatusRe" +
      "sponse\022r\n\027FinishScenarioExecution\022..gaug" +
      "e.messages.ScenarioExecutionEndingReques" +
      "t\032\'.gauge.messages.ExecutionStatusRespon" +
      "se\022j\n\023FinishSpecExecution\022*.gauge.messag" +
      "es.SpecExecutionEndingRequest\032\'.gauge.me" +
      "ssages.ExecutionStatusResponse\022b\n\017Finish" +
      "Execution\022&.gauge.messages.ExecutionEndi" +
      "ngRequest\032\'.gauge.messages.ExecutionStat" +
      "usResponse\022D\n\tCacheFile\022 .gauge.messages" +
      ".CacheFileRequest\032\025.gauge.messages.Empty" +
      "\022P\n\013GetStepName\022\037.gauge.messages.StepNam" +
      "eRequest\032 .gauge.messages.StepNameRespon" +
      "se\022_\n\017GetGlobPatterns\022\025.gauge.messages.E" +
      "mpty\0325.gauge.messages.ImplementationFile" +
      "GlobPatternResponse\022S\n\014GetStepNames\022 .ga" +
      "uge.messages.StepNamesRequest\032!.gauge.me" +
      "ssages.StepNamesResponse\022_\n\020GetStepPosit" +
      "ions\022$.gauge.messages.StepPositionsReque" +
      "st\032%.gauge.messages.StepPositionsRespons" +
      "e\022_\n\026GetImplementationFiles\022\025.gauge.mess" +
      "ages.Empty\032..gauge.messages.Implementati" +
      "onFileListResponse\022X\n\rImplementStub\022-.ga" +
      "uge.messages.StubImplementationCodeReque" +
      "st\032\030.gauge.messages.FileDiff\022M\n\010Refactor" +
      "\022\037.gauge.messages.RefactorRequest\032 .gaug" +
      "e.messages.RefactorResponse\022A\n\004Kill\022\".ga" +
      "uge.messages.KillProcessRequest\032\025.gauge." +
      "messages.Empty\022z\n\036NotifyConceptExecution" +
      "Starting\022/.gauge.messages.ConceptExecuti" +
      "onStartingRequest\032\'.gauge.messages.Execu" +
      "tionStatusResponse\022v\n\034NotifyConceptExecu" +
      "tionEnding\022-.gauge.messages.ConceptExecu" +
      "tionEndingRequest\032\'.gauge.messages.Execu" +
      "tionStatusResponse2\377\010\n\010Reporter\022Z\n\027Notif" +
      "yExecutionStarting\022(.gauge.messages.Exec" +
      "utionStartingRequest\032\025.gauge.messages.Em" +
      "pty\022b\n\033NotifySpecExecutionStarting\022,.gau" +
      "ge.messages.SpecExecutionStartingRequest" +
      "\032\025.gauge.messages.Empty\022j\n\037NotifyScenari" +
      "oExecutionStarting\0220.gauge.messages.Scen" +
      "arioExecutionStartingRequest\032\025.gauge.mes" +
      "sages.Empty\022h\n\036NotifyConceptExecutionSta" +
      "rting\022/.gauge.messages.ConceptExecutionS" +
      "tartingRequest\032\025.gauge.messages.Empty\022d\n" +
      "\034NotifyConceptExecutionEnding\022-.gauge.me" +
      "ssages.ConceptExecutionEndingRequest\032\025.g" +
      "auge.messages.Empty\022b\n\033NotifyStepExecuti" +
      "onStarting\022,.gauge.messages.StepExecutio" +
      "nStartingRequest\032\025.gauge.messages.Empty\022" +
      "^\n\031NotifyStepExecutionEnding\022*.gauge.mes" +
      "sages.StepExecutionEndingRequest\032\025.gauge" +
      ".messages.Empty\022f\n\035NotifyScenarioExecuti" +
      "onEnding\022..gauge.messages.ScenarioExecut" +
      "ionEndingRequest\032\025.gauge.messages.Empty\022" +
      "^\n\031NotifySpecExecutionEnding\022*.gauge.mes" +
      "sages.SpecExecutionEndingRequest\032\025.gauge" +
      ".messages.Empty\022V\n\025NotifyExecutionEnding" +
      "\022&.gauge.messages.ExecutionEndingRequest" +
      "\032\025.gauge.messages.Empty\022P\n\021NotifySuiteRe" +
      "sult\022$.gauge.messages.SuiteExecutionResu" +
      "lt\032\025.gauge.messages.Empty\022A\n\004Kill\022\".gaug" +
      "e.messages.KillProcessRequest\032\025.gauge.me" +
      "ssages.Empty2\223\001\n\nDocumenter\022B\n\014GenerateD" +
      "ocs\022\033.gauge.messages.SpecDetails\032\025.gauge" +
      ".messages.Empty\022A\n\004Kill\022\".gauge.messages" +
      ".KillProcessRequest\032\025.gauge.messages.Emp" +
      "tyBT\n\016gauge.messagesZ1github.com/getgaug" +
      "e/gauge-proto/go/gauge_messages\252\002\016Gauge." +
      "Messagesb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          gauge.messages.Messages.getDescriptor(),
        });
    descriptor.resolveAllFeaturesImmutable();
    gauge.messages.Messages.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
