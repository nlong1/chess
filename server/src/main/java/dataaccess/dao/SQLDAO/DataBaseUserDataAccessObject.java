package dataaccess.dao.SQLDAO;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.dao.UserDataAccessObject;

import java.sql.SQLException;

public class DataBaseUserDataAccessObject implements UserDataAccessObject {
    public String getUser(String username) throws DataAccessException {
        String user = null;
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT username FROM user where username = ?")) {
                preparedStatement.setString(1,username);
                var rs = preparedStatement.executeQuery();
                if (rs.next()){
                    user = rs.getString(1);
                    return user;
                }
                return user;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void createUser(String username, String password, String email) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username,password,email) values (?,?,?)")) {
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                preparedStatement.setString(3,email);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public String getPassword(String username) throws DataAccessException {
        String password = "bad_password";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT password FROM user where username = ?")) {
                preparedStatement.setString(1,username);
                var rs = preparedStatement.executeQuery();
                if (rs.next()){
                    password = rs.getString(1);
                }
                return password;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM user")) {
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            System.out.println("Error in deleting user table");
            System.out.println(e.getMessage());
            throw new DataAccessException(e.getMessage());
        }
    }
}
