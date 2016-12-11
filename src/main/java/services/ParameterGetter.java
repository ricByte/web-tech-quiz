package services;


import com.google.gson.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;


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

    public static Date getDateFromDateTime(ResultSet resultSet, String property) {

        try {

            Timestamp timestamp = resultSet.getTimestamp(property);
            if (timestamp != null) {
                return new Date(timestamp.getTime());
            }

        } catch (Exception e) {
        }

        return null;
    }
}
