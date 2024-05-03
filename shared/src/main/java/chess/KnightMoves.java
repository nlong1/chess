package chess;

import java.util.Collection;

public class KnightMoves {
    private Collection<ChessMove> available_moves;
    private ChessPiece.PieceType piece_type;
    private ChessBoard board;

    public KnightMoves(ChessBoard board, ChessPosition myPosition){
        this.piece_type = board.getPiece(myPosition).getPieceType();
        this.board = board;
    }

    public Collection<ChessMove> Calculator(){

        return available_moves;
    }

}
