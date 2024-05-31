package dataaccess.dao.SQLDAO;

import dataaccess.dao.GameDataAccessObject;
import model.GameData;

import java.util.ArrayList;

public class DataBaseGameDataAcessObject implements GameDataAccessObject {
    @Override
    public boolean gameExists(int gameId) {
        return false;
    }

    @Override
    public GameData getGame(int gameId) {
        return null;
    }

    @Override
    public int makeGame(String gameName) {
        return 0;
    }

    @Override
    public void updateGame(int gameId, GameData updatedGame) {

    }

    @Override
    public void clear() {

    }

    @Override
    public ArrayList<GameData> listGames() {
        return null;
    }
}
