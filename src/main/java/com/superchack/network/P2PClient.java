package com.superchack.network;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class P2PClient {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private volatile boolean running = true;
    private Scanner scanner;

    public void connect(String friendIP, int friendPort, Scanner scanner) {
        this.scanner = scanner;

        try {
            System.out.println("\n🔌 Подключение к " + friendIP + ":" + friendPort + "...");

            socket = new Socket(friendIP, friendPort);
            System.out.println("✅ Подключено к другу!");
            System.out.println("   IP друга: " + socket.getInetAddress().getHostAddress());

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            startReceiving();
            startSending();

        } catch (IOException e) {
            System.err.println("❌ Ошибка подключения: " + e.getMessage());
            System.out.println("💡 Проверь адрес и порт друга, а также настройки роутера");
        }
    }

    private void startReceiving() {
        new Thread(() -> {
            try {
                String message;
                while (running && (message = in.readLine()) != null) {
                    System.out.println("\n👤 Друг: " + message);
                    System.out.print("💬 Вы: ");
                }
            } catch (IOException e) {
                if (running) {
                    System.out.println("\n❌ Соединение с другом потеряно");
                }
            }
        }).start();
    }

    private void startSending() {
        new Thread(() -> {
            try {
                System.out.println("💬 Введи сообщение (или /exit для выхода):");

                while (running) {
                    String message = scanner.nextLine();
                    if (message.equalsIgnoreCase("/exit")) {
                        running = false;
                        break;
                    }
                    if (!message.isEmpty()) {
                        out.println(message);
                        System.out.println("📤 Отправлено: " + message);
                    }
                }
                close();
            } catch (Exception e) {
                System.err.println("Ошибка ввода: " + e.getMessage());
            }
        }).start();
    }

    public void close() {
        running = false;
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
            System.out.println("🔌 Соединение закрыто");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}