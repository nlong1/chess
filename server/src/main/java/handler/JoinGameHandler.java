package handler;

import spark.Request;
import spark.Response;

public class JoinGameHandler extends AbstractHandler {
    private static JoinGameHandler singleInstance = null;

    private JoinGameHandler(){
    }

    public static JoinGameHandler getInstance(){
        if (singleInstance == null){
            singleInstance = new JoinGameHandler();
        }
        return singleInstance;
    }

    public String handleJoinGameRequest(Request req, Response res){
        return null;
    }
}
