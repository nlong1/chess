package service;

import dataaccess.dao.memorydao.MemoryAuthDataAccessObject;
import dataaccess.dao.memorydao.MemoryGameDataAccessObject;
import dataaccess.dao.memorydao.MemoryUserDataAccessObject;
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
