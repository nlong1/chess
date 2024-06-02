package dataaccess.dao;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.GameData;

import java.util.ArrayList;

public interface GameDataAccessObject {
    boolean gameExists(int gameId) throws DataAccessException;
    GameData getGame(int gameId) throws DataAccessException;
    int makeGame(String gameName) throws DataAccessException;

    void updateGame(int gameId, GameData updatedGame) throws DataAccessException;

    void clear() throws DataAccessException;

    ArrayList<GameData> listGames() throws DataAccessException;
}
