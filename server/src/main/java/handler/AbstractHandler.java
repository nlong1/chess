package handler;

import com.google.gson.Gson;
import request.RegisterRequest;
import responses.RegisterResponse;
import spark.Request;
import spark.Response;

public abstract class AbstractHandler {
    public abstract String handleRequest(Request req, Response res);

    protected <T> T toRequest(Request req, Class<T> classRequest) {
        Gson gson = new Gson();
        return gson.fromJson(req.body(), classRequest);
    }

    protected <T> String toString(Response res, T classResponse ) {
        String json = new Gson().toJson(classResponse);
        res.body(json);
        return json;
    }

    protected void updateStatus(Response res,String message){
        if (message == null){
            res.status(200);
            return;
        }
        switch (message){
            case "Error: bad request":
                res.status(400);
                return;
            case "Error: unauthorized":
                res.status(401);
                return;
            case "Error: already taken":
                res.status(403);
                return;
            default:
                res.status(500);

            }
        }

    protected <T> String responseUpdate(Response res, T classResponse, String message){
        String json = toString(res,classResponse);
        updateStatus(res,message);
        return json;
    }

}
