package com.superchack.validation;

public class ComputerOSValidator {

    private static final String OS = System.getProperty("os.name");
    private static final String VERSION = System.getProperty("os.version");
    private static final String ARCH = System.getProperty("os.arch");

    public static  String getOS() {
        return OS;
    }

    public static  String getOSVersion() {
        return VERSION;
    }

    public static String getArch() {
        return ARCH;
    }

    public static void print() {
        System.out.println("Определение Системы...\n"
                + "ОС: " + OS + "\n"
                + "Версия: " + VERSION + "\n"
                + "Архитектура: " + ARCH);
    }
}
