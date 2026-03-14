package com.superchack.validation;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class IPValidator {

    public static String getPublicIP() {
        try {
            // Создаем HTTP клиент с таймаутом
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

            // Создаем запрос на HTTPS (безопасно!)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.ipify.org"))
                    .timeout(Duration.ofSeconds(5))
                    .build();

            // Отправляем запрос и получаем ответ
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (Exception e) {
            return "Ошибка получения внешнего IP.Возможно провайдер блокирует доступ";
        }
    }

    public static String getLocalIP() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    public static boolean isValidIP(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }

        // Разбиваем по точкам
        String[] parts = ip.split("\\.");

        // Должно быть 4 части
        if (parts.length != 4) {
            return false;
        }
        try {
            for (String part : parts) {
                int num = Integer.parseInt(part);
                // Каждая часть от 0 до 255
                if (num < 0 || num > 255) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false; // если не число
        }
        return true;
    }

    public static boolean isLocalIP(String ip) {
        return ip.startsWith("192.168.") ||
                ip.startsWith("10.") ||
                ip.startsWith("172.16.");
    }

    public static boolean isSameNetwork(String friendIP) {
        if (!isValidIP(friendIP)) return false;

        String myLocalIP = getLocalIP();
        if (!isValidIP(myLocalIP)) return false;

        String myNetwork = myLocalIP.substring(0, myLocalIP.lastIndexOf("."));
        String friendNetwork = friendIP.substring(0, friendIP.lastIndexOf("."));

        return myNetwork.equals(friendNetwork);
    }

    public static String getNetworkInfo() {
        StringBuilder info = new StringBuilder();

        String publicIP = getPublicIP();
        String localIP = getLocalIP();

        info.append("Определение ip...\n");
        info.append("Внешний IP: ").append(publicIP).append("\n");
        info.append("Локальный IP: ").append(localIP);

        if (isLocalIP(localIP)) {
            info.append(" (локальная сеть)");
        }

        return info.toString();
    }

}
