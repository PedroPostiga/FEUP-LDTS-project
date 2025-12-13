package com.gladiator;

import java.awt.*;

public class Application {
    public static void main(String[] args) {
        try {
            new GameRunner().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}