package client;

import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:0");
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    void register() throws Exception {
        String randomUsername = UUID.randomUUID().toString();
        String[] registerReq = {"register",randomUsername,"password","p1@email.com"};
        assertEquals("        successful register with user: " + randomUsername,facade.register(registerReq));
    }

    @Test
    void register2() throws Exception {
        try {
            String[] registerReq = {"again", "a", "password", "p1@email.com"};
            facade.register(registerReq);
            String[] registerReq2 = {"again", "a", "password", "p1@email.com"};
            facade.register(registerReq2);
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void login1() throws Exception {
        try{
            String[] registerReq ={"login","again","a","email"};
            facade.register(registerReq);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        String[] loginReq = {"login", "again", "a"};
        assertTrue(facade.login(loginReq).length()>10);
    }

    @Test
    void login2() throws Exception {
        try{
            String[] registerReq ={"login","again",UUID.randomUUID().toString()};
            facade.register(registerReq);
            fail();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    void logout1() throws Exception {
        try {
            facade.logout("beans");
            fail();
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void logout2() throws Exception {
        String username = UUID.randomUUID().toString();
        String[] registerReq = {"register",username,"pass","email"};
        facade.register(registerReq);
        String[] loginReq = {"login",username,"pass"};
        String authToken = facade.login(loginReq);
        assertEquals("        logged out",facade.logout(authToken));
    }

    @Test
    void createGame1() throws Exception {
        String username = UUID.randomUUID().toString();
        String[] registerReq = {"register",username,"pass","email"};
        facade.register(registerReq);
        String[] loginReq = {"login",username,"pass"};
        String authToken = facade.login(loginReq);
        String[] createReq = {"create","game"};
        assertEquals("",facade.createGame(createReq,authToken));
    }

    @Test
    void createGame2() throws Exception {
        try {
            String[] createReq = {"create","game"};
            facade.createGame(createReq,"invalidAuth");
            fail();
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void listGames1() throws Exception {
        String username = UUID.randomUUID().toString();
        String[] registerReq = {"register",username,"pass","email"};
        facade.register(registerReq);
        String[] loginReq = {"login",username,"pass"};
        String authToken = facade.login(loginReq);
        assertNotNull(facade.listGames(authToken));
    }

    @Test
    void listGames2() throws Exception {
        try {
            assertNotNull(facade.listGames("bad auth"));
            fail();
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void joinGame1() throws Exception {
        try{
            facade.joinGame("WHITE",34,"badAuth");
            fail();
        }
        catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void joinGame2() throws Exception {
        try{
            facade.joinGame("w",45,"auth");
            fail();
        }
        catch (Exception e){
            assertEquals("not black or white",e.getMessage());
        }
    }

}
