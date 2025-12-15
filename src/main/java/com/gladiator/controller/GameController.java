package com.gladiator.controller;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.WaveManager;
import com.gladiator.model.component.Position;
import com.gladiator.view.game.ArenaViewer;

import java.awt.*;
import java.io.IOException;

public class GameController extends Controller{
    public enum GameState {
        PLAYING,
        WON,
        LOST
    }

    private final Arena arena;
    private final ArenaViewer viewer;
    private final WaveManager waveManager;
    ArenaUpdater updater = new ArenaUpdater();
    GladiatorUpdater gladiatorUpdater = new GladiatorUpdater();
    private GameState gameState;

    public GameController(Arena arena) {
        super(5); // 5 ticks per second
        this.arena = arena;
        this.viewer = new ArenaViewer(arena);
        this.waveManager = new WaveManager(arena);
        this.gameState = GameState.PLAYING;
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
        // Don't update if game is already over
        if (isGameOver()) {
            return;
        }

        // Perform gladiator attacks automatically
        gladiatorUpdater.update(arena);
        updater.update(arena);
        
        // Check if all enemies are dead and wave should complete
        boolean gameWon = waveManager.checkWaveCompletion();
        
        // Check lose condition: gladiator is dead
        if (arena.getGladiator() != null && !arena.getGladiator().isAlive()) {
            gameState = GameState.LOST;
            System.out.println("GAME OVER: You lost! The gladiator has died.");
            stop();
            return;
        }
        
        // Check win condition: completed all waves
        if (gameWon) {
            gameState = GameState.WON;
            System.out.println("GAME OVER: You won! Completed " + waveManager.getCurrentWave() + " waves!");
            stop();
            return;
        }
        
        // Start next wave if current wave is complete
        if (!waveManager.isWaveInProgress()) {
            waveManager.startNextWave();
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean isGameOver() {
        return gameState == GameState.WON || gameState == GameState.LOST;
    }

    @Override
    protected void draw(GUI gui) throws IOException {
        viewer.draw(gui);
    }
}
