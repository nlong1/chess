package database;

import dataaccess.DataAccessException;
import dataaccess.dao.AuthDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseAuthDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseUserDataAccessObject;
import dataaccess.dao.UserDataAccessObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseUnitTests {

    private final AuthDataAccessObject authDataAccessObject = new DataBaseAuthDataAccessObject();
    private final UserDataAccessObject userDataAccessObject = new DataBaseUserDataAccessObject();

    @Test
    @Order(1)
    @DisplayName("Create Authorization Test")
    public void createAuthTest() throws DataAccessException {
        String authToken = authDataAccessObject.createAuth("beans");
        assertNotNull(authToken);
        assertTrue(authDataAccessObject.getAuth(authToken));
    }

    @Test
    @Order(2)
    @DisplayName("Create Authorization Test 2")
    public void createAuthTestAgain() throws DataAccessException {
        String authToken1 = authDataAccessObject.createAuth("beans");
        assertNotNull(authToken1);
        String authToken2 = authDataAccessObject.createAuth("beans");
        assertNotNull(authToken2);
        assertNotEquals(authToken1,authToken2);
    }

    @Test
    @Order(3)
    @DisplayName("Get Authorization Test 1")
    public void getAuthTest1() throws DataAccessException {
        String badAuthToken = "not authorized";
        assertFalse(authDataAccessObject.getAuth(badAuthToken));
    }

    @Test
    @Order(4)
    @DisplayName("Get Authorization Test 2")
    public void getAuthTest2() throws DataAccessException {
        String authToken = authDataAccessObject.createAuth("beans");
        assertTrue(authDataAccessObject.getAuth(authToken));
    }

    @Test
    @Order(5)
    @DisplayName("Clear auth Test 1")
    public void clearAuthTest1() throws DataAccessException {
        String authToken1 = authDataAccessObject.createAuth("beans");
        assertNotNull(authToken1);
        authDataAccessObject.clear();
        assertFalse(authDataAccessObject.getAuth(authToken1));
    }

    @Test
    @Order(6)
    @DisplayName("Clear auth Test 2")
    public void clearAuthTest2() throws DataAccessException {
        String authToken1 = authDataAccessObject.createAuth("beans");
        assertNotNull(authToken1);
        String authToken2 = authDataAccessObject.createAuth("beans");
        assertNotNull(authToken2);
        authDataAccessObject.clear();
        assertFalse(authDataAccessObject.getAuth(authToken1));
        assertFalse(authDataAccessObject.getAuth(authToken2));
    }

    @Test
    @Order(7)
    @DisplayName("Delete Authorization Test 1")
    public void deleteAuthTest1() throws DataAccessException {
        String authToken1 = authDataAccessObject.createAuth("beans");
        assertNotNull(authToken1);
        authDataAccessObject.deleteAuth(authToken1);
        assertFalse(authDataAccessObject.getAuth(authToken1));
    }

    @Test
    @Order(8)
    @DisplayName("Delete Authorization Test 2")
    public void deleteAuthTest2() throws DataAccessException {
        String authToken1 = authDataAccessObject.createAuth("beans");
        assertNotNull(authToken1);
        String authToken2 = authDataAccessObject.createAuth("beans");
        assertNotNull(authToken2);
        authDataAccessObject.deleteAuth(authToken1);
        assertFalse(authDataAccessObject.getAuth(authToken1));
        assertTrue(authDataAccessObject.getAuth(authToken2));
        authDataAccessObject.deleteAuth(authToken2);
        assertFalse(authDataAccessObject.getAuth(authToken2));
    }

    @Test
    @Order(9)
    @DisplayName("Get Username Test 1")
    public void getUsernameTest1() throws DataAccessException {
        String authToken = authDataAccessObject.createAuth("beans");
        assertEquals("beans",authDataAccessObject.getUsername(authToken));
    }

    @Test
    @Order(10)
    @DisplayName("Get Username Test 2")
    public void getUsernameTest2() throws DataAccessException {
        String authToken1 = authDataAccessObject.createAuth("beans");
        String authToken2 = authDataAccessObject.createAuth("rice");
        assertEquals("beans",authDataAccessObject.getUsername(authToken1));
        assertEquals("rice",authDataAccessObject.getUsername(authToken2));
    }

    @Test
    @Order(11)
    @DisplayName("Clear User Table Test 1")
    public void clearUserTableTest1() throws DataAccessException {
        userDataAccessObject.clear();
    }

    @Test
    @Order(12)
    @DisplayName("Create User Test 1")
    public void createUserTest1() throws DataAccessException {
        userDataAccessObject.clear();
        userDataAccessObject.createUser("user","pass","email");
        assertEquals("pass",userDataAccessObject.getPassword("user"));
    }

    @Test
    @Order(13)
    @DisplayName("Clear User Table Test 2")
    public void clearUserTableTest2() throws DataAccessException {
        userDataAccessObject.clear();
    }

    @Test
    @Order(14)
    @DisplayName("Create User Test 2")
    public void createUserTest2() throws DataAccessException {
        userDataAccessObject.clear();
        userDataAccessObject.createUser("user", "pass", "email");
        assertEquals("pass",userDataAccessObject.getPassword("user"));
    }

    @Test
    @Order(15)
    @DisplayName("getPassword Test 1")
    public void getPasswordTest1() throws DataAccessException {
        userDataAccessObject.clear();
        userDataAccessObject.createUser("user", "pass", "email");
        assertEquals("pass",userDataAccessObject.getPassword("user"));
        assertEquals("pass",userDataAccessObject.getPassword("user"));
    }

    @Test
    @Order(16)
    @DisplayName("getPassword Test 2")
    public void getPasswordTest2() throws DataAccessException {
        userDataAccessObject.clear();
        userDataAccessObject.createUser("badUser", "p", "email");
        userDataAccessObject.createUser("user", "pass", "email");
        assertNotEquals("pass",userDataAccessObject.getPassword("badUser"));
    }

    @Test
    @Order(17)
    @DisplayName("Verify Username Exists 1")
    public void verifyUsernameExistsTest() throws DataAccessException {
        userDataAccessObject.clear();
        userDataAccessObject.createUser("user", "pass", "email");
        assertEquals("user",userDataAccessObject.getUser("user"));
    }

    @Test
    @Order(18)
    @DisplayName("Username Doesn't Exists 1")
    public void usernameNoExistTest() throws DataAccessException {
        userDataAccessObject.clear();
        assertNull(userDataAccessObject.getUser("user"));
    }

}