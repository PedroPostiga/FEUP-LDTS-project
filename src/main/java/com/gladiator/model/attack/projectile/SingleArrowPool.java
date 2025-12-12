package com.gladiator.model.attack.projectile;

import com.gladiator.model.component.Hitbox;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.MovingEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SingleArrowPool {
    private final Queue<Arrow> available;
    private final List<Arrow> active;

    private static final int INITIAL_POOL_SIZE = 1;
    private static final int MAX_POOL_SIZE = 1;

    public SingleArrowPool() {
        this.available = new LinkedList<>();
        this.active = new LinkedList<>();

        preWarm();
    }

    private void preWarm() {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            Arrow arrow = new Arrow(new Position(0, 0), 0, 0, 0, null);
            available.offer(arrow);
        }
    }

    public Arrow resetProjectileState(Arrow arrow) {
        Position dummyPos = new Position(0, 0);
        arrow = new Arrow(dummyPos, 0, 0, 0, null);
        return arrow;
    }

    public Arrow acquireArrow(Position start, int speed, int damage,
                              double maxDistance, Enemy target) {

        if (available.isEmpty()) {
            return null;
        }

        Arrow arrow = available.poll();

        // Reset state before use
        resetProjectileState(arrow);
        arrow.setPosition(start);
        arrow.setSpeed(speed);
        arrow.setDamage(damage);
        arrow.setMaxDistance(maxDistance);
        arrow.setTarget(target);

        active.add(arrow);
        return arrow;
    }

    public void releaseArrow(Arrow arrow) {
        if (arrow == null) return;

        resetProjectileState(arrow);

        active.remove(arrow);
        available.offer(arrow);
    }

    public List<Arrow> getActiveArrows() {
        return active;
    }

}