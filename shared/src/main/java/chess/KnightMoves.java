package chess;

import java.util.ArrayList;
import java.util.Collection;


public class KnightMoves {
    private ChessGame.TeamColor teamColor;
    private ChessBoard board;
    private ChessPosition position;
    private Collection<ChessMove> availableMoves = new ArrayList<>();

    public KnightMoves(ChessBoard board, ChessPosition myPosition){
        this.teamColor = board.getPiece(myPosition).getTeamColor();
        this.board = board;
        this.position = myPosition;
    }

    private boolean checkOnBoard(ChessPosition position){
        if ((1<=position.getRow()) && (position.getRow()<=8) && (1<=position.getColumn()) && (position.getColumn()<=8)){
            return true;
        }
        return false;
    }

    private boolean checkNewPosition(ChessPosition position, ChessBoard board){
        if (board.getPiece(position) == null){
            return true;
        }
        else if (board.getPiece(position).getTeamColor() != teamColor){
            return true;
        }
        return false;
    }


    private void calculation(ChessPosition newPosition){
        // PUT THEM IN THE CHECK VALID COORDINATE
        if (checkOnBoard(newPosition)){
            //  IF THIS RETURNS TRUE, CHECK IF THERE IS A PIECE AND ITS COLOR
            if (checkNewPosition(newPosition, board)){
                    // NOW ADD THIS NEW MOVE TO AVAILABLE MOVES
                ChessMove newMove = new ChessMove(position,newPosition,null);
                availableMoves.add(newMove);
            }
        }
    }

    public Collection<ChessMove> calculator(){
//        MAKE A LIST OF ALL POSSIBLE MOVES FOR KNIGHT
        ChessPosition position1 = new  ChessPosition(position.getRow() + 2, position.getColumn() + 1);
        ChessPosition position2 = new  ChessPosition(position.getRow() + 2, position.getColumn() - 1);
        ChessPosition position3 = new  ChessPosition(position.getRow() + 1, position.getColumn() + 2);
        ChessPosition position4 = new  ChessPosition(position.getRow() + 1, position.getColumn() - 2);
        ChessPosition position5 = new  ChessPosition(position.getRow() - 2, position.getColumn() + 1);
        ChessPosition position6 = new  ChessPosition(position.getRow() - 2, position.getColumn() - 1);
        ChessPosition position7 = new  ChessPosition(position.getRow() - 1, position.getColumn() + 2);
        ChessPosition position8 = new  ChessPosition(position.getRow() - 1, position.getColumn() - 2);

//        CHECK/ADD ALL AVAILABLE MOVES
        calculation(position1);
        calculation(position2);
        calculation(position3);
        calculation(position4);
        calculation(position5);
        calculation(position6);
        calculation(position7);
        calculation(position8);

        return availableMoves;
    }

}
