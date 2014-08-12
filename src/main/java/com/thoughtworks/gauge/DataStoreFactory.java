package com.thoughtworks.gauge;

public class DataStoreFactory {
    private static DataStore suiteDataStore = new DataStore();
    private static DataStore specDataStore = new DataStore();
    private static DataStore scenarioDataStore = new DataStore();


    public static DataStore getSuiteDataStore() {
        return suiteDataStore;
    }

    public static DataStore getSpecDataStore() {
        return specDataStore;
    }

    public static DataStore getScenarioDataStore() {
        return scenarioDataStore;
    }
}
