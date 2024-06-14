package websocket.commands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    private final Integer gameID;
    private final ChessMove chessMove;
    private final ChessGame.TeamColor color;

    public MakeMoveCommand(String authToken, Integer gameID, ChessGame.TeamColor color, ChessMove chessMove) {
        super(authToken);
        commandType = CommandType.MAKE_MOVE;
        this.gameID = gameID;
        this.chessMove = chessMove;
        this.color = color;
    }

    public Integer getGameID(){
        return gameID;
    }

    public ChessMove getChessMove(){
        return chessMove;
    }

    public ChessGame.TeamColor getColor(){
        return color;
    }
}
