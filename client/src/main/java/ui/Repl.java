package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import ui.EscapeSequences;
import websocket.messages.ServerLoadGame;
import websocket.messages.ServerMessage;
import websocket.messages.ServerNotification;

import java.util.Scanner;

public class Repl implements NotificationHandler{
    private final Client client;
    private boolean loggedIn = false;

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
                result = client.eval(line,loggedIn);
                if (result == "        logged in"){
                    loggedIn = true;
                }
                else if (result == "        logged out"){
                    loggedIn = false;
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
        System.out.println("should've put a message to client");
        ServerMessage serverMessage = new Gson().fromJson(message,ServerMessage.class);
        if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION){
            ServerNotification serverNotification = new Gson().fromJson(message,ServerNotification.class);
            System.out.println(serverNotification.getMessage());
        }
        if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME){
            ServerLoadGame serverLoadGame = new Gson().fromJson(message, ServerLoadGame.class);
            if (serverLoadGame.hasGame()){
                Integer gameID = serverLoadGame.getGameID();
                ChessGame chessGame = client.getGame(gameID);
                System.out.println("Printing Board...");;
                if (chessGame == null){
                    System.out.println("bad");
                }
                System.out.println(chessGame);
                new Printer().printBoard(chessGame.getBoard(), serverLoadGame.getColor());
            }
        }
        else{
            System.out.println("this isn't a notification??");
        }
    }
}
