package com.jsqix.yunpos.app.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dq on 2016/7/4.
 * value 不能为空
 */
public class MapVaules {
    private Map<String, Object> map;

    public MapVaules() {
        map = new HashMap<>();
    }

    public void put(String key, Object value) {
        if (!CommUtils.isNull(value)) {
            map.put(key, value);
        }
    }

    public Map getMap() {
        return map;
    }

    public void clear() {
        if (map != null) map.clear();
    }

    public void destroy() {
        map = null;
    }
}
