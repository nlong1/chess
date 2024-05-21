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

    private boolean isOnBoard(ChessPosition newPosition){
        if ((newPosition.getRow()>=1) && (newPosition.getRow()<=8) && (newPosition.getColumn()>=1) && (newPosition.getColumn()<=8)){
            return true;
        }
        return false;
    }

    private boolean isEmptySpace(ChessPosition newPosition){
        if (board.getPiece(newPosition) == null){
            return true;
        }
        return false;
    }

    private boolean isEnemyPiece(ChessPosition newPosition){
        if (board.getPiece(newPosition).getTeamColor() != pieceColor){
            return true;
        }
        return false;
    }

    private void moveCalculation (ChessPosition newPosition){
        if (isOnBoard(newPosition)){
            if (isEmptySpace(newPosition)){
                ChessMove newMove = new ChessMove(myPosition,newPosition,null);
                availableMoves.add(newMove);

            }
            else if (isEnemyPiece(newPosition)){
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

        moveCalculation(newPosition1);
        moveCalculation(newPosition2);
        moveCalculation(newPosition3);
        moveCalculation(newPosition4);
        moveCalculation(newPosition5);
        moveCalculation(newPosition6);
        moveCalculation(newPosition7);
        moveCalculation(newPosition8);

        return availableMoves;
    }
}
