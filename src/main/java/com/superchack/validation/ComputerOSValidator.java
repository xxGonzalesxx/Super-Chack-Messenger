package com.superchack.validation;

public class ComputerOSValidator {

    public static  String getOS() {
        return System.getProperty("os.name");
    }

    public static  String getOSVersion() {
        return System.getProperty("os.version");
    }

    public static String getArch() {
        return System.getProperty("os.arch");
    }

    public static void print() {
        System.out.println("Определение Системы...");
        System.out.println("Ваша операционна система:" + ComputerOSValidator.getOS());
        System.out.println("Версия оперционной  системы:" + ComputerOSValidator.getOSVersion());
        System.out.println("Сборка:" + ComputerOSValidator.getArch());
    }
}
