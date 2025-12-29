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
        this(damage, range, gladiator, lifestealPercentage, 144); // Default 144 ticks cooldown (2.4 seconds at 60 ticks/sec)
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

        // Calculate attacker center position
        java.awt.Rectangle attackerHitbox = attacker.getHitbox();
        double attackerX = attackerHitbox.getCenterX();
        double attackerY = attackerHitbox.getCenterY();
        
        // Calculate target (gladiator) center position
        java.awt.Rectangle targetHitbox = gladiator.getHitbox();
        double targetX = targetHitbox.getCenterX();
        double targetY = targetHitbox.getCenterY();

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

    @Override
    public boolean isAttacking(MovingEntity attacker) {
        Integer lastTick = lastAttackTick.get(attacker);
        if (lastTick == null) return false;
        
        int currentTick = getCurrentTick();
        int ticksSinceAttack = currentTick - lastTick;
        
        // Show attack animation for first 18 ticks after attack (~300ms at 60 ticks/sec)
        return ticksSinceAttack < 18;
    }

    private int getCurrentTick() {
        // Convert current time to ticks (60 ticks/sec = ~16.67ms per tick)
        return (int) (System.currentTimeMillis() * 60 / 1000L);
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