package com.gladiator.controller;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.component.Position;
import com.gladiator.view.game.ArenaViewer;

import java.io.IOException;

public class GameController extends Controller{
    private final Arena arena;
    private final ArenaViewer viewer;

    public GameController(Arena arena) {
        super(5); // 5 ticks per second
        this.arena = arena;
        this.viewer = new ArenaViewer(arena);
    }

    @Override
    protected void processInput(GUI gui) throws IOException {
        GUI.ACTION action = gui.getNextAction();

        switch (action) {
            case UP -> moveGladiatorUp();
            case DOWN -> moveGladiatorDown();
            case LEFT -> moveGladiatorLeft();
            case RIGHT -> moveGladiatorRight();
            case QUIT -> stop();
            default -> {}
        }
    }

    public void moveGladiatorLeft() {
        moveGladiator(arena.getGladiator().getPosition().getLeft());
    }

    public void moveGladiatorRight() {
        moveGladiator(arena.getGladiator().getPosition().getRight());
    }

    public void moveGladiatorUp() {
        moveGladiator(arena.getGladiator().getPosition().getUp());
    }

    public void moveGladiatorDown() {
        moveGladiator(arena.getGladiator().getPosition().getDown());
    }

    private void moveGladiator(Position position) {
        if (arena.isEmpty(position)) {
            arena.getGladiator().setPosition(position);
        }
    }

    @Override
    protected void update() {
        arena.updateEnemies();
        if (arena.isGameOver()) {
            stop();
        }
    }

    @Override
    protected void draw(GUI gui) throws IOException {
        viewer.draw(gui);
    }
}
