package com.gladiator.model.attack;

import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.MovingEntity;
import com.gladiator.model.gladiator.Gladiator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BowAttack implements AttackStrategy {

    private final int damage;
    private final int speed;
    private final double maxDistance;
    private final List<Enemy> targets;
    private final SingleArrowPool arrowPool;
    private final int cooldownTicks; // Cooldown in game ticks
    private final Map<MovingEntity, Integer> lastAttackTick; // Track last attack tick per entity

    public BowAttack(int damage, int speed, double maxDistance, List<Enemy> targets) {
        this(damage, speed, maxDistance, targets, new SingleArrowPool(), 180); // Default 180 ticks cooldown (3 seconds at 60 ticks/sec)
    }

    public BowAttack(int damage, int speed, double maxDistance, List<Enemy> targets, SingleArrowPool arrowPool) {
        this(damage, speed, maxDistance, targets, arrowPool, 180); // Default 180 ticks cooldown (3 seconds at 60 ticks/sec)
    }

    public BowAttack(int damage, int speed, double maxDistance, List<Enemy> targets, SingleArrowPool arrowPool, int cooldownTicks) {
        this.damage = damage;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.targets = new java.util.ArrayList<>(targets);
        this.arrowPool = arrowPool;
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

        // Don't fire if there's already an active arrow (only 1 arrow in pool)
        if (!arrowPool.getActiveArrows().isEmpty()) return;
        if (targets.isEmpty()) return;

        // Calculate attacker center position
        java.awt.Rectangle attackerHitbox = attacker.getHitbox();
        double attackerX = attackerHitbox.getCenterX();
        double attackerY = attackerHitbox.getCenterY();

        Enemy closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy t : targets) {
            if (!t.isAlive()) continue;

            // Calculate target center position
            java.awt.Rectangle targetHitbox = t.getHitbox();
            double targetX = targetHitbox.getCenterX();
            double targetY = targetHitbox.getCenterY();

            double dx = targetX - attackerX;
            double dy = targetY - attackerY;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < closestDistance) {
                closestDistance = dist;
                closest = t;
            }
        }

        if (closest == null) return;

        arrowPool.acquireArrow(
                attacker.getPosition(),
                speed,
                damage,
                maxDistance,
                closest
        );
        // Update cooldown after firing
        lastAttackTick.put(attacker, getCurrentTick());
    }

    @Override
    public boolean isAttacking(MovingEntity attacker) {
        Integer lastTick = lastAttackTick.get(attacker);
        if (lastTick == null) return false;
        
        int currentTick = getCurrentTick();
        int ticksSinceAttack = currentTick - lastTick;
        
        // Show attack animation for first 18 ticks after firing (~300ms at 60 ticks/sec)
        return ticksSinceAttack < 18;
    }

    private int getCurrentTick() {
        // Convert current time to ticks (60 ticks/sec = ~16.67ms per tick)
        return (int) (System.currentTimeMillis() * 60 / 1000L);
    }

    public SingleArrowPool getArrowPool() {
        return arrowPool;
    }

    public List<Enemy> getTargets() {
        return targets;
    }
}
