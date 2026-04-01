package com.superchack;

import com.superchack.validation.IPValidator;
import com.superchack.validation.PortValidator;
import com.superchack.validation.Space;
import com.superchack.validation.ComputerOSValidator;
import com.superchack.network.P2PServer;
import com.superchack.network.P2PClient;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            System.setErr(new java.io.PrintStream(System.err, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println("Ошибка установки кодировки: " + e.getMessage());
        }

        System.out.println("=================================");
        System.out.println("   Super Chack Messenger v1.0");
        System.out.println("   P2P Чат без сервера");
        System.out.println("=================================");

        Space.print();

        int port = PortValidator.findFreePort();
        if (port == -1) {
            System.out.println("❌ Нет свободных портов!");
            return;
        }

        System.out.println(IPValidator.getNetworkInfo());
        Space.print();
        ComputerOSValidator.print();
        Space.print();

        Scanner scanner = new Scanner(System.in);

        System.out.println("📋 Выберите режим:");
        System.out.println("   1. Ждать подключения друга (Я сервер)");
        System.out.println("   2. Подключиться к другу (Я клиент)");
        System.out.print("👉 Ваш выбор (1 или 2): ");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            String publicIP = IPValidator.getPublicIP();
            String localIP = IPValidator.getLocalIP();

            System.out.println("\n🎯 Твой адрес для друга:");
            System.out.println("   📡 Radmin IP: " + localIP + ":" + port);
            System.out.println("   🌍 Внешний IP: " + publicIP + ":" + port);
            System.out.println("\n📢 Отправь эти данные другу!");
            System.out.println("⚠️ НЕ ЗАКРЫВАЙ ЭТО ОКНО!");

            P2PServer server = new P2PServer();
            server.start(port, scanner);

        } else if (choice.equals("2")) {
            System.out.print("\n🔌 Введи адрес друга (IP:порт): ");
            String friendAddress = scanner.nextLine();

            String[] parts = friendAddress.split(":");
            if (parts.length == 2) {
                String friendIP = parts[0];
                try {
                    int friendPort = Integer.parseInt(parts[1]);
                    if (IPValidator.isValidIP(friendIP)) {
                        P2PClient client = new P2PClient();
                        client.connect(friendIP, friendPort, scanner);
                    } else {
                        System.out.println("❌ Неверный формат IP");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Неверный формат порта");
                }
            } else {
                System.out.println("❌ Неверный формат адреса. Используйте IP:порт");
            }
        } else {
            System.out.println("❌ Неверный выбор");
        }
    }
}