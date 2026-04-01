package com.superchack.network;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class P2PServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private int port;
    private volatile boolean running = true;
    private Scanner scanner;

    public void start(int port, Scanner scanner) {
        this.port = port;
        this.scanner = scanner;

        try {
            System.out.println("\n🔓 Открываем порт " + port + " на роутере...");
            boolean portOpened = UPnPManager.openPort(port, "Super Chack Messenger");

            if (!portOpened) {
                System.out.println("⚠️ Не удалось открыть порт автоматически");
                System.out.println("💡 Возможно, нужно открыть порт вручную в настройках роутера");
            }

            serverSocket = new ServerSocket(port);
            System.out.println("📡 Ожидание подключения на порту " + port + "...");

            clientSocket = serverSocket.accept();
            System.out.println("✅ Друг подключился!");
            System.out.println("   IP друга: " + clientSocket.getInetAddress().getHostAddress());

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            startReceiving();
            startSending();

        } catch (IOException e) {
            System.err.println("❌ Ошибка сервера: " + e.getMessage());
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
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
            UPnPManager.closePort(port);
            System.out.println("🔌 Соединение закрыто");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}