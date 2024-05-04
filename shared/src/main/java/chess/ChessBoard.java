package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] boardSquares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        boardSquares[position.getRow()-1][position.getColumn()-1] = piece;
    }
    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return boardSquares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
//        POSITIONS
        ChessPosition position11 = new ChessPosition(1,1);
        ChessPosition position12 = new ChessPosition(1,2);
        ChessPosition position13 = new ChessPosition(1,3);
        ChessPosition position14 = new ChessPosition(1,4);
        ChessPosition position15 = new ChessPosition(1,5);
        ChessPosition position16 = new ChessPosition(1,6);
        ChessPosition position17 = new ChessPosition(1,7);
        ChessPosition position18 = new ChessPosition(1,8);

        ChessPosition position21 = new ChessPosition(2,1);
        ChessPosition position22 = new ChessPosition(2,2);
        ChessPosition position23 = new ChessPosition(2,3);
        ChessPosition position24 = new ChessPosition(2,4);
        ChessPosition position25 = new ChessPosition(2,5);
        ChessPosition position26 = new ChessPosition(2,6);
        ChessPosition position27 = new ChessPosition(2,7);
        ChessPosition position28 = new ChessPosition(2,8);

        ChessPosition position71 = new ChessPosition(7,1);
        ChessPosition position72 = new ChessPosition(7,2);
        ChessPosition position73 = new ChessPosition(7,3);
        ChessPosition position74 = new ChessPosition(7,4);
        ChessPosition position75 = new ChessPosition(7,5);
        ChessPosition position76 = new ChessPosition(7,6);
        ChessPosition position77 = new ChessPosition(7,7);
        ChessPosition position78 = new ChessPosition(7,8);

        ChessPosition position81 = new ChessPosition(8,1);
        ChessPosition position82 = new ChessPosition(8,2);
        ChessPosition position83 = new ChessPosition(8,3);
        ChessPosition position84 = new ChessPosition(8,4);
        ChessPosition position85 = new ChessPosition(8,5);
        ChessPosition position86 = new ChessPosition(8,6);
        ChessPosition position87 = new ChessPosition(8,7);
        ChessPosition position88 = new ChessPosition(8,8);

//        PIECES
        ChessPiece whiteRook = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.ROOK);
        ChessPiece whiteKnight = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KNIGHT);
        ChessPiece whiteBishop = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.BISHOP);
        ChessPiece whiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.QUEEN);
        ChessPiece whiteKing = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KING);
        ChessPiece whitePawn = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.PAWN);

        ChessPiece blackRook = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.ROOK);
        ChessPiece blackKnight = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT);
        ChessPiece blackBishop = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.BISHOP);
        ChessPiece blackQueen = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.QUEEN);
        ChessPiece blackKing = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KING);
        ChessPiece blackPawn = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.PAWN);

//        ADD PIECES TO BOARD
        addPiece(position11,whiteRook);
        addPiece(position12,whiteKnight);
        addPiece(position13,whiteBishop);
        addPiece(position14,whiteQueen);
        addPiece(position15,whiteKing);
        addPiece(position16,whiteBishop);
        addPiece(position17,whiteKnight);
        addPiece(position18,whiteRook);

        addPiece(position81,blackRook);
        addPiece(position82,blackKnight);
        addPiece(position83,blackBishop);
        addPiece(position84,blackQueen);
        addPiece(position85,blackKing);
        addPiece(position86,blackBishop);
        addPiece(position87,blackKnight);
        addPiece(position88,blackRook);

        addPiece(position21,whitePawn);
        addPiece(position22,whitePawn);
        addPiece(position23,whitePawn);
        addPiece(position24,whitePawn);
        addPiece(position25,whitePawn);
        addPiece(position26,whitePawn);
        addPiece(position27,whitePawn);
        addPiece(position28,whitePawn);

        addPiece(position71,blackPawn);
        addPiece(position72,blackPawn);
        addPiece(position73,blackPawn);
        addPiece(position74,blackPawn);
        addPiece(position75,blackPawn);
        addPiece(position76,blackPawn);
        addPiece(position77,blackPawn);
        addPiece(position78,blackPawn);


    }
}
