package websocket.messages;

import com.google.gson.Gson;

public class ServerNotification extends ServerMessage{
    private String message;
    public ServerNotification(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

}
