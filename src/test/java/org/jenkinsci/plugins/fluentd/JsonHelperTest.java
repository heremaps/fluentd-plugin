package org.jenkinsci.plugins.fluentd;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class JsonHelperTest {
    @Test
    public void fillMapByNulls() throws Exception {
        final JSONObject original = new JSONObject();
        final JSONObject extension = new JSONObject();
        final Map<String, Object> data = JsonHelper.fillMap(original, extension);

        assertTrue(data.isEmpty());
    }

    @Test
    public void fillMap() throws Exception {
        final JSONObject original = new JSONObject();
        original.put("key1", "value1");
        final JSONObject extension = new JSONObject();
        extension.put("key2", "value2");
        final Map<String, Object> data = JsonHelper.fillMap(original, extension);

        final HashMap<String, Object> expected = new HashMap<>();
        expected.put("key1", "value1");
        expected.put("key2", "value2");

        assertFalse(data.isEmpty());
        assertEquals(expected, data);
    }

    @Test
    public void fillMapExtenstionPriority() throws Exception {
        final JSONObject original = new JSONObject();
        original.put("key1", "value1");
        final JSONObject extension = new JSONObject();
        extension.put("key1", "value2");
        final Map<String, Object> data = JsonHelper.fillMap(original, extension);

        final HashMap<String, Object> expected = new HashMap<>();
        expected.put("key1", "value2");

        assertFalse(data.isEmpty());
        assertEquals(expected, data);
    }

    @Test
    public void fillMapEmptyOriginal() throws Exception {
        final JSONObject original = new JSONObject();
        final JSONObject extension = new JSONObject();
        extension.put("key3", "value3");
        final Map<String, Object> data = JsonHelper.fillMap(original, extension);

        final HashMap<String, Object> expected = new HashMap<>();
        expected.put("key3", "value3");

        assertFalse(data.isEmpty());
        assertEquals(expected, data);
    }

    @Test
    public void fillMapEmptyExtension() throws Exception {
        final JSONObject original = new JSONObject();
        original.put("key4", "value4");
        final JSONObject extension = new JSONObject();
        final Map<String, Object> data = JsonHelper.fillMap(original, extension);

        final HashMap<String, Object> expected = new HashMap<>();
        expected.put("key4", "value4");

        assertFalse(data.isEmpty());
        assertEquals(expected, data);
    }

    @Test
    public void fillMapByJsonArray() throws Exception {
        final JSONArray jsonArray = new JSONArray();
        final JSONObject firstElement = new JSONObject();
        firstElement.put("key5", "value5");
        final JSONObject secondElement = new JSONObject();
        secondElement.put("key6", "value6");
        jsonArray.add(firstElement);
        jsonArray.add(secondElement);
        final JSONObject extension = new JSONObject();
        extension.put("key7", "value7");

        final List<Map<String, Object>> data = JsonHelper.fillMap(jsonArray, extension);

        assertEquals(data.get(0).get("key5"), "value5");
        assertEquals(data.get(0).get("key7"), "value7");
        assertEquals(data.get(1).get("key6"), "value6");
        assertEquals(data.get(1).get("key7"), "value7");
    }
}