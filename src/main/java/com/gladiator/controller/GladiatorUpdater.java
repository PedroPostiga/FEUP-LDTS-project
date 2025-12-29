package com.gladiator.controller;

import com.gladiator.model.Arena;
import com.gladiator.model.attack.BowAttack;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.gladiator.Gladiator;

public class GladiatorUpdater implements Updater{
    @Override
    public void update(Arena arena) {
        performGladiatorAttacks(arena);
    }

    private void performGladiatorAttacks(Arena arena) {
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

            // Check if any enemy is in range before attacking (using center-to-center like SwordAttack does)
            boolean enemyInRange = false;
            java.awt.Rectangle gladiatorHitbox = gladiator.getHitbox();
            double gladiatorX = gladiatorHitbox.getCenterX();
            double gladiatorY = gladiatorHitbox.getCenterY();
            int range = swordAttack.getRange();

            for (var enemy : enemies) {
                if (!enemy.isAlive()) continue;
                java.awt.Rectangle enemyHitbox = enemy.getHitbox();
                double enemyX = enemyHitbox.getCenterX();
                double enemyY = enemyHitbox.getCenterY();
                double dist = Math.sqrt(Math.pow(enemyX - gladiatorX, 2) + Math.pow(enemyY - gladiatorY, 2));
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
}