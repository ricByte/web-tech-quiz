package services;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseFactory {
    public static HttpServletResponse createResponse(HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.setContentType("application/json");

        return response;

    }

    public static void sendResponse(HttpServletResponse response, Object value, String name) throws IOException {
        PrintWriter out = response.getWriter();
        out.write(GsonFactory.constructJson("OK", 200, value, name));
        out.flush();
    }
}
