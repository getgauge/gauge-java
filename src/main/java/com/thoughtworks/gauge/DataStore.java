package com.thoughtworks.gauge;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataStore {

    HashMap<Object, Object> map = new HashMap<Object, Object>();

    public void put(Object key, Object value) {
        map.put(key, value);
    }

    public Object remove(Object key) {
        return map.remove(key);
    }

    public Object get(Object key) {
        return map.get(key);
    }

    void clear(){
        map.clear();
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return map.entrySet();
    }

}
