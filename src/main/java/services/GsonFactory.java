package services;

import beans.GsonResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.util.HashMap;
import java.util.Map;

public class GsonFactory {
    public static String constructJson(String status, int error, Object objectToConvert, String property) {
        Gson GsonParser = new Gson();
        Map objectMap = new HashMap();

        objectMap.put(property, objectToConvert);

        GsonResponse response = new GsonResponse(status, error, objectMap);
        return GsonParser.toJson(response);
    }

    public static String getJsonValue(JsonObject jsonObj, String property) {

        if (jsonObj.get(property) != null) {
            return jsonObj.get(property).getAsString();
        }

        return null;
    }
}
