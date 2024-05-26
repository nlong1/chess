package handler;

import com.google.gson.Gson;
import responses.ListGamesResponse;
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
        return new Gson().toJson(new ListGamesResponse(null));
    }
}
