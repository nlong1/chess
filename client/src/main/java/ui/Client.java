package ui;

import server.ResponseException;
import server.ServerFacade;


public class Client {
    private final ServerFacade server;
    private final String serverUrl;
    private String authToken;

    public Client(String serverUrl){
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    private String help(){
        return """
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> <PASSWORD>
                quit - playing chess
                help - list possible commands\n
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
                return server.register(tokens);
            }
            throw new ResponseException(500,"wrong length\n");
        }
        catch (Exception e) {
            throw new ResponseException(500, """
                            Invalid Registration.\n        Register with register <USERNAME> <PASSWORD> <EMAIL>\n
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

    private String createGame(String[] tokens){
        return "";
    }

    private String listGames(){
        return "";
    }

    private String playGame(String[] tokens){
        return "";
    }

    private String observeGame(String[] tokens){
        return "";
    }

    private String loggedInHelp(){
        return """
                logout
                create game <GAME NAME>
                list - lists all available games
                play <"WHITE/BLACK"> <"gameID">
                observe <"gameID>
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
                    case "create game":
                        return createGame(tokens);
                    case "list games":
                        return listGames();
                    case "play game":
                        return playGame(tokens);
                    case "observe game":
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