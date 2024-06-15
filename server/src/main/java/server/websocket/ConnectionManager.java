package server.websocket;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.dao.GameDataAccessObject;
import dataaccess.dao.sqldao.DataBaseGameDataAccessObject;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;
import websocket.messages.ServerNotification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionManager {
    public final HashMap<Integer, HashMap<String,Connection>> connections = new HashMap<>();

    public void add(String username, Session session,Integer gameID) {
        var connection = new Connection(username, session);
        HashMap<String, Connection> userConnection;
        if (connections.get(gameID) != null){
            connections.get(gameID).put(username,connection);
        }
        else {
             userConnection = new HashMap<>();
            userConnection.put(username,connection);
            connections.put(gameID,userConnection);
        }
    }

    public void remove(Integer gameID,String username,ChessGame.TeamColor color) throws DataAccessException {
        connections.get(gameID).remove(username);
        GameData updatedGame;
        DataBaseGameDataAccessObject dataBaseGameDataAccessObject = new DataBaseGameDataAccessObject();
        GameData gameData = dataBaseGameDataAccessObject.getGame(gameID);
        if (color == ChessGame.TeamColor.BLACK){
            updatedGame = new GameData(gameID,gameData.whiteUsername(),null,gameData.gameName(),gameData.game());
            dataBaseGameDataAccessObject.updateGame(gameID,updatedGame);
        }
        else if (color == ChessGame.TeamColor.WHITE){
            updatedGame = new GameData(gameID,null, gameData.blackUsername(), gameData.gameName(),gameData.game());
            dataBaseGameDataAccessObject.updateGame(gameID,updatedGame);
        }
    }

//POSSIBLE CHANGE SERVERNOTIFICATION TO SERVERMESSAGE
    public void broadcast(String excludeVisitorName, ServerMessage serverMessage, Integer gameID) throws IOException {
        HashMap<String,Connection> gameConnections = connections.get(gameID);
        var removeList = new ArrayList<Connection>();
        for (var c : gameConnections.values()) {
            if (c.session.isOpen()) {
                if (!c.username.equals(excludeVisitorName)) {
                    c.send(serverMessage.toString());
                }
            } else {
                removeList.add(c);
            }
        }
        // Clean up any connections that were left open.
        for (var c : removeList) {
            gameConnections.remove(c.username);
        }
    }

    public void send(String username, ServerMessage serverMessage,Integer gameID) throws IOException{
        HashMap<String,Connection> gameConnections = connections.get(gameID);
        gameConnections.get(username).send(serverMessage.toString());
    }

}