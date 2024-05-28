package dataaccess.dao.memorydao;

import dataaccess.dao.UserDataAccessObject;
import java.util.HashMap;
import model.UserData;

public class MemoryUserDataAccessObject implements UserDataAccessObject {
    private static MemoryUserDataAccessObject singleInstance = null;
    private HashMap<String,UserData> users = new HashMap<>();

    private MemoryUserDataAccessObject(){
    }

    public static MemoryUserDataAccessObject getInstance(){
        if (singleInstance == null){
            singleInstance = new MemoryUserDataAccessObject();
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

    public void clear(){
        users.clear();
    }

}
