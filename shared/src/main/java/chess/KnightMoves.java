package chess;

import java.util.Collection;
import java.util.HashSet;

public class KnightMoves {
    private ChessGame.TeamColor pieceColor;
    private ChessBoard board;
    private ChessPosition myPosition;
    private Collection<ChessMove> availableMoves = new HashSet<ChessMove>();

    public KnightMoves(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
        this.pieceColor = board.getPiece(myPosition).getTeamColor();
    }

    private boolean onBoardCheck(ChessPosition newPosition){
        if ((newPosition.getRow()>=1) && (newPosition.getRow()<=8) && (newPosition.getColumn()>=1) && (newPosition.getColumn()<=8)){
            return true;
        }
        return false;
    }

    private boolean emptySpaceCheck(ChessPosition newPosition){
        if (board.getPiece(newPosition) == null){
            return true;
        }
        return false;
    }

    private boolean checkEnemyPiece(ChessPosition newPosition){
        if (board.getPiece(newPosition).getTeamColor() != pieceColor){
            return true;
        }
        return false;
    }

    private void calculation (ChessPosition newPosition){
        if (onBoardCheck(newPosition)){
            if (emptySpaceCheck(newPosition)){
                ChessMove newMove = new ChessMove(myPosition,newPosition,null);
                availableMoves.add(newMove);

            }
            else if (checkEnemyPiece(newPosition)){
                ChessMove newMove = new ChessMove(myPosition,newPosition,null);
                availableMoves.add(newMove);
            }
        }
    }

    public Collection<ChessMove> calculator(){
//        possible moving positions
        ChessPosition newPosition1 = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()-1);
        ChessPosition newPosition2 = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()+1);
        ChessPosition newPosition3 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+2);
        ChessPosition newPosition4 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-2);
        ChessPosition newPosition5 = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()-1);
        ChessPosition newPosition6 = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()+1);
        ChessPosition newPosition7 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+2);
        ChessPosition newPosition8 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-2);

        calculation(newPosition1);
        calculation(newPosition2);
        calculation(newPosition3);
        calculation(newPosition4);
        calculation(newPosition5);
        calculation(newPosition6);
        calculation(newPosition7);
        calculation(newPosition8);

        return availableMoves;
    }
}
