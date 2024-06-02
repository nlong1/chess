package dataaccess.dao;

import dataaccess.DataAccessException;

public interface UserDataAccessObject {
    String getUser(String username) throws DataAccessException;
    void createUser(String username, String password, String email) throws DataAccessException;
    String getPassword(String username) throws DataAccessException;
    void clear() throws DataAccessException;
}