package handler;

import service.LogoutService;
import spark.Request;
import spark.Response;
import responses.LogoutResponse;

import java.util.Set;

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

    @Override
    public String handleRequest(Request req, Response res){
        String authToken = req.headers("authorization");
        LogoutResponse logoutResponse;
        logoutResponse = LogoutService.getInstance().logout(authToken);
        return responseUpdate(res,logoutResponse,logoutResponse.message());
    }

}
