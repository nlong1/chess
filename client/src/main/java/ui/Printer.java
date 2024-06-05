package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.ArrayList;
import java.util.Objects;

import ui.EscapeSequences;

public class Printer {

    public static final String whiteKing = EscapeSequences.WHITE_KING;
    public static final String whiteQueen = EscapeSequences.WHITE_QUEEN;
    public static final String whiteKnight = EscapeSequences.WHITE_KNIGHT;
    public static final String whiteRook = EscapeSequences.WHITE_ROOK;
    public static final String whitePawn = EscapeSequences.WHITE_PAWN;
    public static final String whiteBishop = EscapeSequences.WHITE_BISHOP;
    public static final String blackKing = EscapeSequences.BLACK_KING;
    public static final String blackQueen = EscapeSequences.BLACK_QUEEN;
    public static final String blackBishop = EscapeSequences.BLACK_BISHOP;
    public static final String blackKnight = EscapeSequences.BLACK_KNIGHT;
    public static final String blackRook = EscapeSequences.BLACK_ROOK;
    public static final String blackPawn = EscapeSequences.BLACK_PAWN;
    public static final String empty = EscapeSequences.EMPTY;

    public static final String blackText = EscapeSequences.SET_TEXT_COLOR_BLACK;
    public static final String darkGreyText = EscapeSequences.SET_TEXT_COLOR_DARK_GREY;
    public static final String lightGreyText = EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY;
    public static final String blueText = EscapeSequences.SET_TEXT_COLOR_BLUE;
    public static final String darkGreyBackground = EscapeSequences.SET_BG_COLOR_DARK_GREY;
    public static final String blueBackground = EscapeSequences.SET_BG_COLOR_BLUE;
    public static final String lightGreyBackground = EscapeSequences.SET_BG_COLOR_LIGHT_GREY;

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
                return blueBackground;
            }
            else {
                return lightGreyBackground;
            }
        }
        else{
            if (col % 2 == 1){
                return lightGreyBackground;
            }
            else{
                return blueBackground;
            }
        }
    }

    private String getChessPieceString(ChessBoard board, int i, int j) {
        ChessPosition chessPosition = new ChessPosition(i, j);
        ChessPiece chessPiece = board.getPiece(chessPosition);
        if (chessPiece == null){
            if (Objects.equals(getBackgroundColor(i, j), lightGreyBackground)){
                return lightGreyText + blackPawn;
            }
            return blueText + blackPawn;
        }
        if (chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            return getPieceStringWhite(chessPiece);
        }
        return getPieceStringBlack(chessPiece);
    }

    private static String getPieceStringWhite(ChessPiece chessPiece) {
        return switch (chessPiece.getPieceType()) {
            case KING -> whiteKing;
            case QUEEN -> whiteQueen;
            case BISHOP -> whiteBishop;
            case KNIGHT -> whiteKnight;
            case ROOK -> whiteRook;
            case PAWN -> whitePawn;
            default -> empty;
        };
    }

    private static String getPieceStringBlack(ChessPiece chessPiece) {
        return switch (chessPiece.getPieceType()) {
            case KING -> blackKing;
            case QUEEN -> blackQueen;
            case BISHOP -> blackBishop;
            case KNIGHT -> blackKnight;
            case ROOK -> blackRook;
            case PAWN -> blackPawn;
            default -> empty;
        };
    }

    private void createHeader(){
        header.add(darkGreyBackground + empty);
        header.add(darkGreyBackground + blackText + "   A ");
        header.add(darkGreyBackground + blackText + " B");
        header.add(darkGreyBackground + blackText + "  C ");
        header.add(darkGreyBackground + blackText + "  D ");
        header.add(darkGreyBackground + blackText + "  E ");
        header.add(darkGreyBackground + blackText + " F ");
        header.add(darkGreyBackground + blackText + "  G ");
        header.add(darkGreyBackground + blackText + "  H ");
        header.add(darkGreyBackground + empty);
    }

    private void createRows(ChessBoard board) {

        for (int i = 1; i <= 8; i++){
            for (int j = 0; j <= 9; j++) {

                if (j == 0 || j == 9){
                    rows.get(i-1).add(darkGreyBackground + blackText + "  " + String.valueOf(i) + "  ");
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
        if (color == ChessGame.TeamColor.WHITE){
            printWhiteBoard();
        }
        else{
            printBlackBoard();
        }

    }
}
