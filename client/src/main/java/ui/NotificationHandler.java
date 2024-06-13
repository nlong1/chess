package ui;

import websocket.messages.*;

public interface NotificationHandler {
    void notify(String message);
}