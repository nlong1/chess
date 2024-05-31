package service;

import chess.ChessGame;
import dataaccess.dao.memorydao.MemoryAuthDataAccessObject;
import dataaccess.dao.memorydao.MemoryGameDataAccessObject;
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
        if (!MemoryAuthDataAccessObject.getInstance().getAuth(authToken)) {
            return new JoinGameResponse("Error: unauthorized");
        }
        if (joinGameRequest.gameID() == null){
            return new JoinGameResponse("Error: bad request");
        }
        else {
            Integer gameID = joinGameRequest.gameID();
            ChessGame.TeamColor color = joinGameRequest.playerColor();
            String username = MemoryAuthDataAccessObject.getInstance().getUsername(authToken);
            if (!MemoryGameDataAccessObject.getInstance().gameExists(gameID)) {
                return new JoinGameResponse("Error: bad request");
            }
            GameData gameData = MemoryGameDataAccessObject.getInstance().getGame(gameID);
            if (color == null){
                return new JoinGameResponse("Error: bad request");
            }
            if (color == ChessGame.TeamColor.WHITE && gameData.whiteUsername() == null) {
                GameData newGameData = new GameData(gameID, username, gameData.blackUsername(), gameData.gameName(), gameData.game());
                MemoryGameDataAccessObject.getInstance().updateGame(gameID, newGameData);
            }
            else if (color == ChessGame.TeamColor.BLACK && gameData.blackUsername() == null) {
                GameData newGameData = new GameData(gameID, gameData.whiteUsername(), username, gameData.gameName(), gameData.game());
                MemoryGameDataAccessObject.getInstance().updateGame(gameID, newGameData);
            }
            else {
                return new JoinGameResponse("Error: already taken");
            }
            return new JoinGameResponse(null);
        }
    }
}