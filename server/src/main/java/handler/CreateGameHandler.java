package handler;

import com.google.gson.Gson;
import request.CreateGameRequest;
import responses.CreateGameResponse;
import service.CreateGameService;
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
        String authToken = req.headers("authorization");
        CreateGameRequest createGameRequest = toRequest(req,CreateGameRequest.class);
        CreateGameResponse createGameResponse = CreateGameService.getInstance().createGame(authToken,createGameRequest);

        return responseUpdate(res,createGameResponse,createGameResponse.message());
    }

}
