/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/

package com.thoughtworks.gauge.datastore;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ScenarioDataStore {
    private static final InheritableThreadLocal<ConcurrentHashMap<Object, Object>> MAP = new InheritableThreadLocal<ConcurrentHashMap<Object, Object>>() {
        @Override
        protected ConcurrentHashMap<Object, Object> initialValue() {
            return new ConcurrentHashMap<>();
        }
    };

    /**
     * @param key   - Key of the data entry
     * @param value - value of the Data entry
     */
    public static synchronized void put(Object key, Object value) {
        if (key != null && value != null)  {
            MAP.get().put(key, value);
        }
    }

    /**
     * @param key - Key of the data entry to remove
     * @return The value of the entry removed. Null if no entry.
     */
    public static synchronized Object remove(Object key) {
        if (key != null) {
            return MAP.get().remove(key);
        }
        return null;
    }

    /**
     * @param key - Key of the data entry whose value is needed
     * @return The value corresponding to the key. null if there is no value stored
     */
    public static synchronized Object get(Object key) {
        if (key != null) {
            return MAP.get().get(key);
        }
        return null;
    }

    /**
     * Returns a {@link Set} view of the keys in datastore.
     * @return A set of keys stored in datastore
     */
    public static synchronized Set<Object> items() {
        return Collections.unmodifiableSet(MAP.get().keySet());
    }

    static synchronized void clear() {
        MAP.get().clear();
    }

}
