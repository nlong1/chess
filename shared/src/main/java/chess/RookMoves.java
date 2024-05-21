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

    private void addMove(ChessPosition newPosition){
        ChessMove newMove = new ChessMove(myPosition,newPosition,null);
        availableMoves.add(newMove);
    }

    public Collection<ChessMove> calculator(){
//        possible moving positions

//      GO TO THE RIGHT

        for (int i = 1;i<8;i++){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow(),myPosition.getColumn()+i);
            if (isOnBoard(newPosition)){
                if (isEmptySpace(newPosition)){
                    addMove(newPosition);
                }
                else if (isEnemyPiece(newPosition)){
                    addMove(newPosition);
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

//      LEFT

        for (int i = 1;i<8;i++){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow(),myPosition.getColumn()-i);
            if (isOnBoard(newPosition)){
                if (isEmptySpace(newPosition)){
                    addMove(newPosition);
                }
                else if (isEnemyPiece(newPosition)){
                    addMove(newPosition);
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

//      UP

        for (int i = 1;i<8;i++){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()+i,myPosition.getColumn());
            if (isOnBoard(newPosition)){
                if (isEmptySpace(newPosition)){
                    addMove(newPosition);
                }
                else if (isEnemyPiece(newPosition)){
                    addMove(newPosition);
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

//      DOWN

        for (int i = 1;i<8;i++){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()-i,myPosition.getColumn());
            if (isOnBoard(newPosition)){
                if (isEmptySpace(newPosition)){
                    addMove(newPosition);
                }
                else if (isEnemyPiece(newPosition)){
                    addMove(newPosition);
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

        return availableMoves;
    }
}
