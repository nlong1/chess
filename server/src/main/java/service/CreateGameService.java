package service;

import dataaccess.DAO.MemoryDAO.MemoryAuthDataAccessObject;
import dataaccess.DAO.MemoryDAO.MemoryGameDataAccessObject;
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
        if (!MemoryAuthDataAccessObject.getInstance().getAuth(authToken)){
            createGameResponse = new CreateGameResponse(null,"Error: unauthorized");
        }
        else {
            int gameId = MemoryGameDataAccessObject.getInstance().makeGame(gameName);
            createGameResponse = new CreateGameResponse(gameId,null);
        }
        return createGameResponse;
    }
}
