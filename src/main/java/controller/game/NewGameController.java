package controller.game;


import com.google.gson.Gson;
import model.reader.game.NewGameModel;
import model.response.game.GameResponse;
import services.ResponseFactory;
import services.game.NewGameService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NewGameController", urlPatterns = {"/game/new"})
public class NewGameController extends HttpServlet {

    public NewGameController(){
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        Gson gson = new Gson();
        NewGameModel newGameModel;
        GameResponse newGame = null;

        try {
            newGameModel = gson.fromJson(request.getReader(), NewGameModel.class);
        } catch (Exception e) {
            newGameModel = null;
        }

        if (newGameModel != null) {
            newGame = NewGameService.createNewGame(newGameModel);
        }

        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
        ResponseFactory.sendResponse(responseHeader, newGame);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
        ResponseFactory.sendResponse(responseHeader, false, "loginStatus", 403);
    }
}
