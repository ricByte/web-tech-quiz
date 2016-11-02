package services;


import com.google.gson.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class ParameterGetter {
    public static JsonObject handleRequest(HttpServletRequest request) throws IOException {

        JsonParser parser = new JsonParser();
//        JsonElement jelement = new JsonParser().parse(jsonLine);
//        return (JsonObject) parser.parse(request.getReader());
        JsonElement jsonElement = parser.parse(request.getReader());
        return jsonElement.getAsJsonObject();
    }

}
