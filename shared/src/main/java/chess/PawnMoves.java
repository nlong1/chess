package chess;

import java.util.ArrayList;
import java.util.Collection;


public class PawnMoves {
    private ChessGame.TeamColor teamColor;
    private ChessBoard board;
    private ChessPosition position;
    private Collection<ChessMove> availableMoves = new ArrayList<>();

    public PawnMoves(ChessBoard board, ChessPosition myPosition){
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

    private void checkDiagPosition(ChessPosition newPosition){
        if (board.getPiece(newPosition) == null){
            return;
        }
        else if (board.getPiece(position).getTeamColor() != teamColor){
            ChessMove newmove = new ChessMove(position,newPosition,null);
            availableMoves.add(newmove);
//            MUST IMPLEMENT PROMOTION
        }
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
//        MAKE A LIST OF ALL POSSIBLE MOVES FOR PAWN
        switch(teamColor) {
            case ChessGame.TeamColor.BLACK:
//                1 front
                ChessPosition position1 = new  ChessPosition(position.getRow(), position.getColumn() - 1);
                calculation(position1);
//                2 in front
                ChessPosition position2 = new  ChessPosition(position.getRow(), position.getColumn() - 2);
                if ((position.getRow() == 7) && (!availableMoves.isEmpty())){
                    calculation(position2);
                }
//                1st diagonal
                ChessPosition position3 = new  ChessPosition(position.getRow() - 1, position.getColumn() - 1);
                if (checkOnBoard(position3)){
                    checkDiagPosition(position3);
                }
                ChessPosition position4 = new  ChessPosition(position.getRow() + 1, position.getColumn() - 2);

            case ChessGame.TeamColor.WHITE:

        }



        return availableMoves;
    }

}
