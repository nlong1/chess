package websocket.messages;

import chess.ChessGame;
import com.google.gson.Gson;

public class ServerLoadGame extends ServerMessage{
    private boolean game;
    private Integer gameID;
    private ChessGame.TeamColor color;
    public ServerLoadGame(boolean game,Integer gameID,ChessGame.TeamColor color) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
        this.gameID = gameID;
        this.color = color;
    }
    public boolean hasGame(){
        return game;
    }
    public Integer getGameID(){
        return gameID;
    }
    public ChessGame.TeamColor getColor(){
        return color;
    }

}
