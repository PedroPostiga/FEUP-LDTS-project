package com.gladiator.model.entity;

public interface Entity extends Collidable, Updater, Renderer {
    boolean isAlive();
}
