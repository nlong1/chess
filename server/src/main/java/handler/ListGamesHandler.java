package handler;

import responses.ListGamesResponse;
import service.ListGamesService;
import spark.Request;
import spark.Response;

public class ListGamesHandler extends AbstractHandler{
    private static ListGamesHandler singleInstance = null;

    private ListGamesHandler(){
    }

    public static ListGamesHandler getInstance(){
        if (singleInstance == null){
            singleInstance = new ListGamesHandler();
        }
        return singleInstance;
    }

    @Override
    public String handleRequest(Request req, Response res){
        String authToken = req.headers("authorization");
        ListGamesResponse listGamesResponse = ListGamesService.getInstance().listGames(authToken);
        return responseUpdate(res,listGamesResponse,listGamesResponse.message());
    }
}
