package com.gladiator.model.attack;

import com.gladiator.model.entity.MovingEntity;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.gladiator.Gladiator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwordAttack implements AttackStrategy {

    private final int damage;
    private final int range;
    private final List<MovingEntity> targets;
    private final int cooldownTicks; // Cooldown in game ticks
    private final Map<MovingEntity, Integer> lastAttackTick; // Track last attack tick per entity

    public SwordAttack(int damage, int range, List<? extends MovingEntity> targets) {
        this(damage, range, targets, 120); // Default 120 ticks cooldown (2 seconds at 60 ticks/sec)
    }

    public SwordAttack(int damage, int range, List<? extends MovingEntity> targets, int cooldownTicks) {
        this.damage = damage;
        this.range = range;
        this.targets = new java.util.ArrayList<>(targets);
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

        // Calculate attacker center position
        java.awt.Rectangle attackerHitbox = attacker.getHitbox();
        double attackerX = attackerHitbox.getCenterX();
        double attackerY = attackerHitbox.getCenterY();

        boolean hitAnyTarget = false;
        for (MovingEntity target : targets) {

            if (target == attacker) continue;
            if (!target.isAlive()) continue;

            // Calculate target center position
            java.awt.Rectangle targetHitbox = target.getHitbox();
            double targetX = targetHitbox.getCenterX();
            double targetY = targetHitbox.getCenterY();

            double dist = Math.sqrt(Math.pow(targetX - attackerX, 2) + Math.pow(targetY - attackerY, 2));

            if (dist <= range) {
                target.takeDamage(damage);
                hitAnyTarget = true;
            }
        }

        // Only update cooldown if we actually hit something
        if (hitAnyTarget) {
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
    public List<MovingEntity> getTargets() {
        return targets;
    }
}
