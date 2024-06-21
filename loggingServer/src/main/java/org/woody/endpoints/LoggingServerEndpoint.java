package org.woody.endpoints;

import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.glassfish.tyrus.server.Server;

import java.io.*;

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
        if (message.matches("GET_HISTORY")){
            handleHistoryCommand(session);
        }else {
            handleDefault(session, message);
        }

    }

    private void handleHistoryCommand(Session session){
        try (BufferedReader reader = new BufferedReader(new FileReader("logs.txt"))){
            String line;
            while ((line = reader.readLine()) != null){
                session.getBasicRemote().sendText("HISTORY_" + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDefault(Session session, String message){
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
