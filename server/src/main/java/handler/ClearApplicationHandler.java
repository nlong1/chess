package handler;

import com.google.gson.Gson;
import responses.ClearApplicationResponse;
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

    @Override
    public String handleRequest(Request req, Response res){
        return new Gson().toJson(new ClearApplicationResponse(null));
    }

}
