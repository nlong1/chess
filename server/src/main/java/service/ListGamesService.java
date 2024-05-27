package service;

import dataaccess.DAO.MemoryDAO.MemoryAuthDataAccessObject;
import dataaccess.DAO.MemoryDAO.MemoryGameDataAccessObject;
import model.GameData;
import responses.JoinGameResponse;
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
        if (!MemoryAuthDataAccessObject.getInstance().getAuth(authToken)){
            listGamesResponse = new ListGamesResponse(null,"Error: unauthorized");
        }
        else{
            List<GameData> games = MemoryGameDataAccessObject.getInstance().listGames();
            listGamesResponse = new ListGamesResponse(games,null);
        }
        return listGamesResponse;
    }
}
