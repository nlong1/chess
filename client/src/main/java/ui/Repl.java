package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import ui.EscapeSequences;
import websocket.messages.ServerError;
import websocket.messages.ServerLoadGame;
import websocket.messages.ServerMessage;
import websocket.messages.ServerNotification;

import java.util.Scanner;

public class Repl implements NotificationHandler{
    private final Client client;
    private boolean loggedIn = false;
    private boolean inGame = false;

    public Repl(String serverUrl) {
        client = new Client(serverUrl,this);
    }

    public void run(){
        System.out.println(EscapeSequences.WHITE_QUEEN + "CHESS" + EscapeSequences.WHITE_QUEEN + "\ntype help to get started");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")){
            String line = scanner.nextLine();
            try{
                result = client.eval(line,loggedIn,inGame);
                if (result == "        logged in"){
                    loggedIn = true;
                }
                else if (result == "        logged out"){
                    loggedIn = false;
                }
                else if (result == "..."){
                    inGame = true;
                }
                else if (result == "        left game"){
                    inGame = false;
                }
                System.out.print(result);
            }
            catch (Throwable e){
                System.out.println(e.toString());
            }
            System.out.println();
        }
    }

    public void notify(String message) {
//        System.out.println("should've put a message to client");
        ServerMessage serverMessage = new Gson().fromJson(message,ServerMessage.class);
        if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION){
            ServerNotification serverNotification = new Gson().fromJson(message,ServerNotification.class);
            System.out.println(serverNotification.getMessage());
        }
        else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME){
            ServerLoadGame serverLoadGame = new Gson().fromJson(message, ServerLoadGame.class);
            if (serverLoadGame.hasGame()){
                Integer gameID = serverLoadGame.getGameID();
                ChessGame chessGame = null;
                try {
                    chessGame = client.getGame(gameID);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                if (chessGame == null){
                    System.out.println("bad");
                }
                new Printer().printBoard(chessGame.getBoard(), client.getColor());
            }
        }
        else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.ERROR){
            ServerError serverError = new Gson().fromJson(message,ServerError.class);
            System.out.println(serverError.getMessage());
        }
        else{
            System.out.println("this isn't a notification??");
        }
    }
}
