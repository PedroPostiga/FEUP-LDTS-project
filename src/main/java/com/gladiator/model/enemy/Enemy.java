package com.gladiator.model.enemy;

import com.gladiator.model.entity.MovingEntity;

public class Enemy extends MovingEntity {

    public Enemy(int x, int y, int w, int h, int hp, int speed){
        super(x, y, w, h, hp, speed);
    }
}
