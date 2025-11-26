package com.gladiator.model.attack;

import com.gladiator.model.entity.MovingEntity;
import com.gladiator.model.gladiator.Gladiator;

public class VampireAttack implements AttackStrategy {

    private final int damage;
    private final int range;
    private final Gladiator gladiator;
    private final double lifestealPercentage;

    public VampireAttack(int damage, int range, Gladiator gladiator, double lifestealPercentage){
        this.damage = damage;
        this.range = range;
        this.gladiator = gladiator;
        this.lifestealPercentage = lifestealPercentage;
    }

    @Override
    public void attack(MovingEntity attacker) {
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
        }
    }
}