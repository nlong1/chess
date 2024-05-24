package handler;

import com.google.gson.Gson;
import request.RegisterRequest;
import responses.RegisterResponse;
import spark.Request;
import spark.Response;

public class RegisterHandler extends AbstractHandler{
    private static RegisterHandler singleInstance = null;

    private RegisterHandler(){
    }

    public static RegisterHandler getInstance(){
        if (singleInstance == null){
            singleInstance = new RegisterHandler();
        }
        return singleInstance;
    }

    public String handleRegisterRequest(Request req, Response res){
        String json = new Gson().toJson(req.body());
        RegisterRequest registerRequest = new Gson().fromJson(json,RegisterRequest.class);

        return null;
    }

}
