package chess;

import java.util.ArrayList;
import java.util.Collection;


public class BishopMoves {
    private ChessGame.TeamColor teamColor;
    private ChessBoard board;
    private ChessPosition position;
    private Collection<ChessMove> availableMoves = new ArrayList<>();

    public BishopMoves(ChessBoard board, ChessPosition myPosition){
        this.teamColor = board.getPiece(myPosition).getTeamColor();
        this.board = board;
        this.position = myPosition;
    }

    private boolean checkOnBoard(ChessPosition position){
        return (1 <= position.getRow()) && (position.getRow() <= 8) && (1 <= position.getColumn()) && (position.getColumn() <= 8);
    }

    private boolean checkNewPosition(ChessPosition newPosition, ChessBoard board){
        if (board.getPiece(newPosition) == null){
            return true;
        }
        return false;
    }

    private boolean checkCapture(ChessPosition newPosition, ChessBoard board){
        if (board.getPiece(newPosition).getTeamColor() != teamColor){
            return true;
        }
        return false;
    }


    public Collection<ChessMove> calculator(){

        for (int i = 1;i < 7;i++){
            ChessPosition newPosition = new ChessPosition(position.getRow() + i,position.getColumn() + i);
            if (!checkOnBoard(newPosition)){
                break;
            }
            ChessMove newMove = new ChessMove(position,newPosition,null);
            if (checkNewPosition(newPosition,board)) {
                availableMoves.add(newMove);
            }
            else if (checkCapture(newPosition,board)){
                availableMoves.add(newMove);
                break;
            }
            else{
                break;
            }
        }

        for (int i = 1;i < 7;i++){
            ChessPosition newPosition = new ChessPosition(position.getRow() - i,position.getColumn() - i);
            if (!checkOnBoard(newPosition)){
                break;
            }
            ChessMove newMove = new ChessMove(position,newPosition,null);
            if (checkNewPosition(newPosition,board)) {
                availableMoves.add(newMove);
            }
            else if (checkCapture(newPosition,board)){
                availableMoves.add(newMove);
                break;
            }
            else{
                break;
            }

        }

// Index row up
        for (int i = 1;i < 7;i++){
            ChessPosition newPosition = new ChessPosition(position.getRow() + i,position.getColumn() - i);
            if (!checkOnBoard(newPosition)){
                break;
            }
            ChessMove newMove = new ChessMove(position,newPosition,null);
            if (checkNewPosition(newPosition,board)) {
                availableMoves.add(newMove);
            }
            else if (checkCapture(newPosition,board)){
                availableMoves.add(newMove);
                break;
            }
            else{
                break;
            }
        }

// Index row down
        for (int i = 1;i < 7;i++){
            ChessPosition newPosition = new ChessPosition(position.getRow() - i,position.getColumn() + i);
            if (!checkOnBoard(newPosition)){
                break;
            }
            ChessMove newMove = new ChessMove(position,newPosition,null);
            if (checkNewPosition(newPosition,board)) {
                availableMoves.add(newMove);
            }
            else if (checkCapture(newPosition,board)){
                availableMoves.add(newMove);
                break;
            }
            else{
                break;
            }
        }

        return availableMoves;
    }

}
