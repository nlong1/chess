package handler;

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

    public String handleListGamesRequest(Request req, Response res){
        return null;
    }
}
