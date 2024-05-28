package service;

import chess.ChessGame;
import dataaccess.DAO.memoryDAO.memoryAuthDataAccessObject;
import dataaccess.DAO.memoryDAO.memoryGameDataAccessObject;
import model.GameData;
import request.JoinGameRequest;
import responses.JoinGameResponse;

public class JoinGameService {
    private static JoinGameService singleInstance = null;

    private JoinGameService() {
    }

    public static JoinGameService getInstance() {
        if (singleInstance == null) {
            singleInstance = new JoinGameService();
        }
        return singleInstance;
    }

    public JoinGameResponse joinGame(String authToken, JoinGameRequest joinGameRequest) {
        if (!memoryAuthDataAccessObject.getInstance().getAuth(authToken)) {
            return new JoinGameResponse("Error: unauthorized");
        }
        else {
            int gameId = joinGameRequest.gameId();
            ChessGame.TeamColor color = joinGameRequest.color();
            String username = memoryAuthDataAccessObject.getInstance().getUsername(authToken);
            if (!memoryGameDataAccessObject.getInstance().gameExists(gameId)) {
                return new JoinGameResponse("Error: bad request");
            }
            GameData gameData = memoryGameDataAccessObject.getInstance().getGame(gameId);
            if (color == ChessGame.TeamColor.WHITE && gameData.whiteUsername() == null) {
                GameData newGameData = new GameData(gameId, username, gameData.blackUsername(), gameData.gameName(), gameData.game());
                memoryGameDataAccessObject.getInstance().updateGame(gameId, newGameData);
            }
            else if (color == ChessGame.TeamColor.BLACK && gameData.blackUsername() == null) {
                GameData newGameData = new GameData(gameId, gameData.whiteUsername(), username, gameData.gameName(), gameData.game());
                memoryGameDataAccessObject.getInstance().updateGame(gameId, newGameData);
            }
            else {
                return new JoinGameResponse("Error: already taken");
            }
            return new JoinGameResponse(null);
        }
    }
}