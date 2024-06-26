package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private TeamColor teamColor = TeamColor.WHITE;
    public GameStatus gameStatus;

    public ChessGame() {
        this.board = new ChessBoard();
        board.resetBoard();
        this.gameStatus = GameStatus.IN_PROGRESS;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }
    
    /**
     * @param color the color of friendly team
     * @return Enemy team color
     */
    public TeamColor getEnemyTeamColor(TeamColor color){
        if (color == TeamColor.WHITE){
            return TeamColor.BLACK;
        }
        return TeamColor.WHITE;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    public enum GameStatus {
        IN_PROGRESS,
        WHITE_WON,
        BLACK_WON,
        STALEMATE
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        } else {
            Collection<ChessMove> pieceMoves = piece.pieceMoves(board, startPosition);
            Collection<ChessMove> validMoves = new HashSet<ChessMove>();
//            if a john causes check
            for (ChessMove move : pieceMoves) {
//                create clone board/game simulation
                ChessBoard boardClone = (ChessBoard) board.clone();
                ChessGame gameSimulation = new ChessGame();
                gameSimulation.setBoard(boardClone);
                gameSimulation.setTeamTurn(piece.getTeamColor());
//                apply move to simulation board
                gameSimulation.getBoard().applyMove(move);
                if (!gameSimulation.isInCheck(piece.getTeamColor())) {
                    validMoves.add(move);
                }
            }
            return validMoves;
        }
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        if (board.getPiece(startPosition) == null || board.getPiece(startPosition).getTeamColor() != teamColor){
            throw new InvalidMoveException();
        }

        Collection<ChessMove> moves = this.validMoves(startPosition);
        if (!moves.contains(move)){
            throw new InvalidMoveException("        invalid move");
        }

        board.applyMove(move);
        if (teamColor == TeamColor.BLACK){
            teamColor = TeamColor.WHITE;
        }
        else {
            teamColor = TeamColor.BLACK;
        }
    }

    /**
     * @param color the color of the friendly team
     * @return Enemy team's possible moves
     */
    public Collection<ChessMove> enemyTeamMoves(TeamColor color){
        TeamColor enemyTeamColor = getEnemyTeamColor(color);
        return friendlyTeamMoves(enemyTeamColor);

    }

    /**
     * @param color the color of the friendly team
     * @return Friendly team's possible moves
     */
    public Collection<ChessMove> friendlyTeamMoves(TeamColor color){
        Collection<ChessMove> possibleEnemyTeamMoves = new HashSet<ChessMove>();
//        LOOPS THROUGH ALL POSITIONS ON THE BOARD TO FIND PIECES
//        IF THESE PIECES ARE OF THE ENEMY TEAM COLOR, ADD THEIR MOVES TO THE POSSIBLE MOVES
        for (int i = 1 ; i < 9 ; i++){
            for (int j = 1; j < 9 ; j++){
                ChessPosition position = new ChessPosition(i,j);
                ChessPiece piece = board.getPiece(position);
                if (piece != null){
                    if (piece.getTeamColor() ==  color){
                        Collection<ChessMove> pieceMoves = piece.pieceMoves(board,position);
                        if (pieceMoves != null){
                            possibleEnemyTeamMoves.addAll(pieceMoves);
                        }
                    }
                }
            }
        }
        return possibleEnemyTeamMoves;
    }
    
    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
//        CHECK IF THE END POSITION OF ANY ENEMY PIECE IS THE KING OF THE FRIENDLY TEAM
        Collection<ChessMove> enemyTeamMoves = enemyTeamMoves(teamColor);
        for (ChessMove move : enemyTeamMoves){
            ChessPosition position = move.getEndPosition();
            ChessPiece piece = board.getPiece(position);
            if (piece != null){
                if (piece.getPieceType() == ChessPiece.PieceType.KING){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (isInCheck(teamColor)){
            Collection<ChessMove> friendlyMoves = friendlyTeamMoves(teamColor);
            for (ChessMove move : friendlyMoves){
                ChessBoard cloneBoard = (ChessBoard) board.clone();
                ChessGame simulationGame = new ChessGame();
                simulationGame.setBoard(cloneBoard);
                simulationGame.setTeamTurn(teamColor);
                simulationGame.getBoard().applyMove(move);
                if (!simulationGame.isInCheck(teamColor)){
                    return false;
                }
            }
            return true;
        }
        return false;

    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (!isInCheck(teamColor)){
            HashSet<ChessMove> allMoves = new HashSet<>();
            for (int i = 1 ; i < 9 ; i++){
                for (int j = 1; j < 9 ; j++){
                    ChessPosition position = new ChessPosition(i,j);
                    ChessPiece piece = board.getPiece(position);
                    if (piece != null){
                        if (piece.getTeamColor() == teamColor){
                            Collection<ChessMove> pieceMoves = validMoves(position);
                            allMoves.addAll(pieceMoves);

                        }
                    }
                }
            }
            if (!allMoves.isEmpty()){
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
