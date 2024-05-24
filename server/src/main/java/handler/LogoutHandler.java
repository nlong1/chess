package handler;

import spark.Request;
import spark.Response;

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
        return null;
    }
}
