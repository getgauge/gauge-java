package com.thoughtworks.gauge;

public class SpecificationInfo {
    private Specification currentSpecification;
    private Scenario currentScenario;
    private StepDetails currentStep;

    public Specification getCurrentSpecification() {
        return currentSpecification;
    }

    public Scenario getCurrentScenario() {
        return currentScenario;
    }

    public StepDetails getCurrentStep() {
        return currentStep;
    }

    public void setStep(StepDetails step) {
        this.currentStep = step;
    }

    public void setScenario(Scenario scenario) {
        this.currentScenario = scenario;
    }

    public void setSpecification(Specification specification) {
        this.currentSpecification = specification;
    }
}
