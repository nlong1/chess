package handler;

import com.google.gson.Gson;
import responses.JoinGameResponse;
import responses.LoginResponse;
import spark.Request;
import spark.Response;

public class LoginHandler extends AbstractHandler{
    private static LoginHandler singleInstance = null;

    private LoginHandler(){
    }

    public static LoginHandler getInstance(){
        if (singleInstance == null){
            singleInstance = new LoginHandler();
        }
        return singleInstance;
    }

    @Override
    public String handleRequest(Request req, Response res){
        return new Gson().toJson(new LoginResponse(null,null));
    }
}
