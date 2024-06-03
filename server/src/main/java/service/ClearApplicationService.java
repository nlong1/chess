package service;

import dataaccess.DataAccessException;
import dataaccess.dao.AuthDataAccessObject;
import dataaccess.dao.GameDataAccessObject;
import dataaccess.dao.sqldao.DataBaseAuthDataAccessObject;
import dataaccess.dao.sqldao.DataBaseGameDataAccessObject;
import dataaccess.dao.sqldao.DataBaseUserDataAccessObject;
import dataaccess.dao.UserDataAccessObject;
import responses.ClearApplicationResponse;

public class ClearApplicationService {
    private static ClearApplicationService singleInstance = null;
    private final AuthDataAccessObject auth = new DataBaseAuthDataAccessObject();
    private final UserDataAccessObject user = new DataBaseUserDataAccessObject();
    private final GameDataAccessObject game = new DataBaseGameDataAccessObject();

    private ClearApplicationService(){
    }

    public static ClearApplicationService getInstance(){
        if (singleInstance == null){
            singleInstance = new ClearApplicationService();
        }
        return singleInstance;
    }

    public ClearApplicationResponse clear(){
        try {
            auth.clear();
            user.clear();
            game.clear();
            return new ClearApplicationResponse(null);
        }
        catch (DataAccessException e){
            return new ClearApplicationResponse(e.getMessage());
        }
    }
}
