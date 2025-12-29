package com.gladiator.gui;

import com.gladiator.model.component.Position;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LanternaGUI implements GUI {
    private final Screen screen;
    private final Map<String, BufferedImage> spriteCache;
    private final TextGraphics graphics;

    public LanternaGUI(int width, int height) throws IOException, FontFormatException, URISyntaxException {
        AWTTerminalFontConfiguration fontConfig = loadSquareFont();
        Terminal terminal = createTerminal(width, height, fontConfig);
        spriteCache = new HashMap<>();
        this.screen = createScreen(terminal);
        this.graphics = screen.newTextGraphics();
    }

    private Screen createScreen(Terminal terminal) throws IOException {
        final Screen screen;
        screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }

    private Terminal createTerminal(int width, int height, AWTTerminalFontConfiguration fontConfig) throws IOException {
        TerminalSize terminalSize = new TerminalSize(width, height + 1);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize);
        terminalFactory.setForceAWTOverSwing(true);
        terminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
        Terminal terminal = terminalFactory.createTerminal();
        return terminal;
    }

    private AWTTerminalFontConfiguration loadSquareFont() throws URISyntaxException, FontFormatException, IOException {
        URL resource = getClass().getClassLoader().getResource("fonts/square.ttf");
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        Font loadedFont = font.deriveFont(Font.PLAIN, 2);
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);
        return fontConfig;
    }

    public ACTION getNextAction() throws IOException {
        ACTION lastAction = ACTION.NONE;
        KeyStroke keyStroke;
        boolean foundEvent = false;

        while ((keyStroke = screen.pollInput()) != null) {
            foundEvent = true;
            
            if (keyStroke.getKeyType() == KeyType.EOF) {
                lastAction = ACTION.QUIT;
                continue;
            }
            if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q') {
                lastAction = ACTION.QUIT;
                continue;
            }

            if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                lastAction = ACTION.UP;
                continue;
            }
            if (keyStroke.getKeyType() == KeyType.ArrowRight) {
                lastAction = ACTION.RIGHT;
                continue;
            }
            if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                lastAction = ACTION.DOWN;
                continue;
            }
            if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
                lastAction = ACTION.LEFT;
                continue;
            }

            if (keyStroke.getKeyType() == KeyType.Enter) {
                lastAction = ACTION.SELECT;
                continue;
            }
        }

        return foundEvent ? lastAction : ACTION.NONE;
    }

    public void drawSprite(String img, Position position) throws IOException {

        // Use sprite cache to avoid loading images every frame
        BufferedImage sprite = loadImage(img);

        for (int x = 0; x < sprite.getWidth(); x++){
            for (int y = 0; y < sprite.getHeight(); y++){
                int a = sprite.getRGB(x, y);
                int alpha = (a >> 24) & 0xff;
                int red = (a >> 16) & 255;
                int green = (a >> 8) & 255;
                int blue = a & 255;

                if (alpha != 0) {
                    TextCharacter c = new TextCharacter(' ', new TextColor.RGB(red, green, blue), new TextColor.RGB(red, green, blue));
                    graphics.setCharacter(position.getX() + x, position.getY() + y, c);
                }
            }
        }
    }

    private BufferedImage loadImage(String img) throws IOException {
        if (spriteCache.containsKey(img)) {
            return spriteCache.get(img);
        }

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource(img)));

            spriteCache.put(img, image);

            return image;
        }
        catch (IOException e) {
            System.err.println("Error loading image: " + img);
            throw e;
        }
    }

    @Override
    public void drawText(Position position, String text, String color) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.Factory.fromString(color));
        tg.putString(position.getX(), position.getY(), text);
    }

    @Override
    public void drawHitbox(int x, int y, int width, int height, String color) {
        TextGraphics tg = screen.newTextGraphics();
        TextColor boxColor = TextColor.Factory.fromString(color);
        tg.setForegroundColor(boxColor);

        // Draw top and bottom borders using simple characters
        for (int i = 0; i < width; i++) {
            tg.setCharacter(x + i, y, new TextCharacter('-', boxColor, TextColor.ANSI.BLACK));
            if (y + height - 1 >= 0) {
                tg.setCharacter(x + i, y + height - 1, new TextCharacter('-', boxColor, TextColor.ANSI.BLACK));
            }
        }

        // Draw left and right borders
        for (int i = 0; i < height; i++) {
            tg.setCharacter(x, y + i, new TextCharacter('|', boxColor, TextColor.ANSI.BLACK));
            tg.setCharacter(x + width - 1, y + i, new TextCharacter('|', boxColor, TextColor.ANSI.BLACK));
        }

        // Draw corners
        tg.setCharacter(x, y, new TextCharacter('+', boxColor, TextColor.ANSI.BLACK));
        tg.setCharacter(x + width - 1, y, new TextCharacter('+', boxColor, TextColor.ANSI.BLACK));
        if (y + height - 1 >= 0) {
            tg.setCharacter(x, y + height - 1, new TextCharacter('+', boxColor, TextColor.ANSI.BLACK));
            tg.setCharacter(x + width - 1, y + height - 1, new TextCharacter('+', boxColor, TextColor.ANSI.BLACK));
        }
    }

    private void drawCharacter(int x, int y, char c, String color) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.Factory.fromString(color));
        tg.putString(x, y + 1, "" + c);
    }

    @Override
    public void drawCircle(int centerX, int centerY, int radius, String color) {
        TextGraphics tg = screen.newTextGraphics();
        TextColor circleColor = TextColor.Factory.fromString(color);
        tg.setForegroundColor(circleColor);

        // Draw circle outline using Bresenham's circle algorithm approximation
        // Draw 8 octants for symmetry
        for (int angle = 0; angle < 360; angle += 2) {
            double rad = Math.toRadians(angle);
            int x = (int) (centerX + radius * Math.cos(rad));
            int y = (int) (centerY + radius * Math.sin(rad));
            if (x >= 0 && y >= 0) {
                tg.setCharacter(x, y, new TextCharacter('·', circleColor, TextColor.ANSI.BLACK));
            }
        }
    }

    @Override
    public void clear() {
        screen.clear();
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    @Override
    public void close() throws IOException {
        screen.close();
    }
}