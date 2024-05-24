package dataaccess.DAO.MemoryDAO;

import dataaccess.DAO.AuthDAO;
import model.AuthData;
import java.util.HashMap;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class MemoryAuthDAO implements AuthDAO {
    private static MemoryAuthDAO singleInstance = null;
    private HashMap<String, AuthData> auth = new HashMap<>();

    private MemoryAuthDAO(){
    }

    public static MemoryAuthDAO getInstance(){
        if (singleInstance == null){
            singleInstance = new MemoryAuthDAO();
        }
        return singleInstance;
    }

    public String createAuth(String username) {
        try {
            // Calculate SHA-256 hash of the input string
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digestBytes = md.digest(username.getBytes(StandardCharsets.UTF_8));

            // Convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : digestBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            String authToken = hexString.toString();
            AuthData authData = new AuthData(authToken,username);
            auth.put(authToken,authData);
            return authToken;
        } catch (NoSuchAlgorithmException e) {
            // Handle the case where SHA-256 algorithm is not available
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

}
