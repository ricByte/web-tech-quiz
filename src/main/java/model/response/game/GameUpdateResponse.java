package model.response.game;

import beans.User;
import beans.game.Game;

public class GameUpdateResponse {
    private Game game;
    private User user;
    private boolean response;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public static GameUpdateResponse getCorrectResponse(User user, Game game) {
        GameUpdateResponse gameUpdateResponse = new GameUpdateResponse();

        gameUpdateResponse.setGame(game);
        gameUpdateResponse.setUser(user);
        gameUpdateResponse.setResponse(true);

        return gameUpdateResponse;
    }

    public static GameUpdateResponse getFailedResponse(User user, Game game) {
        GameUpdateResponse gameUpdateResponse = new GameUpdateResponse();

        gameUpdateResponse.setGame(game);
        gameUpdateResponse.setUser(user);
        gameUpdateResponse.setResponse(false);

        return gameUpdateResponse;
    }

    public static GameUpdateResponse returnErrorGame() {
        GameUpdateResponse gameUpdateResponse = new GameUpdateResponse();
        gameUpdateResponse.setResponse(false);

        return gameUpdateResponse;
    }
}
