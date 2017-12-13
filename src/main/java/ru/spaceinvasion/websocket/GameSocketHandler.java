package ru.spaceinvasion.websocket;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.spaceinvasion.models.Message;
import ru.spaceinvasion.models.User;
import ru.spaceinvasion.services.UserService;
import ru.spaceinvasion.services.WebSocketSessionService;
import ru.spaceinvasion.utils.Exceptions;

import javax.validation.constraints.NotNull;

import java.io.IOException;

import static org.springframework.web.socket.CloseStatus.SERVER_ERROR;

public class GameSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameSocketHandler.class);
    private static final CloseStatus ACCESS_DENIED = new CloseStatus(4500, "Not logged in. Access denied");

    @NotNull
    private final UserService userService;
    @NotNull
    private final WebSocketSessionService webSocketSessionService;
    @NotNull
    private final ObjectMapper objectMapper;
    @NotNull
    private final MessageHandlerContainer messageHandlerContainer;


    public GameSocketHandler(UserService userService,
                             WebSocketSessionService webSocketSessionService,
                             ObjectMapper objectMapper,
                             MessageHandlerContainer messageHandlerContainer) {
        this.userService = userService;
        this.webSocketSessionService = webSocketSessionService;
        this.objectMapper = objectMapper;
        this.messageHandlerContainer = messageHandlerContainer;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        final Integer userId = (Integer)webSocketSession.getAttributes().get("user");
        try {
            userService.getUser(userId);
            if(webSocketSessionService.isConnected(userId)) {
                return;
            }
        } catch (Exceptions.NotFoundUser e) {
            LOGGER.warn("User requested web socket is not registred or not logged in. Openning websocket session is denied.");
            closeSessionSilently(webSocketSession, ACCESS_DENIED);
            return;
        }
        webSocketSessionService.registerUser(userId, webSocketSession);
    }

    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) {
        if (!webSocketSession.isOpen()) {
            return;
        }
        final Integer userId = (Integer)webSocketSession.getAttributes().get("user");
        final User user;
        try {
            user = userService.getUser(userId);
        } catch (Exceptions.NotFoundUser e) {
            closeSessionSilently(webSocketSession, ACCESS_DENIED);
            return;
        }
        collectMessage(user, message);
    }

    private void collectMessage(User user, TextMessage text) {
        final Message message;
        try {
            message = objectMapper.readValue(text.getPayload(), Message.class);
        } catch (IOException ex) {
            LOGGER.error("wrong json format at game response", ex);
            return;
        }
        try {
            messageHandlerContainer.collect(message, user.getId());
        } catch (Exceptions.HandleException e) {
            LOGGER.error("Can't handle message of type " + message.getClass().getName() + " with content: " + text, e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        LOGGER.warn("Websocket transport problem", throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        final Integer userId = (Integer)webSocketSession.getAttributes().get("user");
        if (userId == null) {
            LOGGER.warn("User disconnected but his session was not found (closeStatus=" + closeStatus + ')');
            return;
        }
        webSocketSessionService.removeUser(userId);
    }


    private void closeSessionSilently(WebSocketSession session, CloseStatus closeStatus) {
        final CloseStatus status = closeStatus == null ? SERVER_ERROR : closeStatus;
        try {
            session.close(status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
