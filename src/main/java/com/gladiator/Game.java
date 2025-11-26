package com.gladiator;

import com.gladiator.gui.LanternaGUI;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Application {
    private final LanternaGUI gui;
    public Application() throws IOException, URISyntaxException, FontFormatException {
        this.gui = new LanternaGUI(20, 20);
    }
    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException {
        new Application().start();
    }
    private void start() throws IOException{

    }
}
