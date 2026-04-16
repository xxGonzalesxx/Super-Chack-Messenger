package com.superchack.network;

import com.superchack.gui.ChatWindow;
import javafx.application.Platform;

import java.io.*;
import java.net.*;

public class P2PServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private int port;
    private volatile boolean running = true;
    private final ChatWindow chatWindow;

    public P2PServer(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }

    public void start(int port) {
        this.port = port;

        try {
            System.out.println("\nОткрываем порт " + port + " на роутере...");
            boolean portOpened = UPnPManager.openPort(port, "Super Chack Messenger");

            if (!portOpened) {
                System.out.println("Не удалось открыть порт автоматически");
                System.out.println("Возможно, нужно открыть порт вручную в настройках роутера");
            }

            serverSocket = new ServerSocket(port);
            System.out.println("Ожидание подключения на порту " + port + "...");

            clientSocket = serverSocket.accept();
            System.out.println("Друг подключился!");
            System.out.println("IP друга: " + clientSocket.getInetAddress().getHostAddress());

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            startReceiving();

        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }

    private void startReceiving() {
        new Thread(() -> {
            try {
                String message;
                while (running && (message = in.readLine()) != null) {
                    String msg = message;  // ← копия для лямбды
                    System.out.println("\nДруг: " + msg);
                    if (chatWindow != null) {
                        Platform.runLater(() -> chatWindow.addMessage(msg));
                    }
                }
            } catch (IOException e) {
                if (running) {
                    System.out.println("\nСоединение с другом потеряно");
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        if (out != null && !message.isEmpty()) {
            out.println(message);
            System.out.println("Отправлено: " + message);
        }
    }

    public void close() {
        running = false;
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
            UPnPManager.closePort(port);
            System.out.println("Соединение закрыто");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}