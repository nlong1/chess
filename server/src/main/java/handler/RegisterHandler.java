package handler;

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

    @Override
    public String handleRequest(Request req, Response res) {
        RegisterRequest registerRequest = toRequest(req,RegisterRequest.class);
        RegisterResponse registerResponse;

        if (registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null){
            registerResponse = new RegisterResponse(null, null, "Error: bad request");
        }
        else {
            registerResponse = RegistrationService.getInstance().register(registerRequest);
        }
        return responseUpdate(res,registerResponse,registerResponse.message());
    }
}