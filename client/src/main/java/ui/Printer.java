package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.ArrayList;
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
    public static final String darkGreyBackground = EscapeSequences.SET_BG_COLOR_DARK_GREY;
    public static final String greenBackground = EscapeSequences.SET_BG_COLOR_GREEN;
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

    private void createHeader(){
        header.add(darkGreyBackground + empty + empty + empty);
        header.add(darkGreyBackground + blackText + empty + "a" + empty);
        header.add(darkGreyBackground + blackText + empty + "b" + empty);
        header.add(darkGreyBackground + blackText + empty + "c" + empty);
        header.add(darkGreyBackground + blackText + empty + "d" + empty);
        header.add(darkGreyBackground + blackText + empty + "e" + empty);
        header.add(darkGreyBackground + blackText + empty + "f" + empty);
        header.add(darkGreyBackground + blackText + empty + "g" + empty);
        header.add(darkGreyBackground + blackText + empty + "h" + empty);
    }


    private String getBackgroundColor(int row, int col){
        if (row % 2 == 0) {
            if (col % 2 == 1){
                return greenBackground;
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
                return greenBackground;
            }
        }
    }

    private void printWhiteBoard(){
        for (int i=0;i<header.size();i++){
            System.out.print(header.get(i));
        }
        System.out.println();
        for (ArrayList<String> item : rows){
            for (String square : item){
                System.out.print(square);
            }
            System.out.println();
        }
    }
    
    public void printBoard(ChessBoard board,ChessGame.TeamColor color){
        createRows(board);
        createHeader();
        if (color == ChessGame.TeamColor.WHITE){
            printWhiteBoard();
        }
//        else{
//            printBlackBoard()
//        }

    }

    private void createRows(ChessBoard board) {
        for (int i = 1; i < 8; i++){
            for (int j = 0; j <= 9; j++) {

                if (j == 0 || j == 9){
                    rows.get(i).add(darkGreyBackground + blackText+ empty + String.valueOf(i) + empty);
                }
                else{
                    rows.get(i).add(getBackgroundColor(i,j) + empty + getChessPieceString(board,i,j) + empty);
                }

            }
        }
    }

    private String getChessPieceString(ChessBoard board, int i, int j) {
        ChessPosition chessPosition = new ChessPosition(i, j);
        ChessPiece chessPiece = board.getPiece(chessPosition);
        if (chessPiece == null){
            return empty;
        }
        if (chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            return getPieceStringWhite(chessPiece);
        }
        return getPieceStringBlack(chessPiece);
    }

    private static String getPieceStringWhite(ChessPiece chessPiece) {
        switch (chessPiece.getPieceType()){
            case KING:
                return whiteKing;
            case QUEEN:
                return whiteQueen;
            case BISHOP:
                return whiteBishop;
            case KNIGHT:
                return whiteKnight;
            case ROOK:
                return whiteRook;
            case PAWN:
                return whitePawn;
            default:
                return empty;
        }
    }

    private static String getPieceStringBlack(ChessPiece chessPiece) {
        switch (chessPiece.getPieceType()){
            case KING:
                return blackKing;
            case QUEEN:
                return blackQueen;
            case BISHOP:
                return blackBishop;
            case KNIGHT:
                return blackKnight;
            case ROOK:
                return blackRook;
            case PAWN:
                return blackPawn;
            default:
                return empty;
        }
    }

}
