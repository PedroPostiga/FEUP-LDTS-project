package com.Gladiator.model.entity;

public interface Entity extends Collidable, Updater, Renderer {
    boolean isAlive();
}
