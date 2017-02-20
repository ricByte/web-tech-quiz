package services;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static services.GsonFactory.constructJson;

public class ResponseFactory {
    public static HttpServletResponse createResponse(HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.setContentType("application/json");

        return response;

    }

    public static void sendResponse(HttpServletResponse response, Object value, String name) {

        try {
            PrintWriter out = response.getWriter();
            out.write(constructJson("OK", 200, value, name));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void sendResponse(HttpServletResponse response, Object value, String name, int status) throws IOException {

        PrintWriter out = response.getWriter();
        out.write(constructJson("OK", status, value, name));
        out.flush();

    }

    public static void sendResponse(HttpServletResponse response, Object value) {

        try {
            PrintWriter out = response.getWriter();
            out.write(constructJson(value));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
