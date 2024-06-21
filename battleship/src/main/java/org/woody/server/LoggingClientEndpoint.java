package org.woody.server;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;
import lombok.Getter;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.server.Server;
import org.woody.game.BattleController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint

public class LoggingClientEndpoint {
    private static final int loggingServerPort = 8001;

    @Getter
    private static Session session;

    public static void sendLoggText(String text) throws IOException {
        session.getBasicRemote().sendText(text);
    }

    public static void startClient() {
        ClientManager client = ClientManager.createClient();
        try {
            String args = String.format("ws://localhost:%s/websockets/log", loggingServerPort);
            session = client.connectToServer(LoggingClientEndpoint.class, new URI(args));
        } catch (DeploymentException | URISyntaxException | IOException e) {
            System.err.println("Не удалось подключиться к серверу логирования: " + e.getMessage());
        }
    }

    public static String getSessionId(){
        return session.getId();
    }
}
