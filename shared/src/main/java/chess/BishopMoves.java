package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMoves {
    private ChessGame.TeamColor pieceColor;
    private ChessBoard board;
    private ChessPosition myPosition;
    private Collection<ChessMove> availableMoves = new HashSet<ChessMove>();

    public BishopMoves(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
        this.pieceColor = board.getPiece(myPosition).getTeamColor();
    }

    public Collection<ChessMove> calculator(){
//      GO TO THE UP/RIGHT
        PieceMoves.lineCalculation(myPosition,board,availableMoves,pieceColor,1,1);
//      UP/LEFT
        PieceMoves.lineCalculation(myPosition,board,availableMoves,pieceColor,1,-1);
//      DOWN/RIGHT
        PieceMoves.lineCalculation(myPosition,board,availableMoves,pieceColor,-1,1);
//      DOWN/LEFT
        PieceMoves.lineCalculation(myPosition,board,availableMoves,pieceColor,-1,-1);

        return availableMoves;
    }
}
