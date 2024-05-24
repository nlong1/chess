package handler;

import spark.Request;
import spark.Response;

public class ClearApplicationHandler extends AbstractHandler{
    private static ClearApplicationHandler singleInstance = null;

    private ClearApplicationHandler(){
    }

    public static ClearApplicationHandler getInstance(){
        if (singleInstance == null){
            singleInstance = new ClearApplicationHandler();
        }
        return singleInstance;
    }

    public String handleClearApplicationRequest(Request req, Response res){
        return null;
    }
}
