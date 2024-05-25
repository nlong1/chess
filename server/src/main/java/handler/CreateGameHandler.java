package handler;

import com.google.gson.Gson;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends AbstractHandler{
    private static CreateGameHandler singleInstance = null;

    private CreateGameHandler(){
    }

    public static CreateGameHandler getInstance(){
        if (singleInstance == null){
            singleInstance = new CreateGameHandler();
        }
        return singleInstance;
    }

    @Override
    public String handleRequest(Request req, Response res){
        return new Gson().toJson(new CreateGameResponse(null));
    }

}
