package service;

import dataaccess.DataAccessException;
import dataaccess.dao.AuthDataAccessObject;
import dataaccess.dao.GameDataAccessObject;
import dataaccess.dao.sqldao.DataBaseAuthDataAccessObject;
import dataaccess.dao.sqldao.DataBaseGameDataAccessObject;
import model.GameData;
import responses.ListGamesResponse;

import java.util.List;

public class ListGamesService {
    private static ListGamesService singleInstance = null;
    private final AuthDataAccessObject auth = new DataBaseAuthDataAccessObject();
    private final GameDataAccessObject game = new DataBaseGameDataAccessObject();

    private ListGamesService(){
    }

    public static ListGamesService getInstance(){
        if (singleInstance == null){
            singleInstance = new ListGamesService();
        }
        return singleInstance;
    }
    public ListGamesResponse listGames(String authToken) {
        try {
            ListGamesResponse listGamesResponse;
            if (!auth.getAuth(authToken)) {
                listGamesResponse = new ListGamesResponse(null, "Error: unauthorized");
            } else {
                List<GameData> games = game.listGames();
                listGamesResponse = new ListGamesResponse(games, null);
            }
            return listGamesResponse;
        }
        catch (DataAccessException e) {
            return new ListGamesResponse(null,e.getMessage());
        }
    }
}
