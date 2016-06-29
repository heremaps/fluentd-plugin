package org.jenkinsci.plugins.fluentd;

import hudson.Util;
import org.fluentd.logger.FluentLogger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.logging.Logger;

import static org.jenkinsci.plugins.fluentd.JsonHelper.fillMap;

/**
 * Created by akbashev on 5/3/2016.
 */
public class FluentHelper {
    private static final Logger LOGGER = Logger.getLogger(Fluentd.class.getName());

    public static void sendJson(FluentLogger fluentLogger, String tag, Map<String, String> envVars, String jsonFromExtension,
                                String jsonFromFile, long startTimeInMillis) throws IllegalArgumentException {
        final JSONObject originalJson = decodeJson(jsonFromFile, envVars);
        final JSONObject extension;
        if (!jsonFromExtension.isEmpty()) {
            extension = decodeJson(jsonFromExtension, envVars);
        } else {
            extension = new JSONObject();
        }

        final Map<String, Object> data = fillMap(originalJson, extension);
        data.put("timestamp", startTimeInMillis);

        fluentLogger.log(tag, data);
        LOGGER.info("Successfully send to Fluentd. Tag: " + tag + ". Data: " + data);
    }

    private static JSONObject decodeJson(String originalJson, Map<String, String> envVars)  {
        final String decodedJson = Util.replaceMacro(originalJson, envVars);
        try {
            return (JSONObject) new JSONParser().parse(decodedJson);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to convert string: " + decodedJson + " to JSON", e);
        }
    }
}
