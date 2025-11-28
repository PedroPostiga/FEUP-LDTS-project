package com.gladiator.model.attack;

import com.gladiator.model.attack.projectile.Projectile;
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

        when(enemy1.getPosition()).thenReturn(new Position(50, 0));
        when(enemy2.getPosition()).thenReturn(new Position(0, 30));

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
        Projectile arrow = arrowPool.getActiveArrow();

        assertNotNull(arrow, "Arrow should be active after attack");
        assertEquals(20, arrow.getDamage());
        assertEquals(0.0, arrow.getVx(), 0.001);
        assertEquals(10.0, arrow.getVy(), 0.001);
        assertEquals(30.0, arrow.getMaxDistance(), 0.001);
    }

    @Test
    void testAttackWithNoTargets() {
        List<Enemy> emptyTargets = new ArrayList<>();
        BowAttack emptyBowAttack = new BowAttack(20, 10, 100.0, emptyTargets);

        emptyBowAttack.attack(attacker);

        SingleArrowPool arrowPool = emptyBowAttack.getArrowPool();
        assertNull(arrowPool.getActiveArrow(), "No arrow should be active when no targets");
    }

    @Test
    void testAttackIgnoresDeadEnemies() {
        when(enemy1.isAlive()).thenReturn(false);
        when(enemy2.isAlive()).thenReturn(false);

        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        assertNull(arrowPool.getActiveArrow(), "No arrow should be active when all enemies are dead");
    }

    @Test
    void testCannotShootWhenArrowAlreadyActive() {
        bowAttack.attack(attacker);
        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        assertNotNull(arrowPool.getActiveArrow(), "First arrow should be active");

        bowAttack.attack(attacker);

        Projectile arrow = arrowPool.getActiveArrow();
        assertNotNull(arrow, "Arrow should still be active");
    }

    @Test
    void testAttackUsesMaxDistanceWhenEnemyIsFar() {
        when(enemy1.getPosition()).thenReturn(new Position(200, 0));
        when(enemy1.isAlive()).thenReturn(true);

        targets.clear();
        targets.add(enemy1);

        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        Projectile arrow = arrowPool.getActiveArrow();
        assertNotNull(arrow);
        assertEquals(100.0, arrow.getMaxDistance(), 0.001);
    }

    @Test
    void testAttackUsesActualDistanceWhenEnemyIsClose() {
        when(enemy1.getPosition()).thenReturn(new Position(25, 0));
        when(enemy1.isAlive()).thenReturn(true);

        targets.clear();
        targets.add(enemy1);

        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        Projectile arrow = arrowPool.getActiveArrow();
        assertNotNull(arrow);
        assertEquals(25.0, arrow.getMaxDistance(), 0.001);
    }

    @Test
    void testProjectileCreationWithCorrectParameters() {
        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        Projectile arrow = arrowPool.getActiveArrow();
        assertNotNull(arrow);

        assertEquals(20, arrow.getDamage());
        assertEquals(0.0, arrow.getPosition().getX(), 0.001);
        assertEquals(0.0, arrow.getPosition().getY(), 0.001);
    }

    @Test
    void testAttackWithMultipleAliveAndDeadEnemies() {
        Enemy enemy3 = mock(Enemy.class);
        when(enemy3.getPosition()).thenReturn(new Position(10, 0));
        when(enemy3.isAlive()).thenReturn(true);

        when(enemy1.isAlive()).thenReturn(false);
        when(enemy2.isAlive()).thenReturn(true);

        targets.add(enemy3);

        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        Projectile arrow = arrowPool.getActiveArrow();
        assertNotNull(arrow);

        assertEquals(10.0, arrow.getVx(), 0.001);
        assertEquals(0.0, arrow.getVy(), 0.001);
    }

    @Test
    void testAttackWithSameDistanceEnemies() {
        when(enemy1.getPosition()).thenReturn(new Position(40, 0));
        when(enemy2.getPosition()).thenReturn(new Position(0, 40));

        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        assertNotNull(arrowPool.getActiveArrow());
    }

    @Test
    void testProjectileVelocityCalculation() {
        when(enemy1.getPosition()).thenReturn(new Position(30, 40));
        when(enemy1.isAlive()).thenReturn(true);

        targets.clear();
        targets.add(enemy1);

        bowAttack.attack(attacker);

        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        Projectile arrow = arrowPool.getActiveArrow();
        assertNotNull(arrow);

        assertEquals(6.0, arrow.getVx(), 0.001);
        assertEquals(8.0, arrow.getVy(), 0.001);
    }

    @Test
    void testArrowPoolCanBeReusedAfterReturn() {
        SingleArrowPool arrowPool = bowAttack.getArrowPool();

        bowAttack.attack(attacker);
        assertNotNull(arrowPool.getActiveArrow(), "Arrow should be active after first attack");

        arrowPool.returnArrow();
        assertNull(arrowPool.getActiveArrow(), "Arrow should be inactive after return");

        bowAttack.attack(attacker);
        assertNotNull(arrowPool.getActiveArrow(), "Arrow should be active again after second attack");
    }

    @Test
    void testGetArrowPool() {
        SingleArrowPool arrowPool = bowAttack.getArrowPool();
        assertNotNull(arrowPool, "Should be able to get arrow pool");
        assertTrue(arrowPool instanceof SingleArrowPool);
    }
}