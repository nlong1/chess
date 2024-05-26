package handler;

import request.LoginRequest;
import request.LogoutRequest;
import responses.JoinGameResponse;
import responses.LoginResponse;
import service.LoginService;
import service.LogoutService;
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

    @Override
    public String handleRequest(Request req, Response res){
        LogoutRequest logoutRequest = toRequest(req,LogoutRequest.class);
        LogoutResponse logoutResponse;

        if (logoutRequest == null || logoutRequest.authtoken() == null) {
            logoutResponse = new LogoutResponse("Error: unauthorized");
        }
        else{
            logoutResponse = LogoutService.getInstance().logout(logoutRequest);
        }

        return responseUpdate(res,logoutResponse,logoutResponse.message());
    }

}
