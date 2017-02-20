package controller.game;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import model.reader.game.GameGetModel;
import model.response.game.GameResponse;
import services.ParameterGetter;
import services.ResponseFactory;
import services.game.GameService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GameController", urlPatterns = {"/game/get/*"})
public class GameController extends HttpServlet {

    public GameController(){
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        Gson gson = new Gson();
        GameGetModel gameGetModel;

        try {
            gameGetModel = gson.fromJson(request.getReader(), GameGetModel.class);
        } catch (Exception e) {
            gameGetModel = null;
        }

        controllerBody(response, gameGetModel);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        JsonArray gameId = ParameterGetter.serializeGet(pathInfo);

        GameGetModel gameGetModel = new GameGetModel();
        gameGetModel.setGameId(gameId.get(0).getAsInt());
        gameGetModel.setSession(gameId.get(1).getAsString());

        controllerBody(response, gameGetModel);

    }

    private void controllerBody(HttpServletResponse response, GameGetModel gameGetModel) {

        GameResponse responseObject = new GameResponse();

        if (gameGetModel != null) {
            responseObject = GameService.getGame(gameGetModel);
        }

        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
        ResponseFactory.sendResponse(responseHeader, responseObject);
    }
}
