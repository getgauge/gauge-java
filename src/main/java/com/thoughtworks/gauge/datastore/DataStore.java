/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.datastore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataStore {

    private HashMap<Object, Object> map = new HashMap<>();

    /**
     * @param key - Key of the data entry
     * @param value - value of the Data entry
     */
    public void put(Object key, Object value) {
        map.put(key, value);
    }

    /**
     * @param key - Key of the data entry to remove
     * @return The value of the entry removed. Null if no entry.
     */
    public Object remove(Object key) {
        return map.remove(key);
    }

    /**
     * @param key - Key of the data entry whose value is needed
     * @return The value corresponding to the key. null if there is no value stored
     */
    public Object get(Object key) {
        return map.get(key);
    }

    void clear() {
        map.clear();
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return map.entrySet();
    }

}
