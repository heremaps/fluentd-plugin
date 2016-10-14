package org.jenkinsci.plugins.fluentd;

import hudson.Util;
import org.fluentd.logger.FluentLogger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.logging.Logger;

import static org.jenkinsci.plugins.fluentd.JsonHelper.fillMap;

public class FluentHelper {
    private static final Logger LOGGER = Logger.getLogger(Fluentd.class.getName());

    static void sendJson(FluentLogger fluentLogger, String tag, Map<String, String> envVars, String jsonFromExtension,
                         String jsonFromFile, long startTimeInMillis) throws IllegalArgumentException {
        final JSONObject extension;
        if (!jsonFromExtension.isEmpty()) {
            extension = (JSONObject) decodeJson(jsonFromExtension, envVars);
        } else {
            extension = new JSONObject();
        }

        final Object jsonObject = decodeJson(jsonFromFile, envVars);

        if (jsonObject instanceof JSONArray) {
            for(Map<String, Object> sample : fillMap((JSONArray) jsonObject, extension)) {
                sendToFluentd(fluentLogger, tag, sample, startTimeInMillis);
            }
        } else {
            final Map<String, Object> data = fillMap((JSONObject) jsonObject, extension);
            sendToFluentd(fluentLogger, tag, data, startTimeInMillis);
        }
    }

    private static Object decodeJson(String originalJson, Map<String, String> envVars)  {
        final String decodedJson = Util.replaceMacro(originalJson, envVars);
        try {
            return new JSONParser().parse(decodedJson);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to convert string: " + decodedJson + " to JSON", e);
        }
    }

    private static void sendToFluentd(FluentLogger logger, String tag, Map<String, Object> data, long timestamp) {
        data.put("timestamp", timestamp);

        logger.log(tag, data);
        LOGGER.info("Successfully send to Fluentd. Tag: " + tag + ". Data: " + data);
    }
}
