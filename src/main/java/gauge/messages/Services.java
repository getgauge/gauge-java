// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: services.proto
// Protobuf Java Version: 4.27.1

package gauge.messages;

public final class Services {
  private Services() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 27,
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
      "es.proto2\363\020\n\006Runner\022Y\n\014ValidateStep\022#.ga" +
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
      "messages.Empty2\257\007\n\010Reporter\022Z\n\027NotifyExe" +
      "cutionStarting\022(.gauge.messages.Executio" +
      "nStartingRequest\032\025.gauge.messages.Empty\022" +
      "b\n\033NotifySpecExecutionStarting\022,.gauge.m" +
      "essages.SpecExecutionStartingRequest\032\025.g" +
      "auge.messages.Empty\022j\n\037NotifyScenarioExe" +
      "cutionStarting\0220.gauge.messages.Scenario" +
      "ExecutionStartingRequest\032\025.gauge.message" +
      "s.Empty\022b\n\033NotifyStepExecutionStarting\022," +
      ".gauge.messages.StepExecutionStartingReq" +
      "uest\032\025.gauge.messages.Empty\022^\n\031NotifySte" +
      "pExecutionEnding\022*.gauge.messages.StepEx" +
      "ecutionEndingRequest\032\025.gauge.messages.Em" +
      "pty\022f\n\035NotifyScenarioExecutionEnding\022..g" +
      "auge.messages.ScenarioExecutionEndingReq" +
      "uest\032\025.gauge.messages.Empty\022^\n\031NotifySpe" +
      "cExecutionEnding\022*.gauge.messages.SpecEx" +
      "ecutionEndingRequest\032\025.gauge.messages.Em" +
      "pty\022V\n\025NotifyExecutionEnding\022&.gauge.mes" +
      "sages.ExecutionEndingRequest\032\025.gauge.mes" +
      "sages.Empty\022P\n\021NotifySuiteResult\022$.gauge" +
      ".messages.SuiteExecutionResult\032\025.gauge.m" +
      "essages.Empty\022A\n\004Kill\022\".gauge.messages.K" +
      "illProcessRequest\032\025.gauge.messages.Empty" +
      "2\223\001\n\nDocumenter\022B\n\014GenerateDocs\022\033.gauge." +
      "messages.SpecDetails\032\025.gauge.messages.Em" +
      "pty\022A\n\004Kill\022\".gauge.messages.KillProcess" +
      "Request\032\025.gauge.messages.EmptyBT\n\016gauge." +
      "messagesZ1github.com/getgauge/gauge-prot" +
      "o/go/gauge_messages\252\002\016Gauge.Messagesb\006pr" +
      "oto3"
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
