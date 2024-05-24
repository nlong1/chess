package server;

import handler.ListGamesHandler;
import handler.RegisterHandler;
import handler.JoinGameHandler;
import handler.CreateGameHandler;
import handler.ClearApplicationHandler;
import handler.LoginHandler;
import handler.LogoutHandler;
import spark.*;


public class Server {
    public static void main(String[] args){
        run(8080);
    }

    public static int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (req, res) -> RegisterHandler.getInstance().handleRegisterRequest(req, res));
        Spark.post("/session", (req, res) -> LoginHandler.getInstance().handleLoginRequest(req, res));
        Spark.delete("/session", (req, res) -> LogoutHandler.getInstance().handleLogoutRequest(req, res));
        Spark.get("/game", (req, res) -> ListGamesHandler.getInstance().handleListGamesRequest(req, res));
        Spark.post("/game", (req, res) -> CreateGameHandler.getInstance().handleCreateGameRequest(req, res));
        Spark.put("/game",(req, res) -> JoinGameHandler.getInstance().handleJoinGameRequest(req, res));
        Spark.delete("/db", (req,res) -> ClearApplicationHandler.getInstance().handleClearApplicationRequest(req, res));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
