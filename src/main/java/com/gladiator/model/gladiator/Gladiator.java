package com.gladiator.model.gladiator;

import com.gladiator.model.attack.BowAttack;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.entity.MovingEntity;

public class Gladiator extends MovingEntity {

    private static Gladiator instance;
    private SwordAttack swordAttack;
    private BowAttack bowAttack;

    private Gladiator(int x, int y, int w, int h, int hp, int speed) {
        super(x, y, w, h, hp, speed);
    }

    public static Gladiator getInstance(int x, int y, int w, int h, int hp, int speed) {
        if (instance == null) {
            instance = new Gladiator(x, y, w, h, hp, speed);
        }
        return instance;
    }

    public SwordAttack getSwordAttack() {
        return swordAttack;
    }

    public void setSwordAttack(SwordAttack swordAttack) {
        this.swordAttack = swordAttack;
    }

    public BowAttack getBowAttack() {
        return bowAttack;
    }

    public void setBowAttack(BowAttack bowAttack) {
        this.bowAttack = bowAttack;
    }
}
