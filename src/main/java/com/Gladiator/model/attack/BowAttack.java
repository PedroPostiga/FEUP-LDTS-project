package com.Gladiator.model.attack;

import com.Gladiator.model.attack.projectile.Projectile;
import com.Gladiator.model.component.Hitbox;
import com.Gladiator.model.enemy.Enemy;
import com.Gladiator.model.entity.MovingEntity;

import java.util.List;

public class BowAttack implements AttackStrategy {

    private final int damage;
    private final int speed;
    private final double maxDistance;
    private final List<Enemy> targets;
    private final List<Projectile> projectiles;
    private final Hitbox hitbox = new Hitbox(8, 8);

    public BowAttack(int damage, int speed, double maxDistance, List<Enemy> targets, List<Projectile> projectiles) {
        this.damage = damage;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.targets = targets;
        this.projectiles = projectiles;
    }

    @Override
    public void attack(MovingEntity attacker) {
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

        Projectile p = new Projectile(attacker.getPosition(), closest.getPosition(), speed, damage, effectiveDistance, targets, hitbox);

        projectiles.add(p);
    }
}
