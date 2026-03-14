package com.superchack;

import com.superchack.validation.IPValidator;
import com.superchack.validation.PortValidator;
import com.superchack.validation.Space;

public class Main {
    public static void main(String[] args) {
        System.out.println("Super Chack Messenger v1.0");
        System.out.println("Режим: p2p(без сервера)");
        Space.put();
        PortValidator.findFreePort();
        Space.put();
        System.out.println(IPValidator.getNetworkInfo());
    }
}