package service;

import dataaccess.DAO.MemoryDAO.memoryAuthDataAccessObject;
import dataaccess.DAO.MemoryDAO.memoryGameDataAccessObject;
import dataaccess.DAO.MemoryDAO.memoryUserDataAccessObject;
import responses.ClearApplicationResponse;

public class ClearApplicationService {
    private static ClearApplicationService singleInstance = null;

    private ClearApplicationService(){
    }

    public static ClearApplicationService getInstance(){
        if (singleInstance == null){
            singleInstance = new ClearApplicationService();
        }
        return singleInstance;
    }

    public ClearApplicationResponse clear(){
        memoryAuthDataAccessObject.getInstance().clear();
        memoryUserDataAccessObject.getInstance().clear();
        memoryGameDataAccessObject.getInstance().clear();
        return new ClearApplicationResponse(null);
    }
}
