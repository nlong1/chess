package dataaccess.dao.SQLDAO;

import dataaccess.dao.UserDataAccessObject;

public class DataBaseUserDataAccessObject implements UserDataAccessObject {
    @Override
    public String getUser(String username) {
        return "";
    }

    @Override
    public void createUser(String username, String password, String email) {

    }

    @Override
    public String getPassword(String username) {
        return "";
    }

    @Override
    public void clear() {

    }
}
