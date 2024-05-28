package dataaccess.dao.memorydao;

import chess.ChessGame;
import dataaccess.dao.GameDataAccessObject;
import model.GameData;
import java.util.ArrayList;


public class MemoryGameDataAccessObject implements GameDataAccessObject {
    private static MemoryGameDataAccessObject singleInstance = null;
    private static final ArrayList<GameData> arrayGames = new ArrayList<>();

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
        if (arrayGames.isEmpty()){
            return false;
        }
        return (0 <= gameIndex && gameIndex <arrayGames.size());
    }

    public GameData getGame(int gameId){
        return arrayGames.get(gameId-1);
    }

    public int makeGame(String gameName){
        int gameId = arrayGames.size() + 1;
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData(gameId,null,null,gameName,chessGame);
        arrayGames.add(gameData);
        return gameId;
    }

    public void updateGame(int gameId, GameData updatedGame){
        arrayGames.set(gameId-1,updatedGame);
    }

    public void clear(){
        arrayGames.clear();
    }

    public ArrayList<GameData> listGames(){
        return arrayGames;
    }
}
