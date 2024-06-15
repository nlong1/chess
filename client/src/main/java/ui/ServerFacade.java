package ui;
import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.LoginRequest;
import request.RegisterRequest;
import responses.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url){
        if (url == "http://localhost:0"){
            this.serverUrl = "http://localhost:8080";
        }
        else {
            this.serverUrl = url;
        }
    }

    public String joinGame(String player, Integer gameId, String authToken) throws Exception{
        ChessGame.TeamColor color;
        if (Objects.equals(player, "white")){
            color = ChessGame.TeamColor.WHITE;
        }
        else if (Objects.equals(player, "black")){
            color = ChessGame.TeamColor.BLACK;
        }
        else{
            throw new Exception("not black or white");
        }
        JoinGameResponse joinGameResponse = makeRequest("PUT","/game",new JoinGameRequest(color,gameId),JoinGameResponse.class,authToken);
        return "        joined game successfully \n";
    }

    public Collection<GameData> listGames(String authToken) throws ResponseException {
        ListGamesResponse listGamesResponse = makeRequest("GET","/game",null,ListGamesResponse.class,authToken);
        return listGamesResponse.games();
    }

    public String createGame(String[] request, String authToken) throws Exception{
        CreateGameResponse createGameResponse = makeRequest("POST","/game",new CreateGameRequest(request[1]),CreateGameResponse.class,authToken);
        System.out.println("        Successful creation of: " + request[1]);
        return "";
    }

    public String logout(String authToken) throws Exception{
        LogoutResponse logoutResponse = makeRequest("DELETE","/session",null, LogoutResponse.class,authToken);
        return "        logged out";
    }

    public String login(String[] request) throws Exception {
        LoginResponse loginResponse = makeRequest("POST","/session",new LoginRequest(request[1],request[2]), LoginResponse.class,null);
        System.out.println("        user: " + loginResponse.username());
        return loginResponse.authToken();
    }

    public String register(String[] request) throws Exception {
        RegisterResponse registerResponse = makeRequest("POST","/user",new RegisterRequest(request[1],request[2],request[3]), RegisterResponse.class,null);
        System.out.println("        successful register with user: " + registerResponse.username());
        return registerResponse.authToken();
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass,String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (authToken != null){
                http.setRequestProperty("authorization",authToken);
            }

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
