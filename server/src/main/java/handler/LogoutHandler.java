package handler;

import com.google.gson.Gson;
import org.eclipse.jetty.util.log.Log;
import request.LogoutRequest;
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
//         System.out.println(req.headers("Host"));
        String authToken = "";
        LogoutResponse logoutResponse;
        if (authToken == null){
            logoutResponse = new LogoutResponse("Error: bad request");
        }
        else{
            logoutResponse = LogoutService.getInstance().logout(authToken);
        }
        return responseUpdate(res,logoutResponse,logoutResponse.message());
    }

}
