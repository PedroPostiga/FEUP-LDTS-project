package com.gladiator.model.attack;

import com.gladiator.model.entity.MovingEntity;

public interface AttackStrategy {
    void attack(MovingEntity attacker);
    
    /**
     * Checks if the given entity is currently in an attack animation state.
     * This is used by viewers to determine which sprite to display.
     * 
     * @param attacker The entity to check
     * @return true if the entity recently attacked and should show attack animation
     */
    default boolean isAttacking(MovingEntity attacker) {
        return false; // Default implementation - override in subclasses
    }
}
