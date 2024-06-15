package websocket.commands;

import chess.ChessGame;

public class ResignCommand extends UserGameCommand{
    private final Integer gameID;

    public ResignCommand(String authToken,Integer gameID) {
        super(authToken);
        commandType = CommandType.RESIGN;
        this.gameID = gameID;
    }

    public Integer getGameID(){
        return gameID;
    }

}
