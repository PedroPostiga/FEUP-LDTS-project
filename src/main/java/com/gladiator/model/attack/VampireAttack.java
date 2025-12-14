package com.gladiator.model.attack;

import com.gladiator.model.entity.MovingEntity;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.gladiator.Gladiator;

import java.util.HashMap;
import java.util.Map;

public class VampireAttack implements AttackStrategy {

    private final int damage;
    private final int range;
    private final Gladiator gladiator;
    private final double lifestealPercentage;
    private final int cooldownTicks; // Cooldown in game ticks
    private final Map<MovingEntity, Integer> lastAttackTick; // Track last attack tick per entity

    public VampireAttack(int damage, int range, Gladiator gladiator, double lifestealPercentage){
        this(damage, range, gladiator, lifestealPercentage, 12); // Default 12 ticks cooldown (2.4 seconds)
    }

    public VampireAttack(int damage, int range, Gladiator gladiator, double lifestealPercentage, int cooldownTicks){
        this.damage = damage;
        this.range = range;
        this.gladiator = gladiator;
        this.lifestealPercentage = lifestealPercentage;
        this.cooldownTicks = cooldownTicks;
        this.lastAttackTick = new HashMap<>();
    }

    @Override
    public void attack(MovingEntity attacker) {
        // Check cooldown
        Integer lastTick = lastAttackTick.get(attacker);
        if (lastTick != null) {
            int currentTick = getCurrentTick();
            if (currentTick - lastTick < cooldownTicks) {
                return; // Still on cooldown
            }
        }

        if (!gladiator.isAlive()) return;

        double attackerX = attacker.getPosition().getX();
        double attackerY = attacker.getPosition().getY();
        double targetX = gladiator.getPosition().getX();
        double targetY = gladiator.getPosition().getY();

        double dist = Math.sqrt(Math.pow(targetX - attackerX, 2) + Math.pow(targetY - attackerY, 2));

        if (dist <= range) {
            gladiator.takeDamage(damage);

            int healAmount = (int) (damage * lifestealPercentage);
            attacker.getHealth().heal(healAmount);

            String attackerType = (attacker instanceof Enemy) ? "ENEMY" : "UNKNOWN";
            System.out.println("VampireAttack: " + attackerType + " made a vampire attack!");

            // Update cooldown after successful attack
            lastAttackTick.put(attacker, getCurrentTick());
        }
    }

    private int getCurrentTick() {
        // Convert current time to ticks (5 ticks/sec = 200ms per tick)
        return (int) (System.currentTimeMillis() / 200L);
    }

    public int getDamage() {
        return damage;
    }
    public int getRange() {
        return range;
    }

    public double getLifestealPercentage() {
        return lifestealPercentage;
    }
}