package org.woody;

import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.glassfish.tyrus.server.Server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@ServerEndpoint("/log")
public class LoggingServerEndpoint {
    private BufferedWriter writer;

    public LoggingServerEndpoint(){
        try {
            writer = new BufferedWriter(new FileWriter("logs.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session){
        System.out.println("Присоединение к серверу логирования:" + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session){
        System.out.printf("Партия сессии %s%n", session.getId());
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void startServer(int port) {
        Server server;
        server = new Server("localhost", port, "/websockets", null, LoggingServerEndpoint.class);
        try {
            server.start();
            System.out.println("---сервер запущен, ждем сообщений от хостов");
            while (true) {
                Thread.onSpinWait();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            server.stop();
        }
    }
}
