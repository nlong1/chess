package service;

import dataaccess.DAO.MemoryDAO.MemoryAuthDAO;
import request.LogoutRequest;
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

    public LogoutResponse logout(LogoutRequest logoutRequest) {
        String authToken = logoutRequest.authtoken();
        if (!MemoryAuthDAO.getInstance().getAuth(authToken)){
            return new LogoutResponse("Error: unauthorized");
        }
        else {
            MemoryAuthDAO.getInstance().deleteAuth(authToken);
            return new LogoutResponse(null);
        }
    }
}
