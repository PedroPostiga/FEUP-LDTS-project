package com.gladiator.model.attack;

import com.gladiator.model.entity.MovingEntity;

import java.util.List;

public class SwordAttack implements AttackStrategy {

    private final int damage;
    private final int range;
    private final List<? extends MovingEntity> targets;

    public SwordAttack(int damage, int range, List<? extends MovingEntity> targets) {
        this.damage = damage;
        this.range = range;
        this.targets = targets;
    }

    @Override
    public void attack(MovingEntity attacker) {
        double attackerX = attacker.getPosition().getX();
        double attackerY = attacker.getPosition().getY();

        for (MovingEntity target : targets) {

            if (target == attacker) continue;
            if (!target.isAlive()) continue;

            double targetX = target.getPosition().getX();
            double targetY = target.getPosition().getY();

            double dist = Math.sqrt(Math.pow(targetX - attackerX, 2) + Math.pow(targetY - attackerY, 2));

            if (dist <= range) {
                target.takeDamage(damage);
            }
        }

    }
}
