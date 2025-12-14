package com.gladiator.controller;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.WaveManager;
import com.gladiator.model.attack.BowAttack;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.component.Position;
import com.gladiator.model.gladiator.Gladiator;
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

    private void performGladiatorAttacks() {
        Gladiator gladiator = arena.getGladiator();
        if (gladiator == null) return;

        // Get current active enemies
        var enemies = arena.getEnemiePool().getAllActiveEnemies();
        if (enemies.isEmpty()) return;

        // Sword attack: automatically attack all enemies in range
        SwordAttack swordAttack = gladiator.getSwordAttack();
        if (swordAttack != null) {
            swordAttack.getTargets().clear();
            swordAttack.getTargets().addAll(enemies);
            
            // Check if any enemy is in range before attacking
            boolean enemyInRange = false;
            double gladiatorX = gladiator.getPosition().getX();
            double gladiatorY = gladiator.getPosition().getY();
            int range = swordAttack.getRange();
            
            for (var enemy : enemies) {
                if (!enemy.isAlive()) continue;
                double dist = Math.sqrt(Math.pow(enemy.getPosition().getX() - gladiatorX, 2) + 
                                        Math.pow(enemy.getPosition().getY() - gladiatorY, 2));
                if (dist <= range) {
                    enemyInRange = true;
                    break;
                }
            }
            
            // Only attack if at least one enemy is in range
            if (enemyInRange) {
                swordAttack.attack(gladiator);
            }
        }

        // Bow attack: automatically shoot whenever possible
        BowAttack bowAttack = gladiator.getBowAttack();
        if (bowAttack != null) {
            bowAttack.getTargets().clear();
            bowAttack.getTargets().addAll(enemies);
            bowAttack.attack(gladiator);
        }
    }

    @Override
    protected void update() {
        // Perform gladiator attacks automatically
        performGladiatorAttacks();
        
        updater.update(arena);
        
        // Check if all enemies are dead and wave should complete
        waveManager.checkWaveCompletion();
        
        // Start next wave if current wave is complete
        if (!waveManager.isWaveInProgress()) {
            waveManager.startNextWave();
        }
        
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
