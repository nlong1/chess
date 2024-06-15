package server.websocket;

import chess.*;
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
import java.util.Objects;


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
        DataBaseGameDataAccessObject dataBaseGameDataAccessObject = new DataBaseGameDataAccessObject();
        ChessGame.TeamColor color = null;
        try {
            if (dataBaseGameDataAccessObject.getGame(makeMoveCommand.getGameID()).game().gameStatus != ChessGame.GameStatus.IN_PROGRESS){
                ServerError serverError = new ServerError("        game is over");
                session.getRemote().sendString(new Gson().toJson(serverError));
                return;
            }
            if (dataBaseAuthDataAccessObject.getAuth(makeMoveCommand.getAuthString())) {
                GameData gameData = dataBaseGameDataAccessObject.getGame(makeMoveCommand.getGameID());
                System.out.println(username);
                System.out.println(gameData.whiteUsername());
                System.out.println(gameData.blackUsername());
                if (Objects.equals(gameData.whiteUsername(), username)) {
                    System.out.println("white makes a move");
                    color = ChessGame.TeamColor.WHITE;
                    ChessGame updatedGame = gameData.game();
                    System.out.println("have updated game");
                    updatedGame.makeMove(makeMoveCommand.getChessMove());
                    System.out.println("made a move");
                    GameData updatedGameData = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), updatedGame);
                    System.out.println("made updated game data");
                    new DataBaseGameDataAccessObject().updateGame(makeMoveCommand.getGameID(), updatedGameData);
                    System.out.println("updated game");
                }
                else if (Objects.equals(gameData.blackUsername(), username)) {
                    System.out.println("black makes a move");
                    color = ChessGame.TeamColor.BLACK;
                    ChessGame updatedGame = gameData.game();
                    updatedGame.makeMove(makeMoveCommand.getChessMove());
                    GameData updatedGameData = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), updatedGame);
                    new DataBaseGameDataAccessObject().updateGame(makeMoveCommand.getGameID(), updatedGameData);
                }
                else{
                    throw new DataAccessException("        bad");
                }
            }
            else{
                throw new DataAccessException("        unauthorized");
            }
            }
        catch (DataAccessException e){
            System.out.println(e.getMessage());
            var serverError = new ServerError(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(serverError));
            return;
        }
        catch (InvalidMoveException e){
            System.out.println(e.getMessage());
            ServerError serverError = new ServerError(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(serverError));
            return;
        }
        catch (Exception e){
            System.out.println("Exception caught");
            ServerError serverError = new ServerError(e.getMessage());
            session.getRemote().sendString(new Gson().toJson(serverError));
            return;
        }
        var message = String.format("        %s made a move",username);
        var notification = new ServerNotification(message);
        connections.broadcast(username, notification, makeMoveCommand.getGameID());
        var loadGame = new ServerLoadGame(true,makeMoveCommand.getGameID(),color);
        ServerLoadGame serverLoadGame = new ServerLoadGame(true, makeMoveCommand.getGameID(), color);
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
                var message = String.format("        %s has resigned", username);
                var notification = new ServerNotification(message);
                connections.broadcast(username, notification, resignCommand.getGameID());
                GameData gameData = new DataBaseGameDataAccessObject().getGame(resignCommand.getGameID());
                ChessGame updatedGame = gameData.game();

                if (Objects.equals(gameData.whiteUsername(), username)) {
                    updatedGame.gameStatus = ChessGame.GameStatus.BLACK_WON;
                    new DataBaseGameDataAccessObject().updateGame(resignCommand.getGameID(), new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), updatedGame));
                    var winningMessage = String.format("        %s won!","black");
                    var winNotification = new ServerNotification(winningMessage);
                    connections.broadcast(username,winNotification, resignCommand.getGameID());
                }
                else if (Objects.equals(gameData.blackUsername(), username)){
                    updatedGame.gameStatus = ChessGame.GameStatus.WHITE_WON;
                    new DataBaseGameDataAccessObject().updateGame(resignCommand.getGameID(), new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), updatedGame));
                    var winningMessage = String.format("        %s won!","white");
                    var winNotification = new ServerNotification(winningMessage);
                    connections.broadcast(username,winNotification, resignCommand.getGameID());
                }
                else{
                    throw new DataAccessException("        unauthorized");
                }

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