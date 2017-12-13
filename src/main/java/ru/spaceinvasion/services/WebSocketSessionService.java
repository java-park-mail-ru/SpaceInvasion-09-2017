package ru.spaceinvasion.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.spaceinvasion.models.Message;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by egor on 06.11.17.
 */

@Service
public class WebSocketSessionService {
    private @NotNull Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private @NotNull ObjectMapper objectMapper;

    public WebSocketSessionService(@NotNull ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void registerUser(@NotNull Integer userId,
                             @NotNull WebSocketSession websocketSession) {
        sessions.put(userId, websocketSession);
    }

    public boolean isConnected(Integer userId) {
        return sessions.containsKey(userId) && sessions.get(userId).isOpen();
    }

    public void removeUser(Integer userId)
    {
        sessions.remove(userId);
    }

    public void closeConnection(Integer userId, CloseStatus closeStatus) {
        final WebSocketSession webSocketSession = sessions.get(userId);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            try {
                webSocketSession.close(closeStatus);
            } catch (IOException ignore) {

            }
        }
    }

    public void sendMessageToUser(Integer userId, @NotNull Message message) throws IOException {
        final WebSocketSession webSocketSession = sessions.get(userId);
        if (webSocketSession == null) {
            throw new IOException("no game websocket for user " + userId);
        }
        if (!webSocketSession.isOpen()) {
            throw new IOException("session is closed or not exsists");
        }
        try {
            webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new IOException("Unnable to send message", e);
        }
    }
}
