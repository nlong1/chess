package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnMoves {
    private ChessGame.TeamColor pieceColor;
    private ChessBoard board;
    private ChessPosition myPosition;
    private Collection<ChessMove> availableMoves = new HashSet<>();

    public PawnMoves(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.myPosition = myPosition;
        this.pieceColor = board.getPiece(myPosition).getTeamColor();
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

    public Collection<ChessMove>calculator(){
//        WHITE FIRST
        if (pieceColor == ChessGame.TeamColor.WHITE) {
//        1 MOVE FORWARD
            ChessPosition forward1 = new ChessPosition(myPosition.getRow()+1,myPosition.getColumn());
            if (PieceMoves.isOnBoard(forward1)){
                if (PieceMoves.isEmptySpace(forward1,board)){
                    addMove(forward1);
                }
            }
//        2 MOVE FORWARD
            ChessPosition forward2 = new ChessPosition(myPosition.getRow()+2,myPosition.getColumn());
            if (PieceMoves.isOnBoard(forward2)){
                if ((myPosition.getRow()==2) && (!availableMoves.isEmpty()) && (PieceMoves.isEmptySpace(forward2,board))){
                    addMove(forward2);
                }
            }
//        BOTH DIAGONAL
            ChessPosition diag1 = new ChessPosition(myPosition.getRow()+1,myPosition.getColumn()-1);
            if (PieceMoves.isOnBoard(diag1)){
                if(!PieceMoves.isEmptySpace(diag1,board) && PieceMoves.isEnemyPiece(diag1,board,pieceColor)){
                    addMove(diag1);
                }
            }

            ChessPosition diag2 = new ChessPosition(myPosition.getRow()+1,myPosition.getColumn()+1);
            if (PieceMoves.isOnBoard(diag2)){
                if(!PieceMoves.isEmptySpace(diag2,board) && PieceMoves.isEnemyPiece(diag2,board,pieceColor)){
                    addMove(diag2);
                }
            }
        }
//        BLACK NEXT
        else{
            //        1 MOVE FORWARD
            ChessPosition forward1 = new ChessPosition(myPosition.getRow()-1,myPosition.getColumn());
            if (PieceMoves.isOnBoard(forward1)){
                if (PieceMoves.isEmptySpace(forward1,board)){
                    addMove(forward1);
                }
            }
//        2 MOVE FORWARD
            ChessPosition forward2 = new ChessPosition(myPosition.getRow()-2,myPosition.getColumn());
            if (PieceMoves.isOnBoard(forward2)){
                if ((myPosition.getRow()==7) && (!availableMoves.isEmpty()) && (PieceMoves.isEmptySpace(forward2,board))){
                    addMove(forward2);
                }
            }
        }
//        BOTH DIAGONAL
        ChessPosition diag1 = new ChessPosition(myPosition.getRow()-1,myPosition.getColumn()-1);
        if (PieceMoves.isOnBoard(diag1)){
            if(!PieceMoves.isEmptySpace(diag1,board) && PieceMoves.isEnemyPiece(diag1,board,pieceColor)){
                addMove(diag1);
            }
        }

        ChessPosition diag2 = new ChessPosition(myPosition.getRow()-1,myPosition.getColumn()+1);
        if (PieceMoves.isOnBoard(diag2)){
            if(!PieceMoves.isEmptySpace(diag2,board) && PieceMoves.isEnemyPiece(diag2,board,pieceColor)){
                addMove(diag2);
            }
        }
        return availableMoves;
    }
}
