package server.websocket;

import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.dao.sqldao.DataBaseAuthDataAccessObject;
import dataaccess.dao.sqldao.DataBaseGameDataAccessObject;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.*;
import websocket.messages.ServerNotification;

import java.io.IOException;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

            // Throws a custom UnauthorizedException. Yours may work differently.
            String username = new DataBaseAuthDataAccessObject().getUsername(command.getAuthString());

            switch (command.getCommandType()) {
                case CONNECT -> connect(username, session, (ConnectCommand) new Gson().fromJson(message, ConnectCommand.class));
                case MAKE_MOVE -> makeMove(username, session, (MakeMoveCommand) new Gson().fromJson(message, MakeMoveCommand.class));
                case LEAVE -> leaveGame(session, username, (LeaveGameCommand) new Gson().fromJson(message, LeaveGameCommand.class));
                case RESIGN -> resign(session, username, (ResignCommand) new Gson().fromJson(message, ResignCommand.class));
            }
        }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
//        } catch (UnauthorizedException ex) {
//            // Serializes and sends the error message
//            sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
//        }
    }





    private void connect(String username, Session session,ConnectCommand connectCommand) throws IOException {
        connections.add(username, session, connectCommand.getGameID());
        var message = String.format("%s has joined the game", username);
        ServerNotification notification = new ServerNotification(message);
        System.out.println("attempt to broadcast message");
        connections.broadcast(username, notification, connectCommand.getGameID());
    }

    private void makeMove(String username,Session session, MakeMoveCommand makeMoveCommand) throws IOException {
        try {
            GameData gameData = new DataBaseGameDataAccessObject().getGame(makeMoveCommand.getGameID());
            gameData.game().makeMove(makeMoveCommand.getChessMove());
        }
        catch (InvalidMoveException e){
            session.getRemote().sendString(e.getMessage());
        }
        catch (Exception _){
            return;
        }
        var message = String.format("%s made a move",username);
        var notification = new ServerNotification(message);
        connections.broadcast(username, notification, makeMoveCommand.getGameID());
    }

    private void leaveGame(Session session, String username, LeaveGameCommand leaveGameCommand) throws IOException {
        connections.remove(leaveGameCommand.getGameID(), username);
        var message = String.format("%s has left the game", username);
        var notification = new ServerNotification(message);
        connections.broadcast(username, notification, leaveGameCommand.getGameID());
    }

    private void resign(Session session, String username, ResignCommand resignCommand) throws IOException {
        var message = String.format("%s has forfeit", username);
        var notification = new ServerNotification(message);
        connections.broadcast(username,notification, resignCommand.getGameID());
        connections.deleteGame(resignCommand.getGameID());
    }

}