package service;

import dataaccess.dao.memorydao.MemoryAuthDataAccessObject;
import dataaccess.dao.memorydao.MemoryGameDataAccessObject;
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
            return new CreateGameResponse(null,"Error: unauthorized");
        }
        else {
            Integer gameID = MemoryGameDataAccessObject.getInstance().makeGame(gameName);
            System.out.println("\nreturned game Id after making game: ");
            System.out.println(gameID);
            System.out.println("\n");
            return new CreateGameResponse(gameID,null);
        }
    }
}
