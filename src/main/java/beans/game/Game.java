package beans.game;

public class Game {

    private int id;
    private String sessionNumber;
    private int points;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(String session_ID) {
        this.sessionNumber = session_ID;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
