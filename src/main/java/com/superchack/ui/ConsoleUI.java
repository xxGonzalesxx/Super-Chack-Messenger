package com.superchack.ui;

import com.superchack.manager.ChatManager;
import com.superchack.validation.IPValidator;
import com.superchack.validation.PortValidator;
import com.superchack.validation.ComputerOSValidator;

import java.util.Scanner;

public class ConsoleUI {

    private Scanner scanner = new Scanner(System.in);

    public void run() {
        setupEncoding();
        showHeader();

        int port = getFreePort();
        if (port == -1) return;

        showInfo();
        start(port);
    }

    private void setupEncoding() {
        System.setProperty("file.encoding", "UTF-8");
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            System.setErr(new java.io.PrintStream(System.err, true, "UTF-8"));
        } catch (Exception ignored) {}
    }

    private void showHeader() {
        System.out.println("=================================");
        System.out.println("   Super Chack Messenger v1.0");
        System.out.println("   P2P Чат без сервера");
        System.out.println("=================================\n");
    }

    private int getFreePort() {
        int port = PortValidator.findFreePort();
        if (port == -1) {
            System.out.println("❌ Нет свободных портов!");
        }
        return port;
    }

    private void showInfo() {
        System.out.println(IPValidator.getNetworkInfo() + "\n");
        ComputerOSValidator.print();
        System.out.println();
    }

    private void start(int port) {
        String mode = askMode();

        if (mode.equals("1")) {
            startServer(port);
        } else if (mode.equals("2")) {
            startClient();
        } else {
            System.out.println("❌ Неверный выбор");
        }
    }

    private String askMode() {
        System.out.println("📋 Выберите режим:");
        System.out.println("   1. Ждать подключения (сервер)");
        System.out.println("   2. Подключиться (клиент)");
        System.out.print("👉 Ваш выбор (1 или 2): ");
        return scanner.nextLine();
    }

    private void startServer(int port) {
        String localIP = IPValidator.getLocalIP();
        System.out.println("\n🎯 Твой адрес: " + localIP + ":" + port);
        System.out.println("📢 Отправь это другу!\n");

        ChatManager.startServer(port);
    }

    private void startClient() {
        System.out.print("\n🔌 Введи адрес друга (IP:порт): ");
        String address = scanner.nextLine();
        scanner.close();

        ChatManager.startClient(address);
    }
}