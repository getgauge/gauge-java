  package com.thoughtworks.gauge;

  public class SkipScenarioException extends RuntimeException {
    public SkipScenarioException(String message) {
      super(message);
    }
  }
