package dataaccess.DAO;

public interface UserDAO {
    String getUser(String username);

    void createUser(String username, String password, String email);

    String getPassword(String username);
}
