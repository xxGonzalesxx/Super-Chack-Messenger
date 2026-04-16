package com.superchack.network;

import com.superchack.gui.ChatWindow;
import javafx.application.Platform;

import java.io.*;
import java.net.*;

public class P2PClient {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private volatile boolean running = true;
    private final ChatWindow chatWindow;  // ← final

    public P2PClient(ChatWindow chatWindow) {  // ← конструктор
        this.chatWindow = chatWindow;
    }

    public void connect(String friendIP, int friendPort) {  // ← убрал chatWindow
        try {
            System.out.println("\nПодключение к " + friendIP + ":" + friendPort + "...");

            socket = new Socket(friendIP, friendPort);
            System.out.println("Подключено к другу!");
            System.out.println("IP друга: " + socket.getInetAddress().getHostAddress());

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            startReceiving();

        } catch (IOException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
            System.out.println("Проверь адрес и порт друга, а также настройки роутера");
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
            if (socket != null) socket.close();
            System.out.println("Соединение закрыто");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}