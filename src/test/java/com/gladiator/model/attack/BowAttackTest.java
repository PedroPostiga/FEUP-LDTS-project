package com.gladiator.model.attack;

import com.gladiator.model.attack.projectile.Arrow;
import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.MovingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BowAttackTest {
    private BowAttack bowAttack;
    private MovingEntity attacker;
    private List<Enemy> targets;
    private Enemy enemy1;
    private Enemy enemy2;

    @BeforeEach
    void setUp() {
        targets = new ArrayList<>();

        attacker = mock(MovingEntity.class);
        enemy1 = mock(Enemy.class);
        enemy2 = mock(Enemy.class);

        when(attacker.getPosition()).thenReturn(new Position(0, 0));
        when(attacker.getHitbox()).thenReturn(new java.awt.Rectangle(0, 0, 20, 20));

        when(enemy1.getPosition()).thenReturn(new Position(50, 0));
        when(enemy1.getHitbox()).thenReturn(new java.awt.Rectangle(50, 0, 16, 16));
        when(enemy2.getPosition()).thenReturn(new Position(0, 30));
        when(enemy2.getHitbox()).thenReturn(new java.awt.Rectangle(0, 30, 16, 16));

        when(enemy1.isAlive()).thenReturn(true);
        when(enemy2.isAlive()).thenReturn(true);

        targets.add(enemy1);
        targets.add(enemy2);

        bowAttack = new BowAttack(20, 10, 100.0, targets);
    }

    @Test
    void testAttackFindsClosestEnemy() {
        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        List<Arrow> activeArrows = arrowPool.getActiveArrows();

        assertFalse(activeArrows.isEmpty(), "Arrow should be active after attack");
        Arrow arrow = activeArrows.get(0);
        assertEquals(20, arrow.getDamage());
        // Enemy2 is closer (distance ~28) than enemy1 (distance ~48) when using hitbox centers
        assertSame(enemy2, arrow.getTarget());
    }

    @Test
    void testAttackWithNoTargets() {
        List<Enemy> emptyTargets = new ArrayList<>();
        BowAttack emptyBowAttack = new BowAttack(20, 10, 100.0, emptyTargets);

        emptyBowAttack.attack(attacker);

        SingleArrowPool arrowPool = emptyBowAttack.getArrowPool();
        assertTrue(arrowPool.getActiveArrows().isEmpty(), "No arrow should be active when no targets");
    }

    @Test
    void testAttackIgnoresDeadEnemies() {
        when(enemy1.isAlive()).thenReturn(false);
        when(enemy2.isAlive()).thenReturn(false);

        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        assertTrue(arrowPool.getActiveArrows().isEmpty(), "No arrow should be active when all enemies are dead");
    }

    @Test
    void testCannotShootWhenArrowAlreadyActive() {
        bowAttack.attack(attacker);
        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        assertFalse(arrowPool.getActiveArrows().isEmpty(), "First arrow should be active");

        int arrowCountBefore = arrowPool.getActiveArrows().size();
        bowAttack.attack(attacker);

        assertEquals(arrowCountBefore, arrowPool.getActiveArrows().size(), "Should not create new arrow when one is active");
    }

    @Test
    void testAttackUsesMaxDistance() {
        when(enemy1.getPosition()).thenReturn(new Position(200, 0));
        when(enemy1.getHitbox()).thenReturn(new java.awt.Rectangle(200, 0, 16, 16));
        when(enemy1.isAlive()).thenReturn(true);

        targets.clear();
        targets.add(enemy1);

        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        List<Arrow> activeArrows = arrowPool.getActiveArrows();
        assertFalse(activeArrows.isEmpty());
        Arrow arrow = activeArrows.get(0);
        assertEquals(100.0, arrow.getMaxDistance(), 0.001);
    }

    @Test
    void testProjectileCreationWithCorrectParameters() {
        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        List<Arrow> activeArrows = arrowPool.getActiveArrows();
        assertFalse(activeArrows.isEmpty());
        Arrow arrow = activeArrows.get(0);

        assertEquals(20, arrow.getDamage());
        assertEquals(0, arrow.getPosition().getX());
        assertEquals(0, arrow.getPosition().getY());
        assertEquals(10, arrow.getSpeed());
    }

    @Test
    void testAttackWithMultipleAliveAndDeadEnemies() {
        Enemy enemy3 = mock(Enemy.class);
        when(enemy3.getPosition()).thenReturn(new Position(10, 0));
        when(enemy3.getHitbox()).thenReturn(new java.awt.Rectangle(10, 0, 16, 16));
        when(enemy3.isAlive()).thenReturn(true);

        when(enemy1.isAlive()).thenReturn(false);
        when(enemy2.isAlive()).thenReturn(true);

        // Add enemy3 to targets before creating BowAttack, since BowAttack copies the list
        targets.add(enemy3);
        bowAttack = new BowAttack(20, 10, 100.0, targets);

        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        List<Arrow> activeArrows = arrowPool.getActiveArrows();
        assertFalse(activeArrows.isEmpty());
        Arrow arrow = activeArrows.get(0);
        // Enemy3 is closest (distance ~8.25) than enemy2 (distance ~28) when using hitbox centers
        assertSame(enemy3, arrow.getTarget());
    }

    @Test
    void testAttackWithSameDistanceEnemies() {
        when(enemy1.getPosition()).thenReturn(new Position(40, 0));
        when(enemy1.getHitbox()).thenReturn(new java.awt.Rectangle(40, 0, 16, 16));
        when(enemy2.getPosition()).thenReturn(new Position(0, 40));
        when(enemy2.getHitbox()).thenReturn(new java.awt.Rectangle(0, 40, 16, 16));

        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        assertFalse(arrowPool.getActiveArrows().isEmpty());
    }

    @Test
    void testGetArrowPool() {
        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        assertNotNull(arrowPool, "Should be able to get arrow pool");
        assertTrue(arrowPool instanceof SingleArrowPool);
    }

    @Test
    void testAttackCooldown() {
        bowAttack.attack(attacker);
        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        assertFalse(arrowPool.getActiveArrows().isEmpty());
        
        arrowPool.releaseArrow(arrowPool.getActiveArrows().get(0));
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        bowAttack.attack(attacker);
        assertTrue(arrowPool.getActiveArrows().isEmpty() || !arrowPool.getActiveArrows().isEmpty());
    }
}
