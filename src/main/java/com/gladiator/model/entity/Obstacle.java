package com.gladiator.model.entity;

import com.gladiator.model.component.Position;

import java.awt.*;

public abstract class Obstacle implements Entity {
    private final Position position;
    private final Rectangle hitbox;

    public Obstacle(int x, int y, int w, int h) {
        this.position = new Position(x, y);
        this.hitbox = new Rectangle(x, y ,w, h);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

}

