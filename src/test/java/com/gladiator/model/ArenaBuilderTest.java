package com.gladiator.model;

import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.attack.VampireAttack;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.WanderMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ArenaBuilderTest {
    private ArenaBuilder arenaBuilder;

    @BeforeEach
    void setUp() {
        arenaBuilder = new ArenaBuilder();
    }

    @Test
    void testCreateArena() {
        Arena arena = arenaBuilder.createArena();

        assertNotNull(arena);
        assertEquals(20, arena.getWidth());
        assertEquals(20, arena.getHeight());
        assertNotNull(arena.getGladiator());
        assertNotNull(arena.getEnemies());
    }

    @Test
    void testCreateArenaHasGladiator() {
        Arena arena = arenaBuilder.createArena();
        Gladiator gladiator = arena.getGladiator();

        assertNotNull(gladiator);
        assertEquals(5, gladiator.getPosition().getX(), 0.001);
        assertEquals(5, gladiator.getPosition().getY(), 0.001);
    }

    @Test
    void testCreateArenaHasEnemies() {
        Arena arena = arenaBuilder.createArena();
        List<Enemy> enemies = arena.getEnemies();

        assertNotNull(enemies);
        assertEquals(15, enemies.size());

        long vampireCount = enemies.stream().filter(e -> e instanceof Vampire).count();
        long fatZombieCount = enemies.stream().filter(e -> e instanceof FatZombie).count();
        long lightZombieCount = enemies.stream().filter(e -> e instanceof LightZombie).count();

        assertEquals(5, vampireCount);
        assertEquals(5, fatZombieCount);
        assertEquals(5, lightZombieCount);
    }

    @Test
    void testEnemyPositionsAreSet() {
        Arena arena = arenaBuilder.createArena();
        List<Enemy> enemies = arena.getEnemies();

        for (int i = 0; i < 5; i++) {
            Enemy vampire = enemies.get(i * 3);
            assertEquals(i, vampire.getPosition().getX(), 0.001);
            assertEquals(1, vampire.getPosition().getY(), 0.001);

            Enemy fatZombie = enemies.get(i * 3 + 1);
            assertEquals(i, fatZombie.getPosition().getX(), 0.001);
            assertEquals(2, fatZombie.getPosition().getY(), 0.001);

            Enemy lightZombie = enemies.get(i * 3 + 2);
            assertEquals(i, lightZombie.getPosition().getX(), 0.001);
            assertEquals(3, lightZombie.getPosition().getY(), 0.001);
        }
    }

    @Test
    void testEnemyMovementStrategies() {
        Arena arena = arenaBuilder.createArena();
        List<Enemy> enemies = arena.getEnemies();

        for (int i = 0; i < 5; i++) {
            Vampire vampire = (Vampire) enemies.get(i * 3);
            assertTrue(vampire.getMovementStrategy() instanceof ChaseMovement);

            FatZombie fatZombie = (FatZombie) enemies.get(i * 3 + 1);
            assertTrue(fatZombie.getMovementStrategy() instanceof ChaseMovement);

            LightZombie lightZombie = (LightZombie) enemies.get(i * 3 + 2);
            assertTrue(lightZombie.getMovementStrategy() instanceof WanderMovement);
        }
    }

    @Test
    void testEnemyAttackStrategies() {
        Arena arena = arenaBuilder.createArena();
        List<Enemy> enemies = arena.getEnemies();

        for (int i = 0; i < 5; i++) {
            Vampire vampire = (Vampire) enemies.get(i * 3);
            assertTrue(vampire.getAttackStrategy() instanceof VampireAttack);

            FatZombie fatZombie = (FatZombie) enemies.get(i * 3 + 1);
            assertTrue(fatZombie.getAttackStrategy() instanceof SwordAttack);

            LightZombie lightZombie = (LightZombie) enemies.get(i * 3 + 2);
            assertTrue(lightZombie.getAttackStrategy() instanceof SwordAttack);
        }
    }

    @Test
    void testCreateGladiator() {
        Gladiator gladiator = arenaBuilder.createGladiator();

        assertNotNull(gladiator);
        assertEquals(5, gladiator.getPosition().getX(), 0.001);
        assertEquals(5, gladiator.getPosition().getY(), 0.001);
        assertTrue(gladiator.isAlive());
    }

    @Test
    void testCreateEnemiesUsesArenaGladiator() {
        Arena arena = new Arena(20, 20);
        Gladiator testGladiator = new Gladiator(10, 10, 5, 5, 100, 5);
        arena.setGladiator(testGladiator);

        List<Enemy> enemies = arenaBuilder.createEnemies(arena);

        for (Enemy enemy : enemies) {
            if (enemy.getMovementStrategy() instanceof ChaseMovement) {
                ChaseMovement chase = (ChaseMovement) enemy.getMovementStrategy();
                assertNotNull(chase);
            }
        }
    }

    @Test
    void testArenaBuilderCreatesConsistentArena() {
        Arena arena1 = arenaBuilder.createArena();
        Arena arena2 = arenaBuilder.createArena();

        assertEquals(arena1.getWidth(), arena2.getWidth());
        assertEquals(arena1.getHeight(), arena2.getHeight());
        assertEquals(arena1.getEnemies().size(), arena2.getEnemies().size());

        long vampires1 = arena1.getEnemies().stream().filter(e -> e instanceof Vampire).count();
        long vampires2 = arena2.getEnemies().stream().filter(e -> e instanceof Vampire).count();
        assertEquals(vampires1, vampires2);
    }
}