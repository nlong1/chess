package dataaccess.dao;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDataAccessObject {
    boolean gameExists(int gameId);
    GameData getGame(int gameId);
    int makeGame(String gameName);

    void updateGame(int gameId, GameData updatedGame);

    void clear();

    ArrayList<GameData> listGames();
}
