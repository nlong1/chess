package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


public class QueenMoves {
    private ChessBoard board;
    private ChessPosition position;
    private Collection<ChessMove> availableMoves = new HashSet<>();

    public QueenMoves(ChessBoard board, ChessPosition myPosition){
        this.board = board;
        this.position = myPosition;
    }

    public Collection<ChessMove> calculator(){
//        ADD ROOK AND BISHOP TOGETHER

        RookMoves rook = new RookMoves(board,position);
        availableMoves = rook.calculator();

        BishopMoves bishop = new BishopMoves(board,position);
        availableMoves.addAll(bishop.calculator());

        return availableMoves;
    }

}
