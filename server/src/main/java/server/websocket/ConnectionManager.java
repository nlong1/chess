package server.websocket;

import dataaccess.dao.sqldao.DataBaseGameDataAccessObject;
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

    public void remove(Integer gameID,String username) {
        connections.get(gameID).remove(username);
    }

    public void deleteGame(Integer gameID){
        connections.remove(gameID);
    }

//POSSIBLE CHANGE SERVERNOTIFICATION TO SERVERMESSAGE
    public void broadcast(String excludeVisitorName, ServerMessage serverMessage, Integer gameID) throws IOException {
        HashMap<String,Connection> gameConnections = connections.get(gameID);
        System.out.println(gameConnections);
        var removeList = new ArrayList<Connection>();
        for (var c : gameConnections.values()) {
            if (c.session.isOpen()) {
                if (!c.username.equals(excludeVisitorName)) {
                    System.out.println("should've sent a message");
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
}