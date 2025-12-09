package com.gladiator.gui;

import com.gladiator.model.component.Position;

import java.io.IOException;

public interface GUI {
    ACTION getNextAction() throws IOException;

    void drawSprite(String img, Position position) throws IOException;

    void drawGladiator(Position position);

    void drawFatZombie(Position position);

    void drawLightZombie(Position position);

    void drawVampire(Position position);

    void drawSmallRock(Position position);

    void drawLargeRock(Position position);

    void drawTree(Position position);

    void drawText(Position position, String text, String color);

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    enum ACTION {UP, RIGHT, DOWN, LEFT, NONE, QUIT, SELECT}
}
