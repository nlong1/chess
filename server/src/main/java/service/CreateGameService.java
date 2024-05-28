package service;

import dataaccess.DAO.memoryDAO.memoryAuthDataAccessObject;
import dataaccess.DAO.memoryDAO.memoryGameDataAccessObject;
import request.CreateGameRequest;
import responses.CreateGameResponse;

public class CreateGameService {
    private static CreateGameService singleInstance = null;

    private CreateGameService(){
    }

    public static CreateGameService getInstance(){
        if (singleInstance == null){
            singleInstance = new CreateGameService();
        }
        return singleInstance;
    }
    public CreateGameResponse createGame(String authToken, CreateGameRequest createGameRequest){
        String gameName = createGameRequest.gameName();
        CreateGameResponse createGameResponse;
        if (!memoryAuthDataAccessObject.getInstance().getAuth(authToken)){
            return new CreateGameResponse(null,"Error: unauthorized");
        }
        else {
            int gameId = memoryGameDataAccessObject.getInstance().makeGame(gameName);
            return new CreateGameResponse(gameId,null);
        }
    }
}
