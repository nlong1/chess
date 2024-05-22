package chess;

import java.util.Collection;

public class PieceMoves {
    public static boolean isOnBoard(ChessPosition newPosition){
        if ((newPosition.getRow()>=1) && (newPosition.getRow()<=8) && (newPosition.getColumn()>=1) && (newPosition.getColumn()<=8)){
            return true;
        }
        return false;
    }

    public static boolean isEmptySpace(ChessPosition newPosition, ChessBoard board){
        if (board.getPiece(newPosition) == null){
            return true;
        }
        return false;
    }

    public static boolean isEnemyPiece(ChessPosition newPosition, ChessBoard board, ChessGame.TeamColor pieceColor){
        if (board.getPiece(newPosition).getTeamColor() != pieceColor){
            return true;
        }
        return false;
    }

    public static void addMove(ChessPosition myPosition,ChessPosition newPosition, Collection<ChessMove> availableMoves){
        ChessMove newMove = new ChessMove(myPosition,newPosition,null);
        availableMoves.add(newMove);
    }

    public static void moveCalculationKnightOrKing(ChessPosition myPosition, ChessPosition newPosition, ChessBoard board, ChessGame.TeamColor pieceColor, Collection<ChessMove> availableMoves){
        if (isOnBoard(newPosition)){
            if (isEmptySpace(newPosition,board)){
                ChessMove newMove = new ChessMove(myPosition,newPosition,null);
                availableMoves.add(newMove);

            }
            else if (isEnemyPiece(newPosition,board,pieceColor)){
                ChessMove newMove = new ChessMove(myPosition,newPosition,null);
                availableMoves.add(newMove);
            }
        }
    }

    public static void lineCalculation(ChessPosition myPosition,ChessBoard board,Collection<ChessMove> availableMoves,ChessGame.TeamColor pieceColor,int rowIncrement, int colIncrement){
        for (int i = 1;i<8;i++){
        ChessPosition newPosition = new ChessPosition(myPosition.getRow() + (i * rowIncrement),myPosition.getColumn() + (i * colIncrement));
        if (PieceMoves.isOnBoard(newPosition)){
            if (PieceMoves.isEmptySpace(newPosition,board)){
                PieceMoves.addMove(myPosition,newPosition,availableMoves);
            }
            else if (PieceMoves.isEnemyPiece(newPosition,board,pieceColor)){
                PieceMoves.addMove(myPosition,newPosition,availableMoves);
                break;
            }
            else{
                break;
            }
        }
        else{
            break;
        }
    }
    }
}
