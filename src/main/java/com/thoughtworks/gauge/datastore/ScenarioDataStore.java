/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/

package com.thoughtworks.gauge.datastore;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScenarioDataStore {
    private static ThreadLocal<ConcurrentHashMap<Object, Object>> map = ThreadLocal.withInitial(ConcurrentHashMap::new);

    /**
     * @param key - Key of the data entry to remove
     * @return The value of the entry removed. Null if no entry.
     */
    public static synchronized Object remove(Object key) {
        if (key != null) {
            return getMap().remove(key);
        }
        return null;
    }

    /**
     * @param key - Key of the data entry whose value is needed
     * @return The value corresponding to the key. null if there is no value stored
     */
    public static synchronized Object get(Object key) {
        if (key != null) {
            return getMap().get(key);
        }
        return null;
    }

    private static synchronized ConcurrentHashMap<Object, Object> getMap() {
        return map.get();
    }

    public static synchronized Map<Object, Object> items() {
        return Collections.unmodifiableMap(getMap());
    }

    static synchronized void clear() {
        getMap().clear();
    }
}
