package handler;

import spark.Request;
import spark.Response;

public class LoginHandler extends AbstractHandler{
    private static LoginHandler singleInstance = null;

    private LoginHandler(){
    }

    public static LoginHandler getInstance(){
        if (singleInstance == null){
            singleInstance = new LoginHandler();
        }
        return singleInstance;
    }

    public String handleLoginRequest(Request req, Response res){
        return null;
    }
}
