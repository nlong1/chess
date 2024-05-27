package dataaccess.DAO.memoryDAO;

import dataaccess.DAO.UserDataAccessObject;
import java.util.HashMap;
import model.UserData;

public class memoryUserDataAccessObject implements UserDataAccessObject {
    private static memoryUserDataAccessObject singleInstance = null;
    private HashMap<String,UserData> users = new HashMap<>();

    private memoryUserDataAccessObject(){
    }

    public static memoryUserDataAccessObject getInstance(){
        if (singleInstance == null){
            singleInstance = new memoryUserDataAccessObject();
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