package websocket.messages;

import com.google.gson.Gson;

public class ServerError extends ServerMessage{
    private final String errorMessage;
    public ServerError(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }
    public String getMessage(){
        return errorMessage;
    }

}
