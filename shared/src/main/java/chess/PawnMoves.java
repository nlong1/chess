package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnMoves {
    private ChessGame.TeamColor pieceColor;
    private ChessBoard board;
    private ChessPosition myPosition;
    private Collection<ChessMove> availableMoves = new HashSet<ChessMove>();

    public PawnMoves(ChessBoard board, ChessPosition myPosition){
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

    private void addMove(ChessPosition newPosition){
        if ((newPosition.getRow() == 8) || (newPosition.getRow() == 1)){
            ChessMove newMove1 = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.BISHOP);
            availableMoves.add(newMove1);
            ChessMove newMove2 = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.ROOK);
            availableMoves.add(newMove2);
            ChessMove newMove3 = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.QUEEN);
            availableMoves.add(newMove3);
            ChessMove newMove4 = new ChessMove(myPosition, newPosition, ChessPiece.PieceType.KNIGHT);
            availableMoves.add(newMove4);
        }
        else{
            ChessMove newMove = new ChessMove(myPosition, newPosition, null);
            availableMoves.add(newMove);
        }
    }

    public Collection<ChessMove> calculator(){
//        WHITE FIRST
        if (pieceColor == ChessGame.TeamColor.WHITE) {
//        1 MOVE FORWARD
            ChessPosition forward1 = new ChessPosition(myPosition.getRow()+1,myPosition.getColumn());
            if (onBoardCheck(forward1)){
                if (emptySpaceCheck(forward1)){
                    addMove(forward1);
                }
            }
//        2 MOVE FORWARD
            ChessPosition forward2 = new ChessPosition(myPosition.getRow()+2,myPosition.getColumn());
            if (onBoardCheck(forward2)){
                if ((myPosition.getRow()==2) && (!availableMoves.isEmpty()) && (emptySpaceCheck(forward2))){
                    addMove(forward2);
                }
            }
//        BOTH DIAGONAL
            ChessPosition diag1 = new ChessPosition(myPosition.getRow()+1,myPosition.getColumn()-1);
            if (onBoardCheck(diag1)){
                if(!emptySpaceCheck(diag1) && checkEnemyPiece(diag1)){
                    addMove(diag1);
                }
            }

            ChessPosition diag2 = new ChessPosition(myPosition.getRow()+1,myPosition.getColumn()+1);
            if (onBoardCheck(diag2)){
                if(!emptySpaceCheck(diag2) && checkEnemyPiece(diag2)){
                    addMove(diag2);
                }
            }
        }
//        BLACK NEXT
        else{
            //        1 MOVE FORWARD
            ChessPosition forward1 = new ChessPosition(myPosition.getRow()-1,myPosition.getColumn());
            if (onBoardCheck(forward1)){
                if (emptySpaceCheck(forward1)){
                    addMove(forward1);
                }
            }
//        2 MOVE FORWARD
            ChessPosition forward2 = new ChessPosition(myPosition.getRow()-2,myPosition.getColumn());
            if (onBoardCheck(forward2)){
                if ((myPosition.getRow()==7) && (!availableMoves.isEmpty()) && (emptySpaceCheck(forward2))){
                    addMove(forward2);
                }
            }
        }
//        BOTH DIAGONAL
        ChessPosition diag1 = new ChessPosition(myPosition.getRow()-1,myPosition.getColumn()-1);
        if (onBoardCheck(diag1)){
            if(!emptySpaceCheck(diag1) && checkEnemyPiece(diag1)){
                addMove(diag1);
            }
        }

        ChessPosition diag2 = new ChessPosition(myPosition.getRow()-1,myPosition.getColumn()+1);
        if (onBoardCheck(diag2)){
            if(!emptySpaceCheck(diag2) && checkEnemyPiece(diag2)){
                addMove(diag2);
            }
        }
        return availableMoves;
    }
}
