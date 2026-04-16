package com.superchack.validation;

import java.io.IOException;
import java.net.ServerSocket;

public class PortValidator {
    private static final int MIN_PORT = 45000;
    private static final int MAX_PORT = 45050;

    public static int findFreePort() {
        for (int port = MIN_PORT; port <= MAX_PORT; port++) {
            if (isPortAvailable(port)) {
                return port;
            }
        }
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