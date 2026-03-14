package com.superchack.validation;

import java.io.IOException;
import java.net.ServerSocket;

public class PortValidator {
    private static final int MIN_PORT = 45000;

    private static final int MAX_PORT =45050;

    public static int findFreePort() {
        System.out.println("Ищем свободный порт...");
        for (int port = MIN_PORT; port <= MAX_PORT; port++) {
            if (isPortAvailable(port)) {
                System.out.println("Найден свободный порт:" + port);
                return port;
            }
        }
        System.out.println("Нет свободных портов в диапазоне " +
                MIN_PORT + "-" + MAX_PORT);
        return -1;
    }

    private static boolean isPortAvailable(int port) {
        try (ServerSocket ss = new ServerSocket(port)) {
            ss.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
