package org.jenkinsci.plugins.fluentd;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonHelper {
    static Map<String, Object> fillMap(@Nonnull JSONObject originalJson, @Nonnull JSONObject extension) {
        final Map<String, Object> data = new HashMap<>();
        for (Object keyObject : originalJson.keySet()) {
            final String key = (String) keyObject;
            data.put(key, originalJson.get(key));
        }

        for (Object keyObject : extension.keySet()) {
            final String key = (String) keyObject;
            data.put(key, extension.get(key));
        }

        return data;
    }

    static List<Map<String, Object>> fillMap(@Nonnull JSONArray originalJsonArray, @Nonnull JSONObject extension) {
        final ArrayList<Map<String, Object>> dataArray = new ArrayList<>(originalJsonArray.size());

        for (Object element : originalJsonArray) {
            dataArray.add(fillMap((JSONObject) element, extension));
        }

        return dataArray;
    }
}
