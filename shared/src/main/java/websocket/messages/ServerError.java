package websocket.messages;

import com.google.gson.Gson;

public class ServerError extends ServerMessage{
    private final String message;
    public ServerError(String message) {
        super(ServerMessageType.ERROR);
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

}
