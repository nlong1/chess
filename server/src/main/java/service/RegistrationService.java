package service;

import dataaccess.DAO.MemoryDAO.MemoryAuthDAO;
import dataaccess.DAO.MemoryDAO.MemoryUserDAO;
import handler.RegisterHandler;
import model.UserData;
import request.RegisterRequest;
import responses.RegisterResponse;
import dataaccess.DAO.MemoryDAO.MemoryAuthDAO;

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
        if (MemoryUserDAO.getInstance().getUser(registerRequest.username()) == null){
            MemoryUserDAO.getInstance().createUser(registerRequest.username(),registerRequest.password(),registerRequest.email());
            String authToken = MemoryAuthDAO.getInstance().createAuth(registerRequest.username());
            return new RegisterResponse(authToken);
        }
        else{
            return new RegisterResponse(null);
        }
    }
}
