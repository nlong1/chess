package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType pieceType;
    private Collection<ChessMove> emptymoves = new ArrayList<>();
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.pieceType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && pieceType == that.pieceType && Objects.equals(emptymoves, that.emptymoves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType, emptymoves);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch (this.getPieceType()) {

            case PieceType.KNIGHT:
                KnightMoves knight = new KnightMoves(board, myPosition);
                return knight.calculator();

            case PieceType.KING:
                KingMoves king = new KingMoves(board, myPosition);
                return king.calculator();

            case PieceType.PAWN:
                PawnMoves pawn = new PawnMoves(board,myPosition);
                return pawn.calculator();

            default:
                return emptymoves;
        }
    }
}
