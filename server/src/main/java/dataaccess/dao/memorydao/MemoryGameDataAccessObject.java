package dataaccess.dao.memorydao;

import chess.ChessGame;
import dataaccess.dao.GameDataAccessObject;
import model.GameData;
import java.util.ArrayList;


public class MemoryGameDataAccessObject implements GameDataAccessObject {
    private static MemoryGameDataAccessObject singleInstance = null;
    private static final ArrayList<GameData> ARRAY_GAMES = new ArrayList<>();

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
        if (ARRAY_GAMES.isEmpty()){
            return false;
        }
        return (0 <= gameIndex && gameIndex <ARRAY_GAMES.size());
    }

    public GameData getGame(int gameId){
        return ARRAY_GAMES.get(gameId-1);
    }

    public int makeGame(String gameName){
        int gameId = ARRAY_GAMES.size() + 1;
        ChessGame chessGame = new ChessGame();
        GameData gameData = new GameData(gameId,null,null,gameName,chessGame);
        ARRAY_GAMES.add(gameData);
        return gameId;
    }

    public void updateGame(int gameId, GameData updatedGame){
        ARRAY_GAMES.set(gameId-1,updatedGame);
    }

    public void clear(){
        ARRAY_GAMES.clear();
    }

    public ArrayList<GameData> listGames(){
        return ARRAY_GAMES;
    }
}
