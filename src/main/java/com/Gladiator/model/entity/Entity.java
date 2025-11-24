package com.Gladiator.model.entity;

public interface Entity extends Collidable, Updatable, Renderable {
    boolean isAlive();
}
