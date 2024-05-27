package service;

import chess.ChessGame;
import dataaccess.DAO.MemoryDAO.MemoryAuthDataAccessObject;
import dataaccess.DAO.MemoryDAO.MemoryGameDataAccessObject;
import model.GameData;
import request.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;

import java.util.Objects;

public class JoinGameService {
    private static JoinGameService singleInstance = null;

    private JoinGameService(){
    }

    public static JoinGameService getInstance(){
        if (singleInstance == null){
            singleInstance = new JoinGameService();
        }
        return singleInstance;
    }

    public JoinGameResponse joinGame(String authToken, JoinGameRequest joinGameRequest){
        JoinGameResponse joinGameResponse;
        if (!MemoryAuthDataAccessObject.getInstance().getAuth(authToken)){
            joinGameResponse = new JoinGameResponse("Error: unauthorized");
        }
        else{
            String username = MemoryAuthDataAccessObject.getInstance().getUsername(authToken);
            if (MemoryGameDataAccessObject.getInstance().gameExists(joinGameRequest.gameId())){
                GameData gameData = MemoryGameDataAccessObject.getInstance().getGame(joinGameRequest.gameId());
                if (Objects.equals(joinGameRequest.color(), "WHITE")){
                    if (gameData.whiteUsername() == null){
                        GameData updatedGame = new GameData(joinGameRequest.gameId(),username,gameData.blackUsername(),gameData.gameName(),gameData.game());
                        MemoryGameDataAccessObject.getInstance().updateGame(joinGameRequest.gameId(),updatedGame);
                        joinGameResponse = new JoinGameResponse(null);
                    }
                    else {
                        joinGameResponse = new JoinGameResponse("Error: already taken");
                    }
                }
                else if (Objects.equals(joinGameRequest.color(), "BLACK")){
                    if (gameData.blackUsername() == null){
                        GameData updatedGame = new GameData(joinGameRequest.gameId(), gameData.whiteUsername(), username,gameData.gameName(),gameData.game());
                        MemoryGameDataAccessObject.getInstance().updateGame(joinGameRequest.gameId(),updatedGame);
                        joinGameResponse = new JoinGameResponse(null);
                    }
                    else {
                        joinGameResponse = new JoinGameResponse("Error: already taken");
                    }
                }
                else{
                    joinGameResponse = new JoinGameResponse("Error: bad request");
                }
            }
            else{
                joinGameResponse = new JoinGameResponse("Error: bad request");
            }
        }
        return joinGameResponse;
    }
}
