package manager.game;

import beans.User;
import beans.game.Game;
import beans.login.Session;
import services.game.GameService;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static Connection conn;

    public GameManager(Connection connection) {
        this.conn = connection;
    }

    public static Game insertNewGame(Session session) {

        String sql = "insert into game (session_NUMBER, points) " +
                "values (?, ?)";

        Game newGame = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, session.getSession());
            stmt.setInt(2, 0);

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                List<String> ids = new ArrayList<String>();
                ids.add(String.valueOf(id));
                newGame = getGameById(ids);
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newGame;

    }

    private static Game getGameById(List<String> ids) {

        String joined = StringUtils.join(ids, ",");

        String sql = "SELECT * " +
                "FROM game as g " +
                "WHERE g.id in ("+joined+")";

        Game newGame = null;


        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                newGame = GameService.createGame(rs);
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newGame;

    }

}
