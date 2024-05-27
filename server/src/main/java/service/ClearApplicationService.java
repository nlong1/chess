package service;

import dataaccess.DAO.MemoryDAO.MemoryAuthDataAccessObject;
import dataaccess.DAO.MemoryDAO.MemoryGameDataAccessObject;
import dataaccess.DAO.MemoryDAO.MemoryUserDataAccessObject;
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
        MemoryAuthDataAccessObject.getInstance().clear();
        MemoryUserDataAccessObject.getInstance().clear();
        MemoryGameDataAccessObject.getInstance().clear();
        return new ClearApplicationResponse(null);
    }
}
