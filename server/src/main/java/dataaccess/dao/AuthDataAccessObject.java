package dataaccess.dao;

public interface AuthDataAccessObject {
    String createAuth(String username);
    boolean getAuth(String authToken);
    void deleteAuth(String authToken);
    String getUsername(String authToken);
    void clear();
}
