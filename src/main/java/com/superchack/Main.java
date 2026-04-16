package com.superchack;

import com.superchack.encoding.Charset;
import com.superchack.gui.LoginWindow;

public class Main {
    public static void main(String[] args) {
        Charset.set();
        LoginWindow.main(args);
    }
}