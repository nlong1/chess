package service;

import dataaccess.DAO.MemoryDAO.MemoryAuthDataAccessObject;
import responses.LogoutResponse;

public class LogoutService {
    private static LogoutService singleInstance = null;

    private LogoutService(){
    }

    public static LogoutService getInstance(){
        if (singleInstance == null){
            singleInstance = new LogoutService();
        }
        return singleInstance;
    }

    public LogoutResponse logout(String authToken) {
        if (!MemoryAuthDataAccessObject.getInstance().getAuth(authToken)){
            return new LogoutResponse("Error: unauthorized");
        }
        else {
            MemoryAuthDataAccessObject.getInstance().deleteAuth(authToken);
            return new LogoutResponse(null);
        }
    }
}
