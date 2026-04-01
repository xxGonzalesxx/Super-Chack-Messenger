package com.superchack.network;

import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;

import java.net.InetAddress;

public class UPnPManager {

    private static GatewayDevice gateway = null;

    static {
        try {
            GatewayDiscover discover = new GatewayDiscover();
            discover.discover();
            gateway = discover.getValidGateway();

            if (gateway != null) {
                System.out.println("✅ UPnP шлюз найден: " + gateway.getFriendlyName());
            } else {
                System.out.println("⚠️ UPnP не поддерживается роутером");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Ошибка при поиске UPnP: " + e.getMessage());
        }
    }

    public static boolean openPort(int port, String description) {
        if (gateway == null) return false;

        try {
            String localIP = InetAddress.getLocalHost().getHostAddress();
            boolean success = gateway.addPortMapping(port, port, localIP, "TCP", description);

            if (success) {
                System.out.println("✅ Порт " + port + " открыт на роутере (UPnP)");
                System.out.println("   → " + localIP + ":" + port);
            } else {
                System.out.println("⚠️ Не удалось открыть порт " + port + " через UPnP");
            }
            return success;
        } catch (Exception e) {
            System.out.println("❌ Ошибка UPnP: " + e.getMessage());
            return false;
        }
    }

    public static boolean closePort(int port) {
        if (gateway == null) return false;
        try {
            gateway.deletePortMapping(port, "TCP");
            System.out.println("🔒 Порт " + port + " закрыт на роутере");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPortOpen(int port) {
        if (gateway == null) return false;
        try {
            PortMappingEntry entry = new PortMappingEntry();
            return gateway.getSpecificPortMappingEntry(port, "TCP", entry);
        } catch (Exception e) {
            return false;
        }
    }

    public static void showGatewayInfo() {
        if (gateway != null) {
            System.out.println("\n📡 Информация о роутере:");
            System.out.println("   Модель: " + gateway.getFriendlyName());
            System.out.println("   IP: " + gateway.getLocalAddress().getHostAddress());
        } else {
            System.out.println("⚠️ UPnP не доступен");
        }
    }
}