package dataaccess.DAO;

public interface AuthDAO {
    String createAuth(String username);
    boolean getAuth(String authToken);
    void deleteAuth(String authToken);
}
