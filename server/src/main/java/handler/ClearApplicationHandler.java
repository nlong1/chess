package handler;

import responses.ClearApplicationResponse;
import service.ClearApplicationService;
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
        ClearApplicationResponse clearApplicationResponse = ClearApplicationService.getInstance().clear();
        return responseUpdate(res,clearApplicationResponse,clearApplicationResponse.message());
    }

}
