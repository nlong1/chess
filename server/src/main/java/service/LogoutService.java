package service;

import dataaccess.DataAccessException;
import dataaccess.dao.AuthDataAccessObject;
import dataaccess.dao.GameDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseAuthDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseGameDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseUserDataAccessObject;
import dataaccess.dao.UserDataAccessObject;
import dataaccess.dao.memorydao.MemoryAuthDataAccessObject;
import responses.LogoutResponse;

public class LogoutService {
    private static LogoutService singleInstance = null;
    private final AuthDataAccessObject auth = new DataBaseAuthDataAccessObject();

    private LogoutService(){
    }

    public static LogoutService getInstance(){
        if (singleInstance == null){
            singleInstance = new LogoutService();
        }
        return singleInstance;
    }

    public LogoutResponse logout(String authToken) {
        try {
            if (!auth.getAuth(authToken)) {
                return new LogoutResponse("Error: unauthorized");
            } else {
                auth.deleteAuth(authToken);
                return new LogoutResponse(null);
            }
        }
        catch (DataAccessException e){
            return new LogoutResponse(e.getMessage());
        }
    }
}
