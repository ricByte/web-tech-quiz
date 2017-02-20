package services;

import beans.GsonResponse;
import com.google.gson.*;
import services.utils.DateParser;


import java.lang.reflect.Type;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class GsonFactory {

    public static String constructJson(String status, int error, Object objectToConvert, String property) {

        Gson GsonParser = getGsonBuilder();

        Map objectMap = new HashMap();

        objectMap.put(property, objectToConvert);

        GsonResponse response = new GsonResponse(status, error, objectMap);
        return GsonParser.toJson(response);
    }

    public static String constructJson(Object objectToConvert) {

        Gson GsonParser = getGsonBuilder();

        return GsonParser.toJson(objectToConvert);
    }

    public static String getJsonValue(JsonObject jsonObj, String property) {

        if (jsonObj.get(property) != null) {
            return jsonObj.get(property).getAsString();
        }

        return null;
    }

    private static Gson getGsonBuilder() {
        return new GsonBuilder()
                .registerTypeAdapter(GregorianCalendar.class, new JsonSerializer<GregorianCalendar>() {
                    public JsonElement serialize(GregorianCalendar date, Type type, JsonSerializationContext context) {
                        return new JsonPrimitive(DateParser.parseDate(date));
                    }
                }).create();
    }
}
