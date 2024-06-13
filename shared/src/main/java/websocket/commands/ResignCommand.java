package websocket.commands;

public class ResignCommand extends UserGameCommand{
    private final Integer gameID;

    public ResignCommand(String authToken,Integer gameID) {
        super(authToken);
        commandType = CommandType.CONNECT;
        this.gameID = gameID;
    }

    public Integer getGameID(){
        return gameID;
    }
}
