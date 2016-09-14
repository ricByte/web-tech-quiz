package services;

import beans.GsonResponse;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class GsonFactory {
    public static String constructJson (String status, int error, Object objectToConvert, String property){
        Gson GsonParser = new Gson();
        Map objectMap = new HashMap();

        objectMap.put(property, objectToConvert);

        GsonResponse response =  new GsonResponse(status, error, objectMap);
        return GsonParser.toJson(response);
    }
}
