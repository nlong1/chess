package handler;

import com.google.gson.Gson;
import request.RegisterRequest;
import responses.RegisterResponse;
import service.RegistrationService;
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
        RegisterRequest registerRequest = new Gson().fromJson(req.body(),RegisterRequest.class);
        RegisterResponse registerResponse = RegistrationService.getInstance().register(registerRequest);
        return new Gson().toJson(registerResponse);
    }

}
