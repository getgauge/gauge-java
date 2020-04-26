/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.datastore;

public class DataStoreFactory {
    private static ThreadLocal<DataStore> suiteDataStore = new InheritableThreadLocal<DataStore>() {
        @Override
        protected DataStore initialValue() {
            return new DataStore();
        }
    };
    private static ThreadLocal<DataStore> specDataStore = new InheritableThreadLocal<DataStore>() {
        @Override
        protected DataStore initialValue() {
            return new DataStore();
        }
    };
    private static ThreadLocal<DataStore> scenarioDataStore = new InheritableThreadLocal<DataStore>() {
        @Override
        protected DataStore initialValue() {
            return new DataStore();
        }
    };


    /**
     * @return The current instance of the SuiteDataStore
     */
    public static DataStore getSuiteDataStore() {
        return suiteDataStore.get();
    }

    /**
     * @return The current instance of the SpecDataStore
     */
    public static DataStore getSpecDataStore() {
        return specDataStore.get();
    }

    /**
     * @return The current instance of the ScenarioDataStore
     */
    public static DataStore getScenarioDataStore() {
        return scenarioDataStore.get();
    }

}
