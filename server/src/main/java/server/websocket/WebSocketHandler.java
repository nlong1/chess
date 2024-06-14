package server.websocket;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.dao.sqldao.DataBaseAuthDataAccessObject;
import dataaccess.dao.sqldao.DataBaseGameDataAccessObject;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import responses.ListGamesResponse;
import server.Server;
import websocket.commands.*;
import websocket.messages.ServerError;
import websocket.messages.ServerLoadGame;
import websocket.messages.ServerNotification;

import java.io.IOException;
import java.util.List;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final DataBaseAuthDataAccessObject dataBaseAuthDataAccessObject = new DataBaseAuthDataAccessObject();

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
        try {
            if (dataBaseAuthDataAccessObject.getAuth(connectCommand.getAuthString())) {
                if (new DataBaseGameDataAccessObject().gameExists(connectCommand.getGameID())){
                connections.add(username, session, connectCommand.getGameID());

                String color;
                if (connectCommand.getColor() == ChessGame.TeamColor.WHITE) {
                    color = "white";
                } else if (connectCommand.getColor() == ChessGame.TeamColor.BLACK) {
                    color = "black";
                } else {
                    color = "observer";
                }
                var message = String.format("        %s has joined the game as %s", username, color);
                ServerNotification notification = new ServerNotification(message);
                ServerLoadGame serverLoadGame = new ServerLoadGame(true, connectCommand.getGameID(), connectCommand.getColor());
                connections.send(username, serverLoadGame, connectCommand.getGameID());
                connections.broadcast(username, notification, connectCommand.getGameID());
            }
                else{
                    throw new DataAccessException("        bad game ID");
                }
            }
            else{
                throw new DataAccessException("        unauthorized");
            }
        }
        catch (DataAccessException e){
            var serverError = new ServerError(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(serverError));
        }
    }

    private void makeMove(String username,Session session, MakeMoveCommand makeMoveCommand) throws IOException {
        try {
            if (dataBaseAuthDataAccessObject.getAuth(makeMoveCommand.getAuthString())) {
                GameData gameData = new DataBaseGameDataAccessObject().getGame(makeMoveCommand.getGameID());
                ChessGame updatedGame = gameData.game();
                updatedGame.makeMove(makeMoveCommand.getChessMove());
                GameData updatedGameData = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), updatedGame);
                new DataBaseGameDataAccessObject().updateGame(makeMoveCommand.getGameID(), updatedGameData);
            }
            else{
                throw new DataAccessException("        unauthorized");
            }
            }
        catch (DataAccessException e){
            var serverError = new ServerError(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(serverError));
            return;
        }
        catch (InvalidMoveException e){
            ServerError serverError = new ServerError(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(serverError));
        }
        catch (Exception _){
            System.out.println("Exception caught");
            return;
        }
        var message = String.format("        %s made a move",username);
        var notification = new ServerNotification(message);
        connections.broadcast(username, notification, makeMoveCommand.getGameID());
        var loadGame = new ServerLoadGame(true,makeMoveCommand.getGameID(),makeMoveCommand.getColor());
        ServerLoadGame serverLoadGame = new ServerLoadGame(true, makeMoveCommand.getGameID(), makeMoveCommand.getColor());
        session.getRemote().sendString(new Gson().toJson(serverLoadGame));
        connections.broadcast(username,serverLoadGame, makeMoveCommand.getGameID());
    }

    private void leaveGame(Session session, String username, LeaveGameCommand leaveGameCommand) throws IOException {
        try {
            if (dataBaseAuthDataAccessObject.getAuth(leaveGameCommand.getAuthString())) {
                connections.remove(leaveGameCommand.getGameID(), username, leaveGameCommand.getColor());
                var message = String.format("        %s has left the game", username);
                var notification = new ServerNotification(message);
                connections.broadcast(username, notification, leaveGameCommand.getGameID());
            }
            else{
                throw new DataAccessException("        unauthorized");
            }
        }
        catch (DataAccessException e){
            var serverError = new ServerError(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(serverError));
        }
    }

    private void resign(Session session, String username, ResignCommand resignCommand) throws IOException {
        try {
            if (dataBaseAuthDataAccessObject.getAuth(resignCommand.getAuthString())) {
                var message = String.format("        %s has forfeit", username);
                var notification = new ServerNotification(message);
                connections.broadcast(username, notification, resignCommand.getGameID());
                connections.deleteGame(resignCommand.getGameID());
            } else {
                throw new DataAccessException("        unauthorized");
            }
        }
        catch (DataAccessException e){
            var serverError = new ServerError(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(serverError));
        }
    }

}