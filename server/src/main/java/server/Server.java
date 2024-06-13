package server;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import handler.ListGamesHandler;
import handler.RegisterHandler;
import handler.JoinGameHandler;
import handler.CreateGameHandler;
import handler.ClearApplicationHandler;
import handler.LoginHandler;
import handler.LogoutHandler;
import server.websocket.WebSocketHandler;
import spark.*;


public class Server {
    private static final WebSocketHandler webSocketHandler = new WebSocketHandler();

    public static void main(String[] args){
        int port = 8080;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        else{
            run(port);
        }
    }

    public static int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.webSocket("/ws", webSocketHandler);

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (req, res) -> RegisterHandler.getInstance().handleRequest(req, res));
        Spark.post("/session", (req, res) -> LoginHandler.getInstance().handleRequest(req, res));
        Spark.delete("/session", (req, res) -> LogoutHandler.getInstance().handleRequest(req, res));
        Spark.get("/game", (req, res) -> ListGamesHandler.getInstance().handleRequest(req, res));
        Spark.post("/game", (req, res) -> CreateGameHandler.getInstance().handleRequest(req, res));
        Spark.put("/game",(req, res) -> JoinGameHandler.getInstance().handleRequest(req, res));
        Spark.delete("/db", (req,res) -> ClearApplicationHandler.getInstance().handleRequest(req, res));
        try {
            DatabaseManager.createDatabase();
        }
        catch (DataAccessException e){
            System.out.println("Database loading failed");
        }
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
