package request;

import chess.ChessGame;

public record JoinGameRequest(String color,int gameId) {
}
