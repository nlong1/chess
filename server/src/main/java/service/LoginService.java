package service;

import dataaccess.DAO.memoryDAO.memoryAuthDataAccessObject;
import dataaccess.DAO.memoryDAO.memoryUserDataAccessObject;
import request.LoginRequest;
import responses.LoginResponse;

import java.util.Objects;

public class LoginService {
    private static LoginService singleInstance = null;

    private LoginService(){
    }

    public static LoginService getInstance(){
        if (singleInstance == null){
            singleInstance = new LoginService();
        }
        return singleInstance;
    }

    public LoginResponse login(LoginRequest loginRequest){
        if (loginRequest.username() == null || loginRequest.password() == null){
            return new LoginResponse(null,null,"Error: bad request");
        }
        String user = memoryUserDataAccessObject.getInstance().getUser(loginRequest.username());
        if (user == null){
            return new LoginResponse(null,null,"Error: unauthorized");
        }
        String password = memoryUserDataAccessObject.getInstance().getPassword(loginRequest.username());
        if (!Objects.equals(loginRequest.password(), password)){
            return new LoginResponse(null,null,"Error: unauthorized");
        }
        String authToken = memoryAuthDataAccessObject.getInstance().createAuth(user);
        return new LoginResponse(user,authToken,null);
    }
}
