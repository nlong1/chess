package websocket.commands;

public class LeaveGameCommand extends UserGameCommand{
    private final Integer gameID;

    public LeaveGameCommand(String authToken,Integer gameID) {
        super(authToken);
        commandType = CommandType.LEAVE;
        this.gameID = gameID;
    }

    public Integer getGameID(){
        return gameID;
    }
}
