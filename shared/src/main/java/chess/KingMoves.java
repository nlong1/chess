package chess;

import java.util.Collection;
import java.util.HashSet;

public class KingMoves {
    private ChessGame.TeamColor pieceColor;
    private ChessBoard board;
    private ChessPosition myPosition;
    private Collection<ChessMove> availableMoves = new HashSet<ChessMove>();

    public KingMoves(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
        this.pieceColor = board.getPiece(myPosition).getTeamColor();
    }

    public Collection<ChessMove> calculator(){
//        possible moving positions
        ChessPosition newPosition1 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
        ChessPosition newPosition2 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
        ChessPosition newPosition3 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
        ChessPosition newPosition4 = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1);
        ChessPosition newPosition5 = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1);
        ChessPosition newPosition6 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
        ChessPosition newPosition7 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
        ChessPosition newPosition8 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);

        PieceMoves.moveCalculationKnightOrKing(myPosition,newPosition1,board,pieceColor,availableMoves);
        PieceMoves.moveCalculationKnightOrKing(myPosition,newPosition2,board,pieceColor,availableMoves);
        PieceMoves.moveCalculationKnightOrKing(myPosition,newPosition3,board,pieceColor,availableMoves);
        PieceMoves.moveCalculationKnightOrKing(myPosition,newPosition4,board,pieceColor,availableMoves);
        PieceMoves.moveCalculationKnightOrKing(myPosition,newPosition5,board,pieceColor,availableMoves);
        PieceMoves.moveCalculationKnightOrKing(myPosition,newPosition6,board,pieceColor,availableMoves);
        PieceMoves.moveCalculationKnightOrKing(myPosition,newPosition7,board,pieceColor,availableMoves);
        PieceMoves.moveCalculationKnightOrKing(myPosition,newPosition8,board,pieceColor,availableMoves);


        return availableMoves;
    }
}
