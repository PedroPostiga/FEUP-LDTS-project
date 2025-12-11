package com.gladiator.model.entity;

import com.gladiator.model.component.Position;

import java.awt.*;

public interface Entity extends Collidable {
    Position getPosition();
    Rectangle getBounds();
}
