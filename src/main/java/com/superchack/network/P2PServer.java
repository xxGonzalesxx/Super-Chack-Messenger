package com.superchack.network;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class P2PServer {

    private ServerSocket serverSocket;
    private PrintWriter out;
    private volatile boolean running = true;
    private Scanner scanner;
    private int port;

    public void start(int port, Scanner scanner) {
        this.scanner = scanner;
        this.port = port;
        try {
            System.out.println("\nОткрываем порт " + port);
            UPnPManager.openPort(port, "Super Chack Messenger");

            serverSocket = new ServerSocket(port);
            System.out.println("Ожидание подключения на порту " + port + "...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Друг подключился! IP: " + clientSocket.getInetAddress().getHostAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            startReceiving(in);
            startSending();

        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }

    private void startReceiving(BufferedReader in) {
        new Thread(() -> {
            try {
                String message;
                while (running && (message = in.readLine()) != null) {
                    System.out.println("\nДруг: " + message);
                    System.out.print("Вы: ");
                }
            } catch (IOException e) {
                if (running) System.out.println("\nСоединение потеряно");
            }
        }).start();
    }

    private void startSending() {
        new Thread(() -> {
            try {
                System.out.println("Введи сообщение (/exit для выхода):");
                while (running) {
                    String message = scanner.nextLine();
                    if (message.equalsIgnoreCase("/exit")) break;
                    if (!message.isEmpty()) out.println(message);
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
            if (serverSocket != null) serverSocket.close();
            UPnPManager.closePort(port);
            System.out.println("Соединение закрыто");
        } catch (IOException e) {
            System.err.println("Ошибка закрытия: " + e.getMessage());
        }
    }
}