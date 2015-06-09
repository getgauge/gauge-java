// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.dataStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataStore {

    HashMap<Object, Object> map = new HashMap<Object, Object>();

    /**
     * @param key - Key of the data entry
     * @param value - value of the Data entry
     */
    public void put(Object key, Object value) {
        map.put(key, value);
    }

    /**
     * @param key - Key of the data entry to remove
     * @return - The value of the entry removed. Null if no entry.
     */
    public Object remove(Object key) {
        return map.remove(key);
    }

    /**
     * @param key - Key of the data entry whose value is needed
     * @return - The value corresponding to the key. null if there is no value stored
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
