package service;

import dataaccess.DAO.memoryDAO.memoryAuthDataAccessObject;
import dataaccess.DAO.memoryDAO.memoryGameDataAccessObject;
import model.GameData;
import responses.ListGamesResponse;

import java.util.List;

public class ListGamesService {
    private static ListGamesService singleInstance = null;

    private ListGamesService(){
    }

    public static ListGamesService getInstance(){
        if (singleInstance == null){
            singleInstance = new ListGamesService();
        }
        return singleInstance;
    }
    public ListGamesResponse listGames(String authToken){
        ListGamesResponse listGamesResponse;
        if (!memoryAuthDataAccessObject.getInstance().getAuth(authToken)){
            listGamesResponse = new ListGamesResponse(null,"Error: unauthorized");
        }
        else{
            List<GameData> games = memoryGameDataAccessObject.getInstance().listGames();
            listGamesResponse = new ListGamesResponse(games,null);
        }
        return listGamesResponse;
    }
}
