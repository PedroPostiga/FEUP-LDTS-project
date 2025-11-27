package com.gladiator.model.attack;

import com.gladiator.model.attack.projectile.Projectile;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.entity.MovingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BowAttackTest {
    private BowAttack bowAttack;
    private MovingEntity attacker;
    private List<Enemy> targets;
    private List<Projectile> projectiles;
    private Enemy enemy1;
    private Enemy enemy2;

    @BeforeEach
    void setUp() {
        targets = new ArrayList<>();
        projectiles = new ArrayList<>();

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

        bowAttack = new BowAttack(20, 10, 100.0, targets, projectiles);
    }

    @Test
    void testAttackFindsClosestEnemy() {
        bowAttack.attack(attacker);

        assertEquals(1, projectiles.size());

        Projectile projectile = projectiles.get(0);
        assertNotNull(projectile);

        // Verify projectile properties
        assertEquals(20, projectile.getDamage());
        assertEquals(0.0, projectile.getVx(), 0.001);
        assertEquals(10.0, projectile.getVy(), 0.001);

        assertEquals(30.0, projectile.getMaxDistance(), 0.001);
    }

    @Test
    void testAttackWithNoTargets() {
        List<Enemy> emptyTargets = new ArrayList<>();
        List<Projectile> emptyProjectiles = new ArrayList<>();

        BowAttack emptyBowAttack = new BowAttack(20, 10, 100.0, emptyTargets, emptyProjectiles);
        emptyBowAttack.attack(attacker);

        assertEquals(0, emptyProjectiles.size());
    }

    @Test
    void testAttackIgnoresDeadEnemies() {
        when(enemy1.isAlive()).thenReturn(false);
        when(enemy2.isAlive()).thenReturn(false);

        bowAttack.attack(attacker);

        assertEquals(0, projectiles.size());
    }

    @Test
    void testAttackUsesMaxDistanceWhenEnemyIsFar() {
        when(enemy1.getPosition()).thenReturn(new Position(200, 0));
        when(enemy1.isAlive()).thenReturn(true);

        targets.clear();
        targets.add(enemy1);

        bowAttack.attack(attacker);

        assertEquals(1, projectiles.size());
        Projectile projectile = projectiles.get(0);

        assertEquals(100.0, projectile.getMaxDistance(), 0.001);
    }

    @Test
    void testAttackUsesActualDistanceWhenEnemyIsClose() {
        when(enemy1.getPosition()).thenReturn(new Position(25, 0));
        when(enemy1.isAlive()).thenReturn(true);

        targets.clear();
        targets.add(enemy1);

        bowAttack.attack(attacker);

        assertEquals(1, projectiles.size());
        Projectile projectile = projectiles.get(0);

        assertEquals(25.0, projectile.getMaxDistance(), 0.001);
    }

    @Test
    void testProjectileCreationWithCorrectParameters() {
        bowAttack.attack(attacker);

        assertEquals(1, projectiles.size());
        Projectile projectile = projectiles.get(0);

        assertEquals(20, projectile.getDamage());
        assertEquals(10, Math.abs(projectile.getVx()) + Math.abs(projectile.getVy()), 1.0);

        assertEquals(0.0, projectile.getPosition().getX(), 0.001);
        assertEquals(0.0, projectile.getPosition().getY(), 0.001);
    }

    @Test
    void testAttackWithMultipleAliveAndDeadEnemies() {
        Enemy enemy3 = mock(Enemy.class);
        when(enemy3.getPosition()).thenReturn(new Position(10, 10));
        when(enemy3.isAlive()).thenReturn(true);

        when(enemy1.isAlive()).thenReturn(false);
        when(enemy2.isAlive()).thenReturn(true);

        targets.add(enemy3);

        bowAttack.attack(attacker);

        assertEquals(1, projectiles.size());

        Projectile projectile = projectiles.get(0);
        assertNotNull(projectile);
    }

    @Test
    void testAttackWithSameDistanceEnemies() {
        when(enemy1.getPosition()).thenReturn(new Position(40, 0));
        when(enemy2.getPosition()).thenReturn(new Position(0, 40));

        bowAttack.attack(attacker);

        assertEquals(1, projectiles.size());
    }

    @Test
    void testProjectileVelocityCalculation() {
        when(enemy1.getPosition()).thenReturn(new Position(30, 40));
        when(enemy1.isAlive()).thenReturn(true);

        targets.clear();
        targets.add(enemy1);

        bowAttack.attack(attacker);

        assertEquals(1, projectiles.size());
        Projectile projectile = projectiles.get(0);

        assertEquals(6.0, projectile.getVx(), 0.001);
        assertEquals(8.0, projectile.getVy(), 0.001);
    }

    @Test
    void testAttackMultipleTimes() {
        bowAttack.attack(attacker);
        assertEquals(1, projectiles.size());

        bowAttack.attack(attacker);
        assertEquals(2, projectiles.size());

        bowAttack.attack(attacker);
        assertEquals(3, projectiles.size());
    }
}
