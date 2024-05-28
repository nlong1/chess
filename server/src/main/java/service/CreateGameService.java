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
            Integer gameID = memoryGameDataAccessObject.getInstance().makeGame(gameName);
            System.out.println("\nreturned game Id after making game: ");
            System.out.println(gameID);
            System.out.println("\n");
            return new CreateGameResponse(gameID,null);
        }
    }
}
