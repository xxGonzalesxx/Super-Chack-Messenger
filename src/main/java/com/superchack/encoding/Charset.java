package com.superchack.encoding;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Charset {

    public static void set() {
        //настройка кодировки chcp:65001
        try {
            new ProcessBuilder("chcp", "65001").inheritIO().start().waitFor();
        } catch (Exception ignored) {}
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
            System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
    }
}