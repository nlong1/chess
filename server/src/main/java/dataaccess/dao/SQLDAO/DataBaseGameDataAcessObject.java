package dataaccess.dao.SQLDAO;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.dao.GameDataAccessObject;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseGameDataAcessObject implements GameDataAccessObject {

    public boolean gameExists(int gameId) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT gameID FROM game where gameID = ?")) {
                preparedStatement.setInt(1,gameId);
                var rs = preparedStatement.executeQuery();
                if (rs.next()){
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    public GameData getGame(int gameId) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT gameID,whiteUsername,blackUsername,gameName,game FROM game where gameID = ?")) {
                preparedStatement.setInt(1,gameId);
                var rs = preparedStatement.executeQuery();
                if (rs.next()){
                    int gameNum = rs.getInt(1);
                    String whiteUsername = rs.getString(2);
                    String blackUsername = rs.getString(3);
                    String gameName = rs.getString(4);
                    ChessGame game = new Gson().fromJson(rs.getString(5),ChessGame.class);

                    return new GameData(gameNum,whiteUsername,blackUsername,gameName,game);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public int makeGame(String gameName) throws DataAccessException {
        int ID = 0;
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO game (gameName,game) values (?,?)")) {
                ChessGame game = new ChessGame();
                String gameString = new Gson().toJson(game);
                preparedStatement.setString(1,gameName);
                preparedStatement.setString(2,gameString);
                preparedStatement.executeUpdate();
                try (var getIDStatement = conn.prepareStatement("SELECT LAST_INSERT_ID();")){
                    var rs = getIDStatement.executeQuery();
                    if (rs.next()){
                        ID = rs.getInt(1);
                    }
                    return ID;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void updateGame(int gameId, GameData updatedGame) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET whiteUsername = ?, blackUsername = ?, game = ? WHERE gameID = ?")) {
                preparedStatement.setString(1, updatedGame.whiteUsername());
                preparedStatement.setString(2, updatedGame.blackUsername());
                preparedStatement.setString(3, new Gson().toJson(updatedGame.game()));
                preparedStatement.setInt(4,gameId);
                preparedStatement.executeUpdate();
                }
            }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM game")) {
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            System.out.println("Error in deleting game table");
            System.out.println(e.getMessage());
            throw new DataAccessException(e.getMessage());
        }
    }

    public ArrayList<GameData> listGames() throws DataAccessException {
        ArrayList<GameData> games = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM GAME")) {
                var rs = preparedStatement.executeQuery();
                while (rs.next()){
                    int gameId = rs.getInt("gameID");
                    String whiteUsername = rs.getString("whiteUsername");
                    String blackUsername = rs.getString("blackUsername");
                    String gameName = rs.getString("gameName");
                    String gameString = rs.getString("game");
                    games.add(new GameData(gameId,whiteUsername,blackUsername,gameName,new Gson().fromJson(gameString, ChessGame.class)));
                }
                return games;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
