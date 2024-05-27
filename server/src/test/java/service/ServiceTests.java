package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.LoginRequest;
import request.RegisterRequest;
import responses.*;

import java.util.ArrayList;

public class ServiceTests {

    @BeforeEach
    public void clearAll(){
        ClearApplicationService.getInstance().clear();
    }

    @Test
    public void testRegularRegistration(){
        RegisterRequest regReq = new RegisterRequest("bob","jasdf","beans@gmail.com");
        RegisterResponse regRes = RegistrationService.getInstance().register(regReq);
        Assertions.assertEquals(regRes.username(),"bob");
        Assertions.assertNotNull(regRes.authToken());
    }

    @Test
    public void testUserTaken(){
        RegisterRequest initialRegistration = new RegisterRequest("bob","jasdf","beans@gmail.com");
        RegistrationService.getInstance().register(initialRegistration);
        RegisterRequest regReq = new RegisterRequest("bob","jasdf","beans@gmail.com");
        RegisterResponse regRes = RegistrationService.getInstance().register(regReq);
        Assertions.assertNull(regRes.username());
        Assertions.assertEquals(regRes.message(),"Error: already taken");
    }

    @Test
    public void testLogin(){
        RegisterRequest regReq = new RegisterRequest("bob","jasdf","beans@gmail.com");
        RegistrationService.getInstance().register(regReq);
        LoginRequest logReq = new LoginRequest("bob","jasdf");
        LoginResponse logRes = LoginService.getInstance().login(logReq);
        Assertions.assertEquals("bob",logRes.username());
        Assertions.assertNotNull(logRes.authToken());
    }

    @Test
    public void testInvalidLogin(){
        RegisterRequest regReq = new RegisterRequest("bob","jasdf","beans@gmail.com");
        RegistrationService.getInstance().register(regReq);
        LoginRequest logReq = new LoginRequest("bob","jas");
        LoginResponse logRes = LoginService.getInstance().login(logReq);
        Assertions.assertEquals("Error: unauthorized",logRes.message());
    }

    @Test
    public void testLogout(){
        RegisterRequest regReq = new RegisterRequest("bob","jasdf","beans@gmail.com");
        RegistrationService.getInstance().register(regReq);
        LoginRequest logReq = new LoginRequest("bob","jasdf");
        LoginResponse logRes = LoginService.getInstance().login(logReq);
        String authToken = logRes.authToken();
        LogoutResponse logoutResponse = LogoutService.getInstance().logout(authToken);
        Assertions.assertNull(logoutResponse.message());
    }

    @Test
    public void testLogoutInvalidAuthToken(){
        RegisterRequest regReq = new RegisterRequest("bob","jasdf","beans@gmail.com");
        RegistrationService.getInstance().register(regReq);
        LoginRequest logReq = new LoginRequest("bob","jasdf");
        LoginResponse logRes = LoginService.getInstance().login(logReq);
        String authToken = "beans";
        LogoutResponse logoutResponse = LogoutService.getInstance().logout(authToken);
        Assertions.assertEquals(logoutResponse.message(),"Error: unauthorized");
    }

    @Test
    public void testCreateGame(){
        RegisterRequest registerRequest = new RegisterRequest("username","password","email");
        RegistrationService.getInstance().register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResponse loginResponse = LoginService.getInstance().login(loginRequest);
        String defaultUserAuthToken = loginResponse.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest("beans");
        CreateGameResponse createGameResponse = CreateGameService.getInstance().createGame(defaultUserAuthToken,createGameRequest);
        Assertions.assertEquals(1,createGameResponse.gameID());
    }

    @Test
    public void testCreateGameInvalidAuth(){
        CreateGameRequest createGameRequest = new CreateGameRequest("beans");
        CreateGameResponse createGameResponse = CreateGameService.getInstance().createGame("bad",createGameRequest);
        Assertions.assertEquals(createGameResponse.message(),"Error: unauthorized");
    }

    @Test
    public void testJoinGame(){
        RegisterRequest registerRequest = new RegisterRequest("username","password","email");
        RegistrationService.getInstance().register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResponse loginResponse = LoginService.getInstance().login(loginRequest);
        String defaultUserAuthToken = loginResponse.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest("beans");
        CreateGameService.getInstance().createGame(defaultUserAuthToken,createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE",1);
        JoinGameResponse joinGameResponse = JoinGameService.getInstance().joinGame(defaultUserAuthToken,joinGameRequest);
        Assertions.assertNull(joinGameResponse.message());
    }

    @Test
    public void testJoinGameTaken(){
        RegisterRequest registerRequest = new RegisterRequest("username","password","email");
        RegistrationService.getInstance().register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResponse loginResponse = LoginService.getInstance().login(loginRequest);
        String defaultUserAuthToken = loginResponse.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest("beans");
        CreateGameService.getInstance().createGame(defaultUserAuthToken,createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE",1);
        JoinGameService.getInstance().joinGame(defaultUserAuthToken,joinGameRequest);
        JoinGameRequest joinGameReq = new JoinGameRequest("WHITE",1);
        JoinGameResponse joinGameRes = JoinGameService.getInstance().joinGame(defaultUserAuthToken,joinGameReq);
        Assertions.assertEquals("Error: already taken",joinGameRes.message());
    }

    @Test
    public void testClearEverything(){
        RegisterRequest registerRequest = new RegisterRequest("username","password","email");
        RegistrationService.getInstance().register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResponse loginResponse = LoginService.getInstance().login(loginRequest);
        String defaultUserAuthToken = loginResponse.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest("beans");
        CreateGameService.getInstance().createGame(defaultUserAuthToken,createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE",1);
        JoinGameService.getInstance().joinGame(defaultUserAuthToken,joinGameRequest);
        ClearApplicationResponse clearApplicationResponse = ClearApplicationService.getInstance().clear();
        Assertions.assertNull(clearApplicationResponse.message());
    }

    @Test
    public void testListGames(){
        RegisterRequest registerRequest = new RegisterRequest("username","password","email");
        RegistrationService.getInstance().register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResponse loginResponse = LoginService.getInstance().login(loginRequest);
        String defaultUserAuthToken = loginResponse.authToken();
        CreateGameRequest createGameRequest = new CreateGameRequest("beans");
        CreateGameService.getInstance().createGame(defaultUserAuthToken,createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE",1);
        JoinGameService.getInstance().joinGame(defaultUserAuthToken,joinGameRequest);
        ListGamesResponse listGamesResponse = ListGamesService.getInstance().listGames(defaultUserAuthToken);
        Assertions.assertNull(listGamesResponse.message());

    }

    @Test
    public void testListGamesNoGames(){
        RegisterRequest registerRequest = new RegisterRequest("username","password","email");
        RegistrationService.getInstance().register(registerRequest);
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResponse loginResponse = LoginService.getInstance().login(loginRequest);
        String defaultUserAuthToken = loginResponse.authToken();
        ListGamesResponse listGamesResponse = ListGamesService.getInstance().listGames(defaultUserAuthToken);
        Assertions.assertEquals(new ArrayList<>(),listGamesResponse.games());
    }
}
