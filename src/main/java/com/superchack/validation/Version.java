/*
package com.superchack.validation;

import com.superchack.network.P2PClient;
import com.superchack.network.P2PServer;

import java.util.Scanner;

public class Version {

    public static void set() {
        System.out.println("=================================");
        System.out.println("   Super Chack Messenger v1.0");
        System.out.println("   P2P Чат без сервера");
        System.out.println("=================================");
        Space.print();
    }

    public static void choiseMenu(int port, Scanner scanner) {
        System.out.println("Выберите режим:");
        System.out.println("   1. Ждать подключения друга (Я сервер)");
        System.out.println("   2. Подключиться к другу (Я клиент)");
        System.out.print("Ваш выбор (1 или 2): ");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            String publicIP = IPValidator.getPublicIP();
            String localIP = IPValidator.getLocalIP();

            System.out.println("\n Твой адрес для друга:");
            System.out.println("   Radmin IP: " + localIP + ":" + port);
            System.out.println("   Внешний IP: " + publicIP + ":" + port);
            System.out.println("\nОтправь эти данные другу!");
            System.out.println("НЕ ЗАКРЫВАЙ ЭТО ОКНО!");

            P2PServer server = new P2PServer();
            server.start(port, scanner);

        } else if (choice.equals("2")) {
            System.out.print("\nВведи адрес друга (IP:порт): ");
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
                        System.out.println("Неверный формат IP");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат порта");
                }
            } else {
                System.out.println("еверный формат адреса. Используйте IP:порт");
            }
        } else {
            System.out.println("Неверный выбор");
        }
    }
}
*/


