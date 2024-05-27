package handler;

import com.google.gson.Gson;
import request.CreateGameRequest;
import request.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import service.JoinGameService;
import spark.Request;
import spark.Response;

import java.util.Objects;

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
        JoinGameRequest joinGameRequest = toRequest(req,JoinGameRequest.class);
        JoinGameResponse joinGameResponse;
        if (!Objects.equals(joinGameRequest.color(), "WHITE") && !Objects.equals(joinGameRequest.color(), "BLACK")){
            joinGameResponse = new JoinGameResponse("Error: bad request");
        }
        else{
            joinGameResponse = JoinGameService.getInstance().joinGame(authToken,joinGameRequest);
        }
        return responseUpdate(res,joinGameResponse,joinGameResponse.message());
    }
}
