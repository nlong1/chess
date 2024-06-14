package websocket.commands;

import chess.ChessGame;

public class LeaveGameCommand extends UserGameCommand{
    private final Integer gameID;
    private final ChessGame.TeamColor color;

    public LeaveGameCommand(String authToken, Integer gameID, ChessGame.TeamColor color) {
        super(authToken);
        commandType = CommandType.LEAVE;
        this.gameID = gameID;
        this.color = color;
    }

    public Integer getGameID(){
        return gameID;
    }

    public ChessGame.TeamColor getColor(){
        return color;
    }
}
