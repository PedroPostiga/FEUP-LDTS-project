package com.gladiator.model.entity;

import com.gladiator.model.component.Hitbox;
import com.gladiator.model.component.Position;

import java.awt.*;

public abstract class Obstacle implements Entity {
    private final Position position;
    private final Hitbox hitbox;

    public Obstacle(int x, int y, int w, int h) {
        this.position = new Position(x, y);
        this.hitbox = new Hitbox(w, h);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Rectangle getBounds() {
        return hitbox.getBounds(position);
    }

}

