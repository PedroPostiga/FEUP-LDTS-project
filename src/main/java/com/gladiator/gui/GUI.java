package com.gladiator.gui;

import com.gladiator.model.component.Position;

import java.io.IOException;

public interface GUI {
    ACTION getNextAction() throws IOException;

    void drawSprite(String img, Position position) throws IOException;

    void drawText(Position position, String text, String color);

    void drawHitbox(int x, int y, int width, int height, String color);

    void drawCircle(int centerX, int centerY, int radius, String color);

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    enum ACTION {UP, RIGHT, DOWN, LEFT, NONE, QUIT, SELECT}
}
