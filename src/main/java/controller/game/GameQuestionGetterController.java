package controller.game;

import com.google.gson.Gson;
import model.reader.game.GameUpdateModel;
import model.response.game.GameUpdateResponse;
import services.ResponseFactory;
import services.game.GameService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GameQuestionGetterController", urlPatterns = {"/game/question/get/*"})
public class GameQuestionGetterController extends HttpServlet {

    public GameQuestionGetterController() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        Gson gson = new Gson();
        GameUpdateModel gameUpdateModel;

        try {
            gameUpdateModel = gson.fromJson(request.getReader(), GameUpdateModel.class);
        } catch (Exception e) {
            gameUpdateModel = null;
        }

        controllerBody(response, gameUpdateModel);

    }

    private void controllerBody(HttpServletResponse response, GameUpdateModel gameUpdateModel) {

        GameUpdateResponse responseObject = new GameUpdateResponse();

        if (gameUpdateModel != null) {
            responseObject = GameService.updateGame(gameUpdateModel);
        }

        controllerFooter(response, responseObject);
    }

    private void controllerFooter(HttpServletResponse response, Object responseObject) {
        HttpServletResponse responseHeader = ResponseFactory.createResponse(response);
        ResponseFactory.sendResponse(responseHeader, responseObject);
    }
}
