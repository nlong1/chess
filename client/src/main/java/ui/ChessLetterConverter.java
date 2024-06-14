package ui;

import chess.ChessPiece;

import java.util.HashMap;

public class ChessLetterConverter {
    private final HashMap<Character,Integer> letterToNumber = new HashMap<>();
    private final HashMap<String, ChessPiece.PieceType> stringToPieceType = new HashMap<>();
    public ChessLetterConverter(){
        letterToNumber.put('a',1);
        letterToNumber.put('b',2);
        letterToNumber.put('c',3);
        letterToNumber.put('d',4);
        letterToNumber.put('e',5);
        letterToNumber.put('f',6);
        letterToNumber.put('g',7);
        letterToNumber.put('h',8);
        stringToPieceType.put("queen", ChessPiece.PieceType.QUEEN);
        stringToPieceType.put("knight", ChessPiece.PieceType.KNIGHT);
        stringToPieceType.put("bishop", ChessPiece.PieceType.BISHOP);
        stringToPieceType.put("rook", ChessPiece.PieceType.ROOK);
    }

    public Integer getNumber(Character letter){
        return letterToNumber.get(letter);
    }

    public ChessPiece.PieceType getPieceType(String piece){
        return stringToPieceType.get(piece);
    }

}
