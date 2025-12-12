package com.gladiator.controller;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.WaveManager;
import com.gladiator.model.component.Position;
import com.gladiator.view.game.ArenaViewer;

import java.io.IOException;

public class GameController extends Controller{
    private final Arena arena;
    private final ArenaViewer viewer;
    private final WaveManager waveManager;
    ArenaUpdater updater = new ArenaUpdater();

    public GameController(Arena arena) {
        super(5); // 5 ticks per second
        this.arena = arena;
        this.viewer = new ArenaViewer(arena);
        this.waveManager = new WaveManager(arena);
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
        if (!waveManager.isWaveInProgress()) {
            waveManager.startNextWave();
        }
        updater.update(arena);
        /*if (arena.isGameOver()) {
            stop();
        }*/
    }

    @Override
    protected void draw(GUI gui) throws IOException {
        viewer.draw(gui);
    }
}
