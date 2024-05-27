package dataaccess.DAO.MemoryDAO;

import chess.ChessGame;
import dataaccess.DAO.GameDataAccessObject;
import model.GameData;

import java.util.ArrayList;
import java.util.List;


public class MemoryGameDataAccessObject implements GameDataAccessObject {
    private static MemoryGameDataAccessObject singleInstance = null;
    private static ArrayList<GameData> games = new ArrayList<>();

    private MemoryGameDataAccessObject(){
    }

    public static MemoryGameDataAccessObject getInstance(){
        if (singleInstance == null){
            singleInstance = new MemoryGameDataAccessObject();
        }
        return singleInstance;
    }

    public boolean gameExists(int gameId){
        return (0<=gameId && gameId <games.size());
    }

    public GameData getGame(int gameId){
        return games.get(gameId);
    }

    public int makeGame(String gameName){
        int gameId = games.size();
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData(gameId,null,null,gameName,chessGame);
        games.add(gameData);
        return gameId;
    }

    public void updateGame(int gameId, GameData updatedGame){
        games.set(gameId,updatedGame);
    }

    public void clear(){
        games.clear();
    }

    public ArrayList<GameData> listGames(){
        return games;
    }
}
