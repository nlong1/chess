package server;

public class ResponseException extends Exception {
    public ResponseException(int i, String message) {
        super(message);
    }
}
