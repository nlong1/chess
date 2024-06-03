package dataaccess.dao.sqldao;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.dao.AuthDataAccessObject;

import java.sql.SQLException;
import java.util.UUID;

public class DataBaseAuthDataAccessObject implements AuthDataAccessObject {
    public String createAuth(String username) throws DataAccessException{
        String authToken = UUID.randomUUID().toString();
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (authToken,username) values (?,?)")) {
                preparedStatement.setString(1,authToken);
                preparedStatement.setString(2,username);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            System.out.println("Error in creating authToken");
            System.out.println(e.getMessage());
            throw new DataAccessException(e.getMessage());
        }
        System.out.println("Success in creating authToken");
        return authToken;
    }

    public boolean getAuth(String authToken) throws DataAccessException{
        String token = null;
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT authToken from auth where authToken = ?")) {
                preparedStatement.setString(1,authToken);
                var rs = preparedStatement.executeQuery();
                if(rs.next()){
                    token = rs.getString(1);
                }
                return token != null;
            }
        } catch (SQLException e) {
            System.out.println("Error in selecting authToken");
            System.out.println(e.getMessage());
            throw new DataAccessException(e.getMessage());
        }
    }

    public void deleteAuth(String authToken) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM auth where authToken = ?")) {
                preparedStatement.setString(1,authToken);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            System.out.println("Error in deleting authToken");
            System.out.println(e.getMessage());
            throw new DataAccessException(e.getMessage());
        }
    }

    public String getUsername(String authToken) throws DataAccessException{
        String username = "bad";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT username from auth where authToken = ?")) {
                preparedStatement.setString(1,authToken);
                var rs = preparedStatement.executeQuery();
                if(rs.next()){
                    username = rs.getString(1);
                }
                return username;
            }
        } catch (SQLException e) {
            System.out.println("Error in selecting authToken");
            System.out.println(e.getMessage());
            throw new DataAccessException(e.getMessage());
        }
    }

    public void clear() throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM auth")) {
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            System.out.println("Error in deleting auth table");
            System.out.println(e.getMessage());
            throw new DataAccessException(e.getMessage());
        }
    }
}
