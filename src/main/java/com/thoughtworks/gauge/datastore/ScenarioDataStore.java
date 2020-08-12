package com.thoughtworks.gauge.datastore;

import java.util.concurrent.ConcurrentHashMap;

public class ScenarioDataStore {
    private static ThreadLocal<ConcurrentHashMap<Object, Object>> map = ThreadLocal.withInitial(ConcurrentHashMap::new);

    /**
     * @param key   - Key of the data entry
     * @param value - value of the Data entry
     */
    public static synchronized void put(Object key, Object value) {
        map.get().put(key, value);
    }

    /**
     * @param key - Key of the data entry to remove
     * @return The value of the entry removed. Null if no entry.
     */
    public static synchronized Object remove(Object key) {
        return map.get().remove(key);
    }

    /**
     * @param key - Key of the data entry whose value is needed
     * @return The value corresponding to the key. null if there is no value stored
     */
    public static synchronized Object get(Object key) {
        return map.get().get(key);
    }

    static synchronized void clear() {
        map.get().clear();
    }
}
