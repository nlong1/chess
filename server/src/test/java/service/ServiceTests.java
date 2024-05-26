package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import responses.LoginResponse;
import responses.LogoutResponse;
import responses.RegisterResponse;

public class ServiceTests {

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
        LogoutRequest logoutRequest = new LogoutRequest(authToken);
        LogoutResponse logoutResponse = LogoutService.getInstance().logout(logoutRequest);
        Assertions.assertNull(logoutResponse.message());
    }

    @Test
    public void testLogoutInvalidAuthToken(){
        RegisterRequest regReq = new RegisterRequest("bob","jasdf","beans@gmail.com");
        RegistrationService.getInstance().register(regReq);
        LoginRequest logReq = new LoginRequest("bob","jasdf");
        LoginResponse logRes = LoginService.getInstance().login(logReq);
        String authToken = logRes.authToken();
        LogoutRequest logoutRequest = new LogoutRequest("fakeAuthToken");
        LogoutResponse logoutResponse = LogoutService.getInstance().logout(logoutRequest);
        Assertions.assertEquals(logoutResponse.message(),"Error: unauthorized");
    }
}
