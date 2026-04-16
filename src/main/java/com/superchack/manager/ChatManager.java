package com.superchack.manager;

import com.superchack.network.P2PClient;
import com.superchack.network.P2PServer;
import com.superchack.validation.IPValidator;

public class ChatManager {

    public static void startServer(int port) {
        new P2PServer().start(port, null);
    }

    public static void startClient(String address) {
        String ip = extractIP(address);
        int port = extractPort(address);

        if (ip == null || port == -1 || !IPValidator.isValidIP(ip)) {
            System.out.println("❌ Неверный формат адреса. Используйте IP:порт");
            return;
        }

        new P2PClient().connect(ip, port, null);
    }

    private static String extractIP(String address) {
        String[] parts = address.split(":");
        return parts.length == 2 ? parts[0] : null;
    }

    private static int extractPort(String address) {
        try {
            String[] parts = address.split(":");
            return parts.length == 2 ? Integer.parseInt(parts[1]) : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}