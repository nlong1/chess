package websocket.commands;

public class ConnectCommand extends UserGameCommand{
    private final Integer gameID;

    public ConnectCommand(String authToken,Integer gameID) {
        super(authToken);
        commandType = CommandType.CONNECT;
        this.gameID = gameID;
    }

    public Integer getGameID(){
        return gameID;
    }
}
