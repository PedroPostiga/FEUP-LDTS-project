package com.gladiator.model.component;

import java.awt.*;

public class Hitbox {
    private final int width;
    private final int height;

    public Hitbox(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds(Position p) {
        return new Rectangle(p.getX(), p.getY(), width, height);
    }
}
