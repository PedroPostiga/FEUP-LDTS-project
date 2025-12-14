package com.gladiator.controller;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.WaveManager;
import com.gladiator.model.component.Position;
import com.gladiator.view.game.ArenaViewer;

import java.awt.*;
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
        int speed = arena.getGladiator().getSpeed();
        Position currentPos = arena.getGladiator().getPosition();
        moveGladiator(new Position(currentPos.getX() - speed, currentPos.getY()));
    }

    public void moveGladiatorRight() {
        int speed = arena.getGladiator().getSpeed();
        Position currentPos = arena.getGladiator().getPosition();
        moveGladiator(new Position(currentPos.getX() + speed, currentPos.getY()));
    }

    public void moveGladiatorUp() {
        int speed = arena.getGladiator().getSpeed();
        Position currentPos = arena.getGladiator().getPosition();
        moveGladiator(new Position(currentPos.getX(), currentPos.getY() - speed));
    }

    public void moveGladiatorDown() {
        int speed = arena.getGladiator().getSpeed();
        Position currentPos = arena.getGladiator().getPosition();
        moveGladiator(new Position(currentPos.getX(), currentPos.getY() + speed));
    }

    private void moveGladiator(Position position) {
        Rectangle hitbox = arena.getGladiator().getHitbox();
        Rectangle newHitbox = new Rectangle(position.getX(), position.getY(), hitbox.width, hitbox.height);
        if (arena.isEmpty(newHitbox, null, arena.getGladiator())) {
            arena.getGladiator().setPosition(position);
        }
    }

    @Override
    protected void update() {
        if (!waveManager.isWaveInProgress()) {
            waveManager.startNextWave();
        }
        updater.update(arena);
        if (!arena.getGladiator().isAlive()){
            System.out.println("gladiator is dead");
        }
        /*if (arena.isGameOver()) {
            stop();
        }*/
    }

    @Override
    protected void draw(GUI gui) throws IOException {
        viewer.draw(gui);
    }
}
