package ui;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import ui.NotificationHandler;
import ui.ResponseException;
import websocket.commands.ConnectCommand;
import websocket.commands.LeaveGameCommand;
import websocket.commands.MakeMoveCommand;
import websocket.commands.ResignCommand;
import websocket.messages.ServerMessage;
import websocket.messages.ServerNotification;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    NotificationHandler notificationHandler;


    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
//                    System.out.println("received a message from Server");
                    notificationHandler.notify(message);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void join(String authToken,Integer gameID,ChessGame.TeamColor color) throws ResponseException {
        try {
            var command = new ConnectCommand(authToken,gameID,color);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex) {
            System.out.println("        couldn't send connect command");
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void leave(String authToken,Integer gameID,ChessGame.TeamColor color) throws ResponseException {
        try {
            var command = new LeaveGameCommand(authToken,gameID,color);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException e) {
            System.out.println("        couldn't leave game");
            throw new ResponseException(500,e.getMessage());
        }
    }

    public void resign(String authToken,Integer gameID) throws ResponseException {
        try {
            var command = new ResignCommand(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        }
        catch (IOException e){
            System.out.println("IOException");
            throw new ResponseException(500,e.getMessage());
        }
    }

    public void makeMove(String authToken, Integer gameID , ChessMove chessMove,ChessGame.TeamColor color) throws ResponseException {
        try {
            var command = new MakeMoveCommand(authToken,gameID,chessMove);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException e) {
            System.out.println("        invalid move");
            throw new ResponseException(500,e.getMessage());
        }
    }

}