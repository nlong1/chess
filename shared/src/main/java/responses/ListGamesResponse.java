package responses;

import model.GameData;

import java.util.List;

public record ListGamesResponse(List<GameData> games, String message) {
}
