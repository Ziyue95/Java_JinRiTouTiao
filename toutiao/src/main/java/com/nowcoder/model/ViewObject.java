package com.nowcoder.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by rainday on 16/6/30.
 */
public class ViewObject {

    private Map<String, Object> objs = new HashMap<String, Object>();

    /** Map a string key to an object */
    public void set(String key, Object value) {
        objs.put(key, value);
    }
    /** Get the string key from an object */
    public Object get(String key) {
        return objs.get(key);
    }
}
