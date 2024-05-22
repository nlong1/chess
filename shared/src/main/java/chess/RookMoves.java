package chess;

import java.util.Collection;
import java.util.HashSet;

public class RookMoves {
    private ChessGame.TeamColor pieceColor;
    private ChessBoard board;
    private ChessPosition myPosition;
    private Collection<ChessMove> availableMoves = new HashSet<ChessMove>();

    public RookMoves(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
        this.pieceColor = board.getPiece(myPosition).getTeamColor();
    }

    public Collection<ChessMove> calculator(){
//      GO TO THE RIGHT
        PieceMoves.lineCalculation(myPosition,board,availableMoves,pieceColor,0,1);
//      LEFT
        PieceMoves.lineCalculation(myPosition,board,availableMoves,pieceColor,0,-1);
//      UP
        PieceMoves.lineCalculation(myPosition,board,availableMoves,pieceColor,1,0);
//      DOWN
        PieceMoves.lineCalculation(myPosition,board,availableMoves,pieceColor,-1,0);

        return availableMoves;
    }
}
