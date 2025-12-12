package com.gladiator.model.attack;

import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.MovingEntity;

import java.util.List;

public class BowAttack implements AttackStrategy {

    private final int damage;
    private final int speed;
    private final double maxDistance;
    private final List<Enemy> targets;
    private final SingleArrowPool arrowPool;

    public BowAttack(int damage, int speed, double maxDistance, List<Enemy> targets) {
        this.damage = damage;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.targets = targets;
        this.arrowPool = new SingleArrowPool();
    }

    @Override
    public void attack(MovingEntity attacker) {
        if (arrowPool.getActiveArrows().isEmpty()) return;
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

    }

    public SingleArrowPool getArrowPool() {
        return arrowPool;
    }
}
