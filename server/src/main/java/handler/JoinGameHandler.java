package handler;

import com.google.gson.Gson;
import responses.JoinGameResponse;
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

    @Override
    public String handleRequest(Request req, Response res){
        String authToken = req.headers("authorization");
        System.out.println(authToken);
        return new Gson().toJson(new JoinGameResponse(null));
    }
}
