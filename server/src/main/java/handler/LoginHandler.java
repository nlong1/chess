package handler;

import com.google.gson.Gson;
import request.LoginRequest;
import request.RegisterRequest;
import responses.JoinGameResponse;
import responses.LoginResponse;
import responses.RegisterResponse;
import service.LoginService;
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
        LoginRequest loginRequest = toRequest(req,LoginRequest.class);
        LoginResponse loginResponse;

        loginResponse = LoginService.getInstance().login(loginRequest);

        return responseUpdate(res,loginResponse,loginResponse.message());
    }
}
