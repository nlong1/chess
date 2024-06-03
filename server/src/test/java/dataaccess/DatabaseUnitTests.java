package dataaccess;

import chess.ChessGame;
import dataaccess.dao.AuthDataAccessObject;
import dataaccess.dao.GameDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseAuthDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseGameDataAccessObject;
import dataaccess.dao.SQLDAO.DataBaseUserDataAccessObject;
import dataaccess.dao.UserDataAccessObject;
import model.GameData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseUnitTests {

    private final AuthDataAccessObject authDataAccessObject = new DataBaseAuthDataAccessObject();
    private final UserDataAccessObject userDataAccessObject = new DataBaseUserDataAccessObject();
    private final GameDataAccessObject gameDataAccessObject = new DataBaseGameDataAccessObject();

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
        assertNotEquals("pass",userDataAccessObject.getPassword("user"));
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
        boolean pass = true;
        userDataAccessObject.clear();
        userDataAccessObject.createUser("user", "pass", "email");
        try{
            userDataAccessObject.createUser("user", "pass", "email");
            pass = false;
        }
        catch (DataAccessException e){
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    @Order(15)
    @DisplayName("getPassword Test 1")
    public void getPasswordTest1() throws DataAccessException {
        userDataAccessObject.clear();
        userDataAccessObject.createUser("user", "pass", "email");
        assertNotEquals("pass",userDataAccessObject.getPassword("user"));
        assertNotEquals("pass",userDataAccessObject.getPassword("user"));
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

    @Test
    @Order(19)
    @DisplayName("Create Game Test 1")
    public void createGameTest1() throws DataAccessException {
        int gameID = gameDataAccessObject.makeGame("yee");
        System.out.println(gameID);
        assertNotEquals(0,gameID);
    }

    @Test
    @Order(20)
    @DisplayName("Create Game Test 2")
    public void createGameTest2() throws DataAccessException {
        int gameID1 = gameDataAccessObject.makeGame("yee");
        int gameID2 = gameDataAccessObject.makeGame("yee");
        assertNotEquals(gameID2,gameID1);
    }

    @Test
    @Order(21)
    @DisplayName("Clear Game Test 1")
    public void clearTest2() throws DataAccessException {
        gameDataAccessObject.clear();
    }

    @Test
    @Order(22)
    @DisplayName("Game Exists Test 1")
    public void gameExistsTest1() throws DataAccessException {
        int gameID = gameDataAccessObject.makeGame("yee");
        assertTrue(gameDataAccessObject.gameExists(gameID));
    }

    @Test
    @Order(23)
    @DisplayName("Game Exists Test 2")
    public void gameExistsTest2() throws DataAccessException {
        int gameID = 523;
        assertFalse(gameDataAccessObject.gameExists(gameID));
    }

    @Test
    @Order(24)
    @DisplayName("Get Game Test 1")
    public void getGameTest1() throws DataAccessException {
        gameDataAccessObject.clear();
        int gameID = gameDataAccessObject.makeGame("yee");
        GameData game = gameDataAccessObject.getGame(gameID);
        assertNull(game.blackUsername());
    }

    @Test
    @Order(25)
    @DisplayName("Get Game Test 2")
    public void getGameTest2() throws DataAccessException {
        gameDataAccessObject.clear();
        int gameID = gameDataAccessObject.makeGame("yee");
        GameData game = gameDataAccessObject.getGame(gameID);
        assertNotNull(game.gameName());
    }

    @Test
    @Order(26)
    @DisplayName("Update Game Test 1")
    public void updateGameTest1() throws DataAccessException {
        gameDataAccessObject.clear();
        int gameID = gameDataAccessObject.makeGame("game");
        GameData game = new GameData(gameID,"beans","wow","game",new ChessGame());
        gameDataAccessObject.updateGame(gameID,game);
        GameData returnedGame = gameDataAccessObject.getGame(gameID);
        assertEquals("beans",returnedGame.whiteUsername());
    }

    @Test
    @Order(27)
    @DisplayName("Update Game Test 2")
    public void updateGameTest2() throws DataAccessException {
        gameDataAccessObject.clear();
        int gameID = gameDataAccessObject.makeGame("game");
        GameData game = new GameData(gameID,null,"wow",null,new ChessGame());
        gameDataAccessObject.updateGame(gameID,game);
        GameData returnedGame = gameDataAccessObject.getGame(gameID);
        assertNull(returnedGame.whiteUsername());
    }

    @Test
    @Order(28)
    @DisplayName("List Games Test 1")
    public void listGameTest1() throws DataAccessException {
        gameDataAccessObject.clear();
        int gameID = gameDataAccessObject.makeGame("game");
        assertNotNull(gameDataAccessObject.listGames());
    }

    @Test
    @Order(29)
    @DisplayName("List Games Test 2")
    public void listGameTest2() throws DataAccessException {
        gameDataAccessObject.clear();
        assertEquals(new ArrayList<>(),gameDataAccessObject.listGames());
    }

}