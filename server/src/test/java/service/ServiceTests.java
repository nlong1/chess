package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import responses.LoginResponse;
import responses.RegisterResponse;

public class ServiceTests {

//    @BeforeEach
//    public void setUp(){
//
//    }

    @Test
    public void testRegularRegistration(){
        RegisterRequest regReq = new RegisterRequest("bob","jasdf","beans@gmail.com");
        RegisterResponse regRes = RegistrationService.getInstance().register(regReq);
        Assertions.assertEquals(regRes.username(),"bob");
        Assertions.assertNotNull(regRes.authToken());
    }

    @Test
    public void testUserTaken(){
        RegisterRequest regReq = new RegisterRequest("bob","jasdf","beans@gmail.com");
        RegisterResponse regRes = RegistrationService.getInstance().register(regReq);
        Assertions.assertNull(regRes.username());
        Assertions.assertEquals(regRes.message(),"Error: already taken");
    }

    @Test
    public void testLogin(){
        LoginRequest logReq = new LoginRequest("bob","jasdf");
        LoginResponse logRes = LoginService.getInstance().login(logReq);
        Assertions.assertEquals("bob",logRes.username());
        Assertions.assertNotNull(logRes.authToken());
    }

    @Test
    public void testInvalidLogin(){
        LoginRequest logReq = new LoginRequest("bob","jas");
        LoginResponse logRes = LoginService.getInstance().login(logReq);
        Assertions.assertEquals("Error: unauthorized",logRes.message());
    }
}
