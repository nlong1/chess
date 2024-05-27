package service;

import dataaccess.DAO.memoryDAO.memoryAuthDataAccessObject;
import dataaccess.DAO.memoryDAO.memoryUserDataAccessObject;
import request.RegisterRequest;
import responses.RegisterResponse;

public class RegistrationService {
    private static RegistrationService singleInstance = null;

    private RegistrationService(){
    }

    public static RegistrationService getInstance(){
        if (singleInstance == null){
            singleInstance = new RegistrationService();
        }
        return singleInstance;
    }

    public RegisterResponse register(RegisterRequest registerRequest){
        if (memoryUserDataAccessObject.getInstance().getUser(registerRequest.username()) == null){
            memoryUserDataAccessObject.getInstance().createUser(registerRequest.username(),registerRequest.password(),registerRequest.email());
            String authToken = memoryAuthDataAccessObject.getInstance().createAuth(registerRequest.username());
            return new RegisterResponse(registerRequest.username(),authToken,null);
        }
        else{
            return new RegisterResponse(null,null,"Error: already taken");
        }
    }
}
