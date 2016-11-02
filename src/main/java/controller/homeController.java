package controller;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import services.GsonFactory;
import services.ParameterGetter;
import services.userAuthenticationService;

@WebServlet(name = "homeController", urlPatterns = {"/login"})
public class homeController extends HttpServlet {

    public homeController(){
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject requestData = ParameterGetter.handleRequest(request);
        Boolean loginStatus = false;
        String email = GsonFactory.getJsonValue(requestData, "email");
        String password = GsonFactory.getJsonValue(requestData, "password");
        if (email != null && password != null) {
            String Password = password.toString();
            String Email = email.toString();
            loginStatus = userAuthenticationService.authLogin(Email,Password);
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.getWriter();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        out.write(GsonFactory.constructJson("OK", 200, loginStatus, "loginStatus"));
        out.flush();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
