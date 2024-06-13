package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.ArrayList;
import java.util.Objects;

public class Printer {

    public static final String WHITE_KING = EscapeSequences.WHITE_KING;
    public static final String WHITE_QUEEN = EscapeSequences.WHITE_QUEEN;
    public static final String WHITE_KNIGHT = EscapeSequences.WHITE_KNIGHT;
    public static final String WHITE_ROOK = EscapeSequences.WHITE_ROOK;
    public static final String WHITE_PAWN = EscapeSequences.WHITE_PAWN;
    public static final String WHITE_BISHOP = EscapeSequences.WHITE_BISHOP;
    public static final String BLACK_KING = EscapeSequences.BLACK_KING;
    public static final String BLACK_QUEEN = EscapeSequences.BLACK_QUEEN;
    public static final String BLACK_BISHOP = EscapeSequences.BLACK_BISHOP;
    public static final String BLACK_KNIGHT = EscapeSequences.BLACK_KNIGHT;
    public static final String BLACK_ROOK = EscapeSequences.BLACK_ROOK;
    public static final String BLACK_PAWN = EscapeSequences.BLACK_PAWN;
    public static final String EMPTY = EscapeSequences.EMPTY;

    public static final String BLACK_TEXT = EscapeSequences.SET_TEXT_COLOR_BLACK;
    public static final String DARK_GREY_TEXT = EscapeSequences.SET_TEXT_COLOR_DARK_GREY;
    public static final String LIGHT_GREY_TEXT = EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY;
    public static final String BLUE_TEXT = EscapeSequences.SET_TEXT_COLOR_BLUE;
    public static final String DARK_GREY_BACKGROUND = EscapeSequences.SET_BG_COLOR_DARK_GREY;
    public static final String BLUE_BACKGROUND = EscapeSequences.SET_BG_COLOR_BLUE;
    public static final String LIGHT_GREY_BACKGROUND = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
    public static final String RESET_BACKGROUND_COLOR = EscapeSequences.RESET_BG_COLOR;
    public static final String WHITE_TEXT = EscapeSequences.SET_TEXT_COLOR_WHITE;

    ArrayList<String> header = new ArrayList<>();

    ArrayList<String> row1 = new ArrayList<>();
    ArrayList<String> row2 = new ArrayList<>();
    ArrayList<String> row3 = new ArrayList<>();
    ArrayList<String> row4 = new ArrayList<>();
    ArrayList<String> row5 = new ArrayList<>();
    ArrayList<String> row6 = new ArrayList<>();
    ArrayList<String> row7 = new ArrayList<>();
    ArrayList<String> row8 = new ArrayList<>();

    private final ArrayList<ArrayList<String>> rows = new ArrayList<>();

    public Printer(){
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);
        rows.add(row6);
        rows.add(row7);
        rows.add(row8);
    }

    private String getBackgroundColor(int row, int col){
        if (row % 2 == 0) {
            if (col % 2 == 1){
                return BLUE_BACKGROUND;
            }
            else {
                return LIGHT_GREY_BACKGROUND;
            }
        }
        else{
            if (col % 2 == 1){
                return LIGHT_GREY_BACKGROUND;
            }
            else{
                return BLUE_BACKGROUND;
            }
        }
    }

    private String getChessPieceString(ChessBoard board, int i, int j) {
        ChessPosition chessPosition = new ChessPosition(i, j);
        ChessPiece chessPiece = board.getPiece(chessPosition);
        if (chessPiece == null){
            if (Objects.equals(getBackgroundColor(i, j), LIGHT_GREY_BACKGROUND)){
                return LIGHT_GREY_TEXT + BLACK_PAWN;
            }
            return BLUE_TEXT + BLACK_PAWN;
        }
        if (chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            return getPieceStringWhite(chessPiece);
        }
        return getPieceStringBlack(chessPiece);
    }

    private static String getPieceStringWhite(ChessPiece chessPiece) {
        return switch (chessPiece.getPieceType()) {
            case KING -> WHITE_KING;
            case QUEEN -> WHITE_QUEEN;
            case BISHOP -> WHITE_BISHOP;
            case KNIGHT -> WHITE_KNIGHT;
            case ROOK -> WHITE_ROOK;
            case PAWN -> WHITE_PAWN;
            default -> EMPTY;
        };
    }

    private static String getPieceStringBlack(ChessPiece chessPiece) {
        return switch (chessPiece.getPieceType()) {
            case KING -> BLACK_KING;
            case QUEEN -> BLACK_QUEEN;
            case BISHOP -> BLACK_BISHOP;
            case KNIGHT -> BLACK_KNIGHT;
            case ROOK -> BLACK_ROOK;
            case PAWN -> BLACK_PAWN;
            default -> EMPTY;
        };
    }

    private void createHeader(){
        header.add(DARK_GREY_BACKGROUND + EMPTY);
        header.add(DARK_GREY_BACKGROUND + BLACK_TEXT + "   A ");
        header.add(DARK_GREY_BACKGROUND + BLACK_TEXT + " B");
        header.add(DARK_GREY_BACKGROUND + BLACK_TEXT + "  C ");
        header.add(DARK_GREY_BACKGROUND + BLACK_TEXT + "  D ");
        header.add(DARK_GREY_BACKGROUND + BLACK_TEXT + "  E ");
        header.add(DARK_GREY_BACKGROUND + BLACK_TEXT + " F ");
        header.add(DARK_GREY_BACKGROUND + BLACK_TEXT + "  G ");
        header.add(DARK_GREY_BACKGROUND + BLACK_TEXT + "  H ");
        header.add(DARK_GREY_BACKGROUND + EMPTY);
    }

    private void createRows(ChessBoard board) {

        for (int i = 1; i <= 8; i++){
            for (int j = 0; j <= 9; j++) {

                if (j == 0 || j == 9){
                    rows.get(i-1).add(DARK_GREY_BACKGROUND + BLACK_TEXT + "  " + String.valueOf(i) + "  ");
                }
                else{
                    rows.get(i-1).add(getBackgroundColor(i,j) + getChessPieceString(board,i,j));
                }

            }
        }
    }

    private void printWhiteBoard(){
        for (String string : header) {
            System.out.print(string);
        }
        System.out.println();
        for (ArrayList<String> row : rows.reversed()){
            for (String square : row){
                System.out.print(square);
            }
            System.out.println();
        }
        for (String s : header) {
            System.out.print(s);
        }
        System.out.println();
    }

    private void printBlackBoard(){
        for (String string : header.reversed()) {
            System.out.print(string);
        }
        System.out.println();
        for (ArrayList<String> row : rows){
            for (String square : row.reversed()){
                System.out.print(square);
            }
            System.out.println();
        }
        for (String s : header.reversed()) {
            System.out.print(s);
        }
        System.out.println();
    }

    public void printBoard(ChessBoard board,ChessGame.TeamColor color){
        createRows(board);
        createHeader();
        if (color == ChessGame.TeamColor.BLACK){
            printBlackBoard();
        }
        else{
            printWhiteBoard();
        }
        System.out.println(RESET_BACKGROUND_COLOR+WHITE_TEXT);

    }
}
