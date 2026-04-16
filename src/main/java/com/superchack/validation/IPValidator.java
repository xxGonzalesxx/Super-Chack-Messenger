package com.superchack.validation;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class IPValidator {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private static final String PUBLIC_IP_URL = "https://api.ipify.org";

    public static String getPublicIP() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(PUBLIC_IP_URL))
                    .timeout(Duration.ofSeconds(5))
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request,
                    HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "Ошибка получения внешнего IP";
        }
    }

    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    public static boolean isValidIP(String ip) {
        if (ip == null || ip.isEmpty()) return false;

        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;

        try {
            for (String part : parts) {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isLocalIP(String ip) {
        return ip.startsWith("192.168.") || ip.startsWith("10.") || ip.startsWith("172.16.");
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
        return "Внешний IP: " + getPublicIP() + "\nЛокальный IP: " + getLocalIP() +
                (isLocalIP(getLocalIP()) ? " (локальная сеть)" : "");
    }
}