package dataaccess.DAO.MemoryDAO;

import dataaccess.DAO.UserDAO;
import java.util.HashMap;
import model.UserData;

public class MemoryUserDAO implements UserDAO {
    private static MemoryUserDAO singleInstance = null;
    private HashMap<String,UserData> users = new HashMap<>();

    private MemoryUserDAO(){
    }

    public static MemoryUserDAO getInstance(){
        if (singleInstance == null){
            singleInstance = new MemoryUserDAO();
        }
        return singleInstance;
    }

    public String getUser(String username){
        UserData user = users.get(username);
        if (user == null){
            return null;
        }
        else{
            return user.username();
        }
    }

    public String getPassword(String username){
        UserData user = users.get(username);
        return user.password();
    }

    public void createUser(String username, String password, String email){
        UserData user = new UserData(username,password,email);
        users.put(username,user);
    }

}
