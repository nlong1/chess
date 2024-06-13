package ui;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class Client {
    private final ServerFacade server;
    private final String serverUrl;
    private String authToken;
    private HashMap<Integer,Integer> gamesIdMap = new HashMap();
    private HashMap<Integer, ChessGame> gamesMap = new HashMap();
    private ui.WebSocketFacade ws;
    private final NotificationHandler notificationHandler;

    public Client(String serverUrl,NotificationHandler notificationHandler){
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
    }

    private String help(){
        return """
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> <PASSWORD>
                quit - playing chess
                help - list possible commands \n
        """;
    }

    private String login(String[] tokens) throws ResponseException {
        try {
            if (tokens.length == 3) {
                authToken = server.login(tokens);
                return "        logged in";
            }
            throw new ResponseException(500,"wrong length\n");
        }
        catch (Exception e) {
            throw new ResponseException(500, """
                            Invalid Login.\n        Login with login <USERNAME> <PASSWORD>
                    """);
        }
    }

    private String register(String[] tokens) throws ResponseException{
        try {
            if (tokens.length == 4) {
                authToken = server.register(tokens);
                return "        logged in";
            }
            throw new ResponseException(500,"wrong length\n");
        }
        catch (Exception e) {
            throw new ResponseException(500, """
                            Invalid Registration \n        Register with register <USERNAME> <PASSWORD> <EMAIL>\n
                    """);
        }
    }

    private String logout() throws ResponseException{
        try{
            return server.logout(authToken);
        }
        catch (Exception e) {
            throw new ResponseException(500,"""
                            Invalid Logout \n        
                    """);
        }
    }

    private String createGame(String[] tokens) throws ResponseException {
        try {
            if (tokens.length == 2) {
                return server.createGame(tokens,authToken);
            }
            throw new ResponseException(500,"wrong length\n");
        }
        catch (Exception e) {
            throw new ResponseException(500, """
                            Invalid Game Creation \n        Create game with: create <GAME NAME>\n
                    """);
        }
    }

    private String listGames() throws ResponseException {
        try{
            Collection<GameData> games = server.listGames(authToken);
            gamesIdMap = new HashMap<>();
            int number = 0;
            for (GameData game:games){
                number++;
                gamesIdMap.put(number,game.gameID());
                gamesMap.put(number,game.game());
                System.out.print("        Game ");
                System.out.print(number);
                System.out.println(": " + game.gameName() + "\n" + "        white player: " + game.whiteUsername() + ", black player: " + game.blackUsername() + "\n");
            }
            return "\n";

        }
        catch (Exception e) {
            throw new ResponseException(500,"""
                            List Games Error \n
                    """);
        }
    }

    private String playGame(String[] tokens) throws ResponseException {
        try {
            if (tokens.length == 3) {
                String response = server.joinGame(tokens[1], gamesIdMap.get(Integer.valueOf(tokens[2])),authToken);
                Printer printerWhite = new Printer();
                printerWhite.printBoard(gamesMap.get(Integer.valueOf(tokens[2])).getBoard(), ChessGame.TeamColor.WHITE);
                Printer printerBlack = new Printer();
                printerBlack.printBoard(gamesMap.get(Integer.valueOf(tokens[2])).getBoard(), ChessGame.TeamColor.BLACK);
                ws = new WebSocketFacade(serverUrl,notificationHandler);
                ws.join(authToken,gamesIdMap.get(Integer.valueOf(tokens[2])));
                return response;
            }
            throw new ResponseException(500,"wrong length\n");
        }
        catch (Exception e) {
            throw new ResponseException(500, """
                            Invalid Request to Play \n        Play game with: play <"WHITE/BLACK"> <GAME NUMBER> \n
                    """);
        }
    }

    private String observeGame(String[] tokens) throws ResponseException {
        try {
            if (tokens.length == 2) {
                Printer whitePrinter = new Printer();
                whitePrinter.printBoard(gamesMap.get(Integer.valueOf(tokens[1])).getBoard(), ChessGame.TeamColor.WHITE);
                Printer blackPrinter = new Printer();
                blackPrinter.printBoard(gamesMap.get(Integer.valueOf(tokens[1])).getBoard(), ChessGame.TeamColor.BLACK);
                return "observing game:" + tokens[1];
            }
            throw new ResponseException(500,"wrong length\n");
        }
        catch (Exception e) {
            throw new ResponseException(500, """
                            Invalid Observer \n        Observe game with: observe <GAME NUMBER>\n
                    """);
        }
    }

    private String loggedInHelp(){
        return """
                logout
                create <GAME NAME>
                list - lists all available games
                play <"WHITE/BLACK"> <GAME NUMBER>
                observe <GAME NUMBER> \n
        """;
    }

    public String eval(String input,boolean loggedIn){
        var tokens = input.toLowerCase().split(" ");
        try{
            if (!loggedIn) {
                switch (tokens[0]) {
                    case "quit":
                        return "quit";
                    case "login":
                        return login(tokens);
                    case "register":
                        return register(tokens);
                    default:
                        return help();

                }
            }
            else{
                switch (tokens[0]) {
                    case "logout":
                        return logout();
                    case "create":
                        return createGame(tokens);
                    case "list":
                        return listGames();
                    case "play":
                        return playGame(tokens);
                    case "observe":
                        return observeGame(tokens);
                    default:
                        return loggedInHelp();

                }
            }
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

}