package service;

import dataaccess.DataAccessException;
import dataaccess.dao.AuthDataAccessObject;
import dataaccess.dao.GameDataAccessObject;
import dataaccess.dao.sqldao.DataBaseAuthDataAccessObject;
import dataaccess.dao.sqldao.DataBaseGameDataAccessObject;
import request.CreateGameRequest;
import responses.CreateGameResponse;

public class CreateGameService {
    private static CreateGameService singleInstance = null;
    private final AuthDataAccessObject auth = new DataBaseAuthDataAccessObject();
    private final GameDataAccessObject game = new DataBaseGameDataAccessObject();

    private CreateGameService(){
    }

    public static CreateGameService getInstance(){
        if (singleInstance == null){
            singleInstance = new CreateGameService();
        }
        return singleInstance;
    }
    public CreateGameResponse createGame(String authToken, CreateGameRequest createGameRequest){
        try {
            String gameName = createGameRequest.gameName();
            if (!auth.getAuth(authToken)) {
                return new CreateGameResponse(null, "Error: unauthorized");
            } else {
                Integer gameID = game.makeGame(gameName);
                return new CreateGameResponse(gameID, null);
            }
        }
        catch (DataAccessException e){
            return new CreateGameResponse(null,e.getMessage());
        }
    }
}
