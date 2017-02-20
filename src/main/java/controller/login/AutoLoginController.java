package controller.login;

import beans.login.loginObject;
import com.google.gson.Gson;
import model.reader.login.AutoLoginModel;
import services.ResponseFactory;
import services.login.userService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "AutoLoginController", urlPatterns = {"/autologin"})
public class AutoLoginController extends HttpServlet {

    public AutoLoginController() {
        super();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        AutoLoginModel autoLoginModel;

        try {
            autoLoginModel = gson.fromJson(request.getReader(), AutoLoginModel.class);
        } catch (Exception e) {
            autoLoginModel = null;
        }

        controllerBody(response, autoLoginModel);

    }


    private void controllerBody(HttpServletResponse response, AutoLoginModel autoLoginModel) {

        loginObject responseObject = new loginObject();

        if (autoLoginModel != null) {
            responseObject = userService.autoLogin(autoLoginModel.getSession());
        }

        controllerFooter(response, responseObject);
    }

    private void controllerFooter(HttpServletResponse response, Object responseObject) {
        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
        ResponseFactory.sendResponse(responseHeader, responseObject);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
