package websocket.commands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    private final Integer gameID;
    private final ChessMove chessMove;

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove chessMove) {
        super(authToken);
        commandType = CommandType.MAKE_MOVE;
        this.gameID = gameID;
        this.chessMove = chessMove;
    }

    public Integer getGameID(){
        return gameID;
    }

    public ChessMove getChessMove(){
        return chessMove;
    }
}
