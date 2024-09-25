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
    private static final ThreadLocal<DataStore> SUITE_DATA_STORE = new InheritableThreadLocal<>() {
        @Override
        protected DataStore initialValue() {
            return new DataStore();
        }
    };
    private static final ThreadLocal<DataStore> SPEC_DATA_STORE = new InheritableThreadLocal<>() {
        @Override
        protected DataStore initialValue() {
            return new DataStore();
        }
    };
    private static final ThreadLocal<DataStore> SCENARIO_DATA_STORE = new InheritableThreadLocal<>() {
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
        return SUITE_DATA_STORE.get();
    }

    /**
     * @return The current instance of the SpecDataStore
     */
    public static DataStore getSpecDataStore() {
        if (isMultithreadingExecution()) {
            throw new RuntimeException("DataStoreFactory cannot be used for multithreaded execution. Use SpecDataStore.");
        }
        return SPEC_DATA_STORE.get();
    }


    /**
     * @return The current instance of the ScenarioDataStore
     */
    public static DataStore getScenarioDataStore() {
        if (isMultithreadingExecution()) {
            throw new RuntimeException("DataStoreFactory cannot be used for multithreaded execution. Use ScenarioDataStore.");
        }
        return SCENARIO_DATA_STORE.get();
    }

    static void clearSuiteDataStore() {
        SUITE_DATA_STORE.get().clear();
    }

    static void clearSpecDataStore() {
        SPEC_DATA_STORE.get().clear();
    }

    static void clearScenarioDataStore() {
        SCENARIO_DATA_STORE.get().clear();
    }

    private static boolean isMultithreadingExecution() {
        return Boolean.parseBoolean(System.getenv(ENABLE_MULTITHREADING_ENV));
    }

}
