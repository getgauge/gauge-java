// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

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
