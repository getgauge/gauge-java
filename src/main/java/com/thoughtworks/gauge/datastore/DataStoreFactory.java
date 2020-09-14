/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.datastore;

import static com.thoughtworks.gauge.GaugeConstant.ENABLE_MULTITHREADING_ENV;


/**
 * @deprecated DataStoreFactory is no longer valid. This API will throw an Exception in multithreaded execution.
 * <p>Use specific data stores instead.</p>
 * @see com.thoughtworks.gauge.datastore.SuiteDataStore
 * @see com.thoughtworks.gauge.datastore.SpecDataStore
 * @see com.thoughtworks.gauge.datastore.ScenarioDataStore
 */
@Deprecated
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
        if (isMultithreadingExecution()) {
            throw new RuntimeException("DataStoreFactory cannot be used for multithreaded execution. Use SuiteDataStore.");
        }
        return suiteDataStore.get();
    }

    /**
     * @return The current instance of the SpecDataStore
     */
    public static DataStore getSpecDataStore() {
        if (isMultithreadingExecution()) {
            throw new RuntimeException("DataStoreFactory cannot be used for multithreaded execution. Use SpecDataStore.");
        }
        return specDataStore.get();
    }


    /**
     * @return The current instance of the ScenarioDataStore
     */
    public static DataStore getScenarioDataStore() {
        if (isMultithreadingExecution()) {
            throw new RuntimeException("DataStoreFactory cannot be used for multithreaded execution. Use ScenarioDataStore.");
        }
        return scenarioDataStore.get();
    }

    static void clearSuiteDataStore() {
        suiteDataStore.get().clear();
    }

    static void clearSpecDataStore() {
        specDataStore.get().clear();
    }

    static void clearScenarioDataStore() {
        scenarioDataStore.get().clear();
    }

    private static boolean isMultithreadingExecution() {
        return Boolean.valueOf(System.getenv(ENABLE_MULTITHREADING_ENV));
    }

}
