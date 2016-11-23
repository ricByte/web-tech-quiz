package services;


import com.google.gson.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class ParameterGetter {
    public static JsonObject handleRequest(HttpServletRequest request) throws IOException {

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.getReader());

        if (!jsonElement.isJsonNull()) {
            return jsonElement.getAsJsonObject();
        }

        return new JsonObject();
    }

    public static JsonArray serializeGet(String path) {

        path = path.substring(1, path.length());
        String[] QuestionsId = path.split("/");
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();

        String QuestionsIdGson = gson.toJson(QuestionsId);
        return parser.parse(QuestionsIdGson).getAsJsonArray();

    }
}
