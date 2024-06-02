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
import request.RegisterRequest;
import responses.RegisterResponse;

public class RegistrationService {
    private static RegistrationService singleInstance = null;
    private final AuthDataAccessObject auth = new DataBaseAuthDataAccessObject();
    private final UserDataAccessObject user = new DataBaseUserDataAccessObject();
    private final GameDataAccessObject game = new DataBaseGameDataAccessObject();

    private RegistrationService(){
    }

    public static RegistrationService getInstance(){
        if (singleInstance == null){
            singleInstance = new RegistrationService();
        }
        return singleInstance;
    }

    public RegisterResponse register(RegisterRequest registerRequest){
        try {
            if (user.getUser(registerRequest.username()) == null) {
                user.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email());
                String authToken = auth.createAuth(registerRequest.username());
                return new RegisterResponse(registerRequest.username(), authToken, null);
            } else {
                return new RegisterResponse(null, null, "Error: already taken");
            }
        }
        catch (DataAccessException e) {
            return new RegisterResponse(null,null,e.getMessage());
        }
    }
}
