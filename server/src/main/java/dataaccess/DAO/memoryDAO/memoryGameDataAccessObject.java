package dataaccess.DAO.memoryDAO;

import chess.ChessGame;
import dataaccess.DAO.GameDataAccessObject;
import model.GameData;
import java.util.ArrayList;


public class memoryGameDataAccessObject implements GameDataAccessObject {
    private static memoryGameDataAccessObject singleInstance = null;
    private static final ArrayList<GameData> games = new ArrayList<>();

    private memoryGameDataAccessObject(){
    }

    public static memoryGameDataAccessObject getInstance(){
        if (singleInstance == null){
            singleInstance = new memoryGameDataAccessObject();
        }
        return singleInstance;
    }

    public boolean gameExists(int gameId){
        int gameIndex = gameId-1;
        return (0 <= gameIndex && gameIndex <games.size());
    }

    public GameData getGame(int gameId){
        return games.get(gameId-1);
    }

    public int makeGame(String gameName){
        int gameId = games.size() + 1;
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData(gameId,null,null,gameName,chessGame);
        games.add(gameData);
        return gameId;
    }

    public void updateGame(int gameId, GameData updatedGame){
        games.set(gameId-1,updatedGame);
    }

    public void clear(){
        games.clear();
    }

    public ArrayList<GameData> listGames(){
        return games;
    }
}
