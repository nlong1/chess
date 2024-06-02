package dataaccess.dao;

import dataaccess.DataAccessException;

public interface AuthDataAccessObject {
    String createAuth(String username) throws DataAccessException;
    boolean getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    String getUsername(String authToken) throws DataAccessException;
    void clear() throws DataAccessException;
}
