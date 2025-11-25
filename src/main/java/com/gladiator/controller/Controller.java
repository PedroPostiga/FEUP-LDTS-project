package com.gladiator.controller;

import com.gladiator.view.GladiatorDraw;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Controller {
    private Screen screen;


    public Controller() {
        try {
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                    .setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            screen = new TerminalScreen(terminal);

            screen.setCursorPosition(null);   // we don't need a cursor
            screen.startScreen();             // screens must be started
            screen.doResizeIfNecessary();     // resize screen if necessary

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void draw() throws IOException{
        screen.clear();
        GladiatorDraw gladiator = new GladiatorDraw();
        gladiator.draw(screen.newTextGraphics());
        screen.refresh();
    }

    public void run() throws IOException{
        while (true) {
            draw();
            KeyStroke key = screen.readInput();
            if ((key.getKeyType() == KeyType.Character && key.getCharacter() == 'q')) {
                screen.stopScreen();
            }
            if (key.getKeyType() == KeyType.EOF) {
                break;
            }
            processKey(key);
        }
    }

    private void processKey(KeyStroke key) throws IOException {
    }

    public void gameOver() throws IOException {
        screen.stopScreen();
    }
}
