package ui;

import chess.*;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class Client {
    private final ServerFacade server;
    private final String serverUrl;
    private String authToken;
    private HashMap<Integer,Integer> gamesIdMap = new HashMap();
    private HashMap<Integer, ChessGame> gamesMap = new HashMap();
    private HashMap<Integer,Integer> reverseGamesIdMap = new HashMap();
    private ui.WebSocketFacade ws;
    private final NotificationHandler notificationHandler;
    private Integer gameNumber;
    private ChessGame.TeamColor color = null;

    public Client(String serverUrl,NotificationHandler notificationHandler){
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
    }

    public ChessGame.TeamColor getColor(){
        return Objects.requireNonNullElse(color, ChessGame.TeamColor.WHITE);
    }

    private void updateGames() throws ResponseException {
        Collection<GameData> games = server.listGames(authToken);
        gamesIdMap = new HashMap<>();
        reverseGamesIdMap = new HashMap<>();
        int number = 0;
        for (GameData game:games){
            number++;
            gamesIdMap.put(number,game.gameID());
            reverseGamesIdMap.put(game.gameID(),number);
            gamesMap.put(number, game.game());
        }
    }

    public ChessGame getGame(Integer gameID) throws ResponseException {
        updateGames();
        return gamesMap.get(reverseGamesIdMap.get(gameID));
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
            reverseGamesIdMap = new HashMap<>();
            int number = 0;
            for (GameData game:games){
                number++;
                gamesIdMap.put(number,game.gameID());
                reverseGamesIdMap.put(game.gameID(),number);
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
                gameNumber = Integer.valueOf(tokens[2]);
                if (Objects.equals(tokens[1], "white")){
                    color = ChessGame.TeamColor.WHITE;
                }
                else{
                    color = ChessGame.TeamColor.BLACK;
                }
                    ws = new WebSocketFacade(serverUrl, notificationHandler);
                    ws.join(authToken, gamesIdMap.get(Integer.valueOf(tokens[2])), color);
                System.out.println(response);
                return "...";
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
                ws = new WebSocketFacade(serverUrl,notificationHandler);
                ws.join(authToken,gamesIdMap.get(Integer.valueOf(tokens[1])),null);
                gameNumber = Integer.valueOf(tokens[1]);
                System.out.println("        observing game:" + tokens[1]);
                return "...";
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

    private String gameHelp(){
        return """
                redraw - redraws chess board
                leave - exits game
                move <start position> <end position> <promotion piece> - makes a move on the board
                    eg: move <A7> <A6> <none>
                resign - the game ends
                highlight <start position> - highlights legal moves for piece \n
        """;
    }

    public String eval(String input,boolean loggedIn,boolean inGame){
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
            else if (!inGame){
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
            else{
                switch (tokens[0]) {
                    case "redraw":
                        return redraw();
                    case "leave":
                        return leave();
                    case "move":
                        return move(tokens);
                    case "resign":
                        return resign();
                    case "highlight":
                        return highlight(tokens);
                    default:
                        return gameHelp();

                }
            }
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    private String highlight(String[] tokens) throws ResponseException {
        if (tokens.length != 2){
            throw new ResponseException(500,"        Invalid Highlight Format: highlight <position>");
        }
        updateGames();
        ChessLetterConverter chessLetterConverter = new ChessLetterConverter();
        ChessPosition position = new ChessPosition(Integer.parseInt(String.valueOf(tokens[1].charAt(1))),chessLetterConverter.getNumber(tokens[1].charAt(0)));
        new Printer().highlightBoard(gamesMap.get(gameNumber),color,position);
        return "";
    }

    private String resign() {
        try {
            ws.resign(authToken,gamesIdMap.get(gameNumber));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    private String move(String[] tokens) {
        try {
            if (tokens.length != 4){
                throw new ResponseException(500,"        Invalid Move Format: move <start> <end> <piece>");
            }
            ChessMove chessMove = getChessMove(tokens);
            ws.makeMove(authToken,gamesIdMap.get(gameNumber),chessMove,color);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    private static ChessMove getChessMove(String[] tokens) throws InvalidMoveException {
        try {
            ChessLetterConverter chessLetterConverter = new ChessLetterConverter();
            ChessPosition startPosition = new ChessPosition(Integer.parseInt(String.valueOf(tokens[1].charAt(1))),chessLetterConverter.getNumber(tokens[1].charAt(0)));
            ChessPosition endPosition = new ChessPosition(Integer.parseInt(String.valueOf(tokens[2].charAt(1))),chessLetterConverter.getNumber(tokens[2].charAt(0)));
            ChessPiece.PieceType pieceType = chessLetterConverter.getPieceType(tokens[3]);
            return new ChessMove(startPosition, endPosition, pieceType);
        }
        catch (Exception e){
            throw new InvalidMoveException("        Invalid Move Creation");
        }
    }

    private String leave() throws ResponseException {
        ws.leave(authToken,gamesIdMap.get(gameNumber),color);
        return "        left game";
    }

    private String redraw() throws ResponseException {
        updateGames();
        new Printer().printBoard(gamesMap.get(gameNumber).getBoard(),color);
        return "";
    }

}