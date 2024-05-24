package handler;

import spark.Request;
import spark.Response;
import responses.LogoutResponse;
import com.google.gson.Gson;

public class LogoutHandler extends AbstractHandler{
    private static LogoutHandler singleInstance = null;

    private LogoutHandler(){
    }

    public static LogoutHandler getInstance(){
        if (singleInstance == null){
            singleInstance = new LogoutHandler();
        }
        return singleInstance;
    }

    public String handleLogoutRequest(Request req, Response res){
        return new Gson().toJson(new LogoutResponse(null));
    }
}
