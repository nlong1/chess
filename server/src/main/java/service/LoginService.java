package service;

import dataaccess.DAO.MemoryDAO.MemoryAuthDAO;
import dataaccess.DAO.MemoryDAO.MemoryUserDAO;
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
        String user = MemoryUserDAO.getInstance().getUser(loginRequest.username());
        if (user == null){
            return new LoginResponse(null,null,"Error: unauthorized");
        }
        String password = MemoryUserDAO.getInstance().getPassword(loginRequest.username());
        if (!Objects.equals(loginRequest.password(), password)){
            return new LoginResponse(null,null,"Error: unauthorized");
        }
        String authToken = MemoryAuthDAO.getInstance().createAuth(user);
        return new LoginResponse(user,authToken,null);
    }
}
