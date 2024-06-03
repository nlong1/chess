package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.dao.AuthDataAccessObject;
import dataaccess.dao.GameDataAccessObject;
import dataaccess.dao.sqldao.DataBaseAuthDataAccessObject;
import dataaccess.dao.sqldao.DataBaseGameDataAccessObject;
import model.GameData;
import request.JoinGameRequest;
import responses.JoinGameResponse;

public class JoinGameService {
    private static JoinGameService singleInstance = null;
    private final AuthDataAccessObject auth = new DataBaseAuthDataAccessObject();
    private final GameDataAccessObject game = new DataBaseGameDataAccessObject();

    private JoinGameService() {
    }

    public static JoinGameService getInstance() {
        if (singleInstance == null) {
            singleInstance = new JoinGameService();
        }
        return singleInstance;
    }

    public JoinGameResponse joinGame(String authToken, JoinGameRequest joinGameRequest) {
        try {
            if (!auth.getAuth(authToken)) {
                return new JoinGameResponse("Error: unauthorized");
            }
            if (joinGameRequest.gameID() == null) {
                return new JoinGameResponse("Error: bad request");
            } else {
                Integer gameID = joinGameRequest.gameID();
                ChessGame.TeamColor color = joinGameRequest.playerColor();
                String username = auth.getUsername(authToken);
                if (!game.gameExists(gameID)) {
                    return new JoinGameResponse("Error: bad request");
                }
                GameData gameData = game.getGame(gameID);
                if (color == null) {
                    return new JoinGameResponse("Error: bad request");
                }
                if (color == ChessGame.TeamColor.WHITE && gameData.whiteUsername() == null) {
                    GameData newGameData = new GameData(gameID, username, gameData.blackUsername(), gameData.gameName(), gameData.game());
                    game.updateGame(gameID, newGameData);
                } else if (color == ChessGame.TeamColor.BLACK && gameData.blackUsername() == null) {
                    GameData newGameData = new GameData(gameID, gameData.whiteUsername(), username, gameData.gameName(), gameData.game());
                    game.updateGame(gameID, newGameData);
                } else {
                    return new JoinGameResponse("Error: already taken");
                }
                return new JoinGameResponse(null);
            }
        }
        catch (DataAccessException e){
            return new JoinGameResponse(e.getMessage());
        }
    }
}