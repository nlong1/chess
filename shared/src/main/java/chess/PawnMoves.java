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

    private boolean checkOnBoard(ChessPosition newPosition){
        if ((1<=newPosition.getRow()) && (newPosition.getRow()<=8) && (1<=newPosition.getColumn()) && (newPosition.getColumn()<=8)){
            return true;
        }
        return false;
    }

    private boolean checkNewPosition(ChessPosition newPosition, ChessBoard board){
        if (board.getPiece(newPosition) == null){
            return true;
        }
        return false;
    }

    private boolean checkDiagPosition(ChessPosition newPosition){
        if ((board.getPiece(newPosition) == null) || (board.getPiece(newPosition).getTeamColor() == teamColor)){
            return false;
        }
        else {
            if (newPosition.getRow()==8 || newPosition.getRow()==1) {
                ChessMove newMove1 = new ChessMove(position,newPosition, ChessPiece.PieceType.BISHOP);
                availableMoves.add(newMove1);
                ChessMove newMove2 = new ChessMove(position,newPosition, ChessPiece.PieceType.ROOK);
                availableMoves.add(newMove2);
                ChessMove newMove3 = new ChessMove(position,newPosition, ChessPiece.PieceType.KNIGHT);
                availableMoves.add(newMove3);
                ChessMove newMove4 = new ChessMove(position,newPosition, ChessPiece.PieceType.QUEEN);
                availableMoves.add(newMove4);
            }
            else{
                ChessMove newmove = new ChessMove(position, newPosition, null);
                availableMoves.add(newmove);
            }
            return true;
        }
    }

    private void colorCalculation(ChessPosition newPosition,int color) {
        // PUT THEM IN THE CHECK VALID COORDINATE
        if (checkOnBoard(newPosition)) {
            //  IF THIS RETURNS TRUE, CHECK IF THERE IS A PIECE AND ITS COLOR
            if (checkNewPosition(newPosition, board)) {
                // NOW ADD THIS NEW MOVE TO AVAILABLE MOVES
                if (newPosition.getRow() == color) {
                    ChessMove newMove1 = new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP);
                    availableMoves.add(newMove1);
                    ChessMove newMove2 = new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK);
                    availableMoves.add(newMove2);
                    ChessMove newMove3 = new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT);
                    availableMoves.add(newMove3);
                    ChessMove newMove4 = new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN);
                    availableMoves.add(newMove4);
                } else {
                    ChessMove newMove = new ChessMove(position, newPosition, null);
                    availableMoves.add(newMove);
//
                }
            }
        }
    }

        public Collection<ChessMove> calculator () {

            switch (teamColor) {

                case ChessGame.TeamColor.WHITE:
                    //                1 front
                    ChessPosition position5 = new ChessPosition(position.getRow() + 1, position.getColumn());
                    colorCalculation(position5, 8);
//                2 in front
                    ChessPosition position6 = new ChessPosition(position.getRow() + 2, position.getColumn());
                    if ((position.getRow() == 2) && (!availableMoves.isEmpty())) {
                        colorCalculation(position6, 8);
                    }
//                1st diagonal
                    ChessPosition position7 = new ChessPosition(position.getRow() + 1, position.getColumn() - 1);
                    if (checkOnBoard(position7)) {
                        checkDiagPosition(position7);
                    }

                    ChessPosition position8 = new ChessPosition(position.getRow() + 1, position.getColumn() + 1);
                    if (checkOnBoard(position8)) {
                        checkDiagPosition(position8);
                    }

                case ChessGame.TeamColor.BLACK:
//                1 front
                    ChessPosition position1 = new ChessPosition(position.getRow() - 1, position.getColumn());
                    colorCalculation(position1, 1);
//                2 in front
                    ChessPosition position2 = new ChessPosition(position.getRow() - 2, position.getColumn());
                    if ((position.getRow() == 7) && (!availableMoves.isEmpty())) {
                        colorCalculation(position2, 1);
                    }
//                1st diagonal
                    ChessPosition position3 = new ChessPosition(position.getRow() - 1, position.getColumn() - 1);
                    if (checkOnBoard(position3)) {
                        checkDiagPosition(position3);
                    }

                    ChessPosition position4 = new ChessPosition(position.getRow() - 1, position.getColumn() + 1);
                    if (checkOnBoard(position4)) {
                        checkDiagPosition(position4);
                    }
            }


            return availableMoves;
        }

    }

