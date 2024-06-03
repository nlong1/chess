package service;

import dataaccess.DataAccessException;
import dataaccess.dao.AuthDataAccessObject;
import dataaccess.dao.GameDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseAuthDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseGameDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseUserDataAccessObject;
import dataaccess.dao.UserDataAccessObject;
import dataaccess.dao.memorydao.MemoryAuthDataAccessObject;
import dataaccess.dao.memorydao.MemoryUserDataAccessObject;
import org.mindrot.jbcrypt.BCrypt;
import request.LoginRequest;
import responses.LoginResponse;

import java.util.Objects;

public class LoginService {
    private static LoginService singleInstance = null;
    private final AuthDataAccessObject auth = new DataBaseAuthDataAccessObject();
    private final UserDataAccessObject userDataAccessObject = new DataBaseUserDataAccessObject();

    private LoginService(){
    }

    public static LoginService getInstance(){
        if (singleInstance == null){
            singleInstance = new LoginService();
        }
        return singleInstance;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            if (loginRequest.username() == null || loginRequest.password() == null) {
                return new LoginResponse(null, null, "Error: bad request");
            }
            String user = userDataAccessObject.getUser(loginRequest.username());
            if (user == null) {
                return new LoginResponse(null, null, "Error: unauthorized");
            }
            String password = userDataAccessObject.getPassword(loginRequest.username());
            if (!BCrypt.checkpw(loginRequest.password(), password)) {
                return new LoginResponse(null, null, "Error: unauthorized");
            }
            String authToken = auth.createAuth(user);
            return new LoginResponse(user, authToken, null);
        }
        catch (DataAccessException e) {
            return new LoginResponse(null,null,e.getMessage());
        }
    }
}
