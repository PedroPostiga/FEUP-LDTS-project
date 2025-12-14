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
        this(damage, speed, maxDistance, targets, new SingleArrowPool(), 15); // Default 15 ticks cooldown (3 seconds)
    }

    public BowAttack(int damage, int speed, double maxDistance, List<Enemy> targets, SingleArrowPool arrowPool) {
        this(damage, speed, maxDistance, targets, arrowPool, 15); // Default 15 ticks cooldown
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

        Enemy closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (Enemy t : targets) {
            if (!t.isAlive()) continue;

            double dx = t.getPosition().getX() - attacker.getPosition().getX();
            double dy = t.getPosition().getY() - attacker.getPosition().getY();
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < closestDistance) {
                closestDistance = dist;
                closest = t;
            }
        }

        if (closest == null) return;

        double effectiveDistance = Math.min(closestDistance, maxDistance);

        arrowPool.acquireArrow(
                attacker.getPosition(),
                speed,
                damage,
                effectiveDistance,
                closest
        );

        String attackerType = (attacker instanceof Gladiator) ? "GLADIATOR" : "ENEMY";
        System.out.println("BowAttack: " + attackerType + " made a bow attack!");

        // Update cooldown after firing
        lastAttackTick.put(attacker, getCurrentTick());
    }

    private int getCurrentTick() {
        // Convert current time to ticks (5 ticks/sec = 200ms per tick)
        return (int) (System.currentTimeMillis() / 200L);
    }

    public SingleArrowPool getArrowPool() {
        return arrowPool;
    }

    public List<Enemy> getTargets() {
        return targets;
    }
}
