package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopMoves {
    private ChessGame.TeamColor pieceColor;
    private ChessBoard board;
    private ChessPosition myPosition;
    private Collection<ChessMove> availableMoves = new HashSet<ChessMove>();

    public BishopMoves(ChessBoard board, ChessPosition myPosition){
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

    private boolean enemyPieceCheck(ChessPosition newPosition){
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

//      GO TO THE UP/RIGHT

        for (int i = 1;i<8;i++){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()+i,myPosition.getColumn()+i);
            if (onBoardCheck(newPosition)){
                if (emptySpaceCheck(newPosition)){
                    addMove(newPosition);
                }
                else if (enemyPieceCheck(newPosition)){
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

//      UP/LEFT

        for (int i = 1;i<8;i++){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()+i,myPosition.getColumn()-i);
            if (onBoardCheck(newPosition)){
                if (emptySpaceCheck(newPosition)){
                    addMove(newPosition);
                }
                else if (enemyPieceCheck(newPosition)){
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

//      DOWN/RIGHT

        for (int i = 1;i<8;i++){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()-i,myPosition.getColumn()+i);
            if (onBoardCheck(newPosition)){
                if (emptySpaceCheck(newPosition)){
                    addMove(newPosition);
                }
                else if (enemyPieceCheck(newPosition)){
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

//      DOWN/LEFT

        for (int i = 1;i<8;i++){
            ChessPosition newPosition = new ChessPosition(myPosition.getRow()-i,myPosition.getColumn()-i);
            if (onBoardCheck(newPosition)){
                if (emptySpaceCheck(newPosition)){
                    addMove(newPosition);
                }
                else if (enemyPieceCheck(newPosition)){
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
