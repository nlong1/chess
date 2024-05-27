package dataaccess.DAO.memoryDAO;

import dataaccess.DAO.AuthDataAccessObject;
import model.AuthData;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

public class memoryAuthDataAccessObject implements AuthDataAccessObject {
    private static memoryAuthDataAccessObject singleInstance = null;
    private final HashMap<String, AuthData> auth = new HashMap<>();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private memoryAuthDataAccessObject(){
    }

    public static memoryAuthDataAccessObject getInstance(){
        if (singleInstance == null){
            singleInstance = new memoryAuthDataAccessObject();
        }
        return singleInstance;
    }

    public String createAuth(String username) {
            byte[] randomBytes = new byte[24];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(randomBytes);
            String authToken = base64Encoder.encodeToString(randomBytes);
            AuthData authData = new AuthData(authToken,username);
            auth.put(authToken,authData);
            return authToken;
    }

    public boolean getAuth(String authToken){
        return auth.containsKey(authToken);
    }

    public void deleteAuth(String authToken){
        auth.remove(authToken);
    }

    public String getUsername(String authToken){
        return auth.get(authToken).username();
    }

    public void clear(){
        auth.clear();
    }

}
