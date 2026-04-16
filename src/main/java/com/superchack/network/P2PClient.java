package com.superchack.network;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class P2PClient {

    private volatile boolean running = true;
    private PrintWriter out;
    private Scanner scanner;

    public void connect(String friendIP, int friendPort, Scanner scanner) {
        this.scanner = scanner;
        try {
            System.out.println("\nПодключение к " + friendIP + ":" + friendPort + "...");
            Socket socket = new Socket(friendIP, friendPort);
            System.out.println("Подключено! IP друга: " + socket.getInetAddress().getHostAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            startReceiving(in);
            startSending();

        } catch (IOException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
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
        if (out != null) out.close();
        System.out.println("Соединение закрыто");
    }
}