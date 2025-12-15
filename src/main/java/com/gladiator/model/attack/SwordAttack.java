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
        this(damage, range, targets, 20); // Default 20 ticks cooldown (2 seconds at 10 ticks/sec)
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

        double attackerX = attacker.getPosition().getX();
        double attackerY = attacker.getPosition().getY();

        boolean hitAnyTarget = false;
        for (MovingEntity target : targets) {

            if (target == attacker) continue;
            if (!target.isAlive()) continue;

            double targetX = target.getPosition().getX();
            double targetY = target.getPosition().getY();

            double dist = Math.sqrt(Math.pow(targetX - attackerX, 2) + Math.pow(targetY - attackerY, 2));

            if (dist <= range) {
                target.takeDamage(damage);
                hitAnyTarget = true;
            }
        }

        // Only update cooldown if we actually hit something
        if (hitAnyTarget) {
            String attackerType = (attacker instanceof Gladiator) ? "GLADIATOR" : "ENEMY";
            System.out.println("SwordAttack: " + attackerType + " made a sword attack!");
            lastAttackTick.put(attacker, getCurrentTick());
        }
    }

    @Override
    public boolean isAttacking(MovingEntity attacker) {
        Integer lastTick = lastAttackTick.get(attacker);
        if (lastTick == null) return false;
        
        int currentTick = getCurrentTick();
        int ticksSinceAttack = currentTick - lastTick;
        
        // Show attack animation for first 3 ticks after attack (300ms at 10 ticks/sec)
        return ticksSinceAttack < 3;
    }

    private int getCurrentTick() {
        // Convert current time to ticks (10 ticks/sec = 100ms per tick)
        return (int) (System.currentTimeMillis() / 100L);
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
