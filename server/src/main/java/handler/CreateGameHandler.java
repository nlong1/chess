package handler;

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

    public String handleCreateGameRequest(Request req, Response res){
        return null;
    }
}
