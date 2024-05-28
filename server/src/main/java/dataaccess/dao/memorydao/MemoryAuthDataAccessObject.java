package dataaccess.dao.memorydao;

import dataaccess.dao.AuthDataAccessObject;
import model.AuthData;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

public class MemoryAuthDataAccessObject implements AuthDataAccessObject {
    private static MemoryAuthDataAccessObject singleInstance = null;
    private final HashMap<String, AuthData> auth = new HashMap<>();
    private static final Base64.Encoder goodEncoder = Base64.getUrlEncoder();

    private MemoryAuthDataAccessObject(){
    }

    public static MemoryAuthDataAccessObject getInstance(){
        if (singleInstance == null){
            singleInstance = new MemoryAuthDataAccessObject();
        }
        return singleInstance;
    }

    public String createAuth(String username) {
            byte[] randomBytes = new byte[24];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(randomBytes);
            String authToken = goodEncoder.encodeToString(randomBytes);
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
