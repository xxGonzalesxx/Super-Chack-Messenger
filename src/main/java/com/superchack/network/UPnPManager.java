package com.superchack.network;

import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import java.net.InetAddress;

public class UPnPManager {

    private static final GatewayDevice GATEWAY;

    static {
        GatewayDevice g = null;
        try {
            GatewayDiscover discover = new GatewayDiscover();
            discover.discover();
            g = discover.getValidGateway();
        } catch (Exception ignored) {}
        GATEWAY = g;
    }

    public static boolean openPort(int port, String description) {
        if (GATEWAY == null) return false;
        try {
            String localIP = InetAddress.getLocalHost().getHostAddress();
            return GATEWAY.addPortMapping(port, port, localIP, "TCP", description);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean closePort(int port) {
        if (GATEWAY == null) return false;
        try {
            GATEWAY.deletePortMapping(port, "TCP");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}