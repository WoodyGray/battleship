package org.woody.server;

import jakarta.websocket.*;
import lombok.Getter;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.server.Server;
import org.woody.game.BattleController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
public class LoggingClientEndpoint {
    private static final int loggingServerPort = 8001;

    @Getter
    private static Session session;

    @OnMessage
    public void onMessage(String message, Session session){
        if (message.matches("HISTORY_")){
            handleHistoryMessage(message);
        }else {
            System.out.println(message);
        }
    }

    private void handleHistoryMessage(String message){
        System.out.println("Список всех законченных игр:");
        System.out.println(message);
    }

    public static void sendLoggText(String text) throws IOException {
        session.getBasicRemote().sendText(text);
    }

    public static void sendHistoryCommand() throws IOException {
        session.getBasicRemote().sendText("GET_HISTORY");
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
