package dataaccess.dao.memorydao;

import chess.ChessGame;
import dataaccess.dao.GameDataAccessObject;
import model.GameData;
import java.util.ArrayList;


public class MemoryGameDataAccessObject implements GameDataAccessObject {
    private static MemoryGameDataAccessObject singleInstance = null;
    private static final ArrayList<GameData> Games = new ArrayList<>();

    private MemoryGameDataAccessObject(){
    }

    public static MemoryGameDataAccessObject getInstance(){
        if (singleInstance == null){
            singleInstance = new MemoryGameDataAccessObject();
        }
        return singleInstance;
    }

    public boolean gameExists(int gameId){
        int gameIndex = gameId-1;
        if (Games.isEmpty()){
            return false;
        }
        return (0 <= gameIndex && gameIndex <Games.size());
    }

    public GameData getGame(int gameId){
        return Games.get(gameId-1);
    }

    public int makeGame(String gameName){
        int gameId = Games.size() + 1;
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData(gameId,null,null,gameName,chessGame);
        Games.add(gameData);
        return gameId;
    }

    public void updateGame(int gameId, GameData updatedGame){
        Games.set(gameId-1,updatedGame);
    }

    public void clear(){
        Games.clear();
    }

    public ArrayList<GameData> listGames(){
        return Games;
    }
}
