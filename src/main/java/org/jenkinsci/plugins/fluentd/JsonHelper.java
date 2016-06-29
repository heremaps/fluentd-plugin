package org.jenkinsci.plugins.fluentd;

import hudson.Util;
import org.fluentd.logger.FluentLogger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akbashev on 5/3/2016.
 */
public class JsonHelper {
    public static Map<String, Object> fillMap(@Nonnull JSONObject originalJson, @Nonnull JSONObject extension) {
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
}
