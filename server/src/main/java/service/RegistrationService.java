package service;

import dataaccess.DAO.MemoryDAO.MemoryAuthDataAccessObject;
import dataaccess.DAO.MemoryDAO.MemoryUserDataAccessObject;
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
        if (MemoryUserDataAccessObject.getInstance().getUser(registerRequest.username()) == null){
            MemoryUserDataAccessObject.getInstance().createUser(registerRequest.username(),registerRequest.password(),registerRequest.email());
            String authToken = MemoryAuthDataAccessObject.getInstance().createAuth(registerRequest.username());
            return new RegisterResponse(registerRequest.username(),authToken,null);
        }
        else{
            return new RegisterResponse(null,null,"Error: already taken");
        }
    }
}
