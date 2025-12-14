package com.gladiator.model.gladiator;

import com.gladiator.model.attack.BowAttack;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.entity.MovingEntity;

public class Gladiator extends MovingEntity {

    private SwordAttack swordAttack;
    private BowAttack bowAttack;

    public Gladiator(int x, int y, int w, int h, int hp, int speed) {
        super(x, y, w, h, hp, speed);
        //this.bowAttack = new BowAttack(20, 2, 5, );
        //this.swordAttack = new SwordAttack(75, 1.5, );
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
