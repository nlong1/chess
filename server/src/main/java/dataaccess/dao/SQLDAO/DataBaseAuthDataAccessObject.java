package dataaccess.dao.SQLDAO;

import dataaccess.dao.AuthDataAccessObject;

public class DataBaseAuthDataAccessObject implements AuthDataAccessObject {
    @Override
    public String createAuth(String username) {
        return "";
    }

    @Override
    public boolean getAuth(String authToken) {
        return false;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public String getUsername(String authToken) {
        return "";
    }

    @Override
    public void clear() {

    }
}
