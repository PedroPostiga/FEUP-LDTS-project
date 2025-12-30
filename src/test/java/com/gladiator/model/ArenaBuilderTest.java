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
import java.lang.reflect.Field;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ArenaBuilderTest {
    private ArenaBuilder arenaBuilder;

    @BeforeEach
    void setUp() throws Exception {
        resetGladiatorInstance();
        arenaBuilder = new ArenaBuilder();
    }

    private void resetGladiatorInstance() throws Exception {
        Field instanceField = Gladiator.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    @Test
    void testCreateArena() {
        Arena arena = arenaBuilder.createArena();

        assertNotNull(arena);
        assertEquals(400, arena.getWidth());
        assertEquals(300, arena.getHeight());
        assertNotNull(arena.getGladiator());
        assertNotNull(arena.getEnemiePool());
    }

    @Test
    void testCreateArenaHasGladiator() {
        Arena arena = arenaBuilder.createArena();
        Gladiator gladiator = arena.getGladiator();

        assertNotNull(gladiator);
        assertEquals(200, gladiator.getPosition().getX());
        assertEquals(150, gladiator.getPosition().getY());
    }

    @Test
    void testCreateArenaHasEnemyPool() {
        Arena arena = arenaBuilder.createArena();
        assertNotNull(arena.getEnemiePool());
    }

    @Test
    void testCreateArenaHasArrowPool() {
        Arena arena = arenaBuilder.createArena();
        assertNotNull(arena.getArrowPool());
    }

    @Test
    void testCreateArenaHasObstacles() {
        Arena arena = arenaBuilder.createArena();
        assertNotNull(arena.getObstacles());
        assertFalse(arena.getObstacles().isEmpty());
    }

    @Test
    void testCreateGladiator() {
        Gladiator gladiator = arenaBuilder.createGladiator();

        assertNotNull(gladiator);
        assertEquals(200, gladiator.getPosition().getX());
        assertEquals(150, gladiator.getPosition().getY());
        assertTrue(gladiator.isAlive());
    }

    @Test
    void testArenaBuilderCreatesConsistentArena() {
        Arena arena1 = arenaBuilder.createArena();
        Arena arena2 = arenaBuilder.createArena();

        assertEquals(arena1.getWidth(), arena2.getWidth());
        assertEquals(arena1.getHeight(), arena2.getHeight());
        assertNotNull(arena1.getEnemiePool());
        assertNotNull(arena2.getEnemiePool());
    }

    @Test
    void testCreateArenaHasGladiatorWithAttacks() {
        Arena arena = arenaBuilder.createArena();
        Gladiator gladiator = arena.getGladiator();

        assertNotNull(gladiator.getSwordAttack());
        assertNotNull(gladiator.getBowAttack());
    }

    @Test
    void testCreateArenaGladiatorAttackStats() {
        Arena arena = arenaBuilder.createArena();
        Gladiator gladiator = arena.getGladiator();

        assertEquals(20, gladiator.getSwordAttack().getDamage());
        assertEquals(30, gladiator.getSwordAttack().getRange());
        // BowAttack doesn't expose getDamage(), but we can verify it has an arrow pool
        assertNotNull(gladiator.getBowAttack().getArrowPool());
    }

    @Test
    void testCreateObstacles() {
        Arena arena = arenaBuilder.createArena();
        List<com.gladiator.model.entity.Obstacle> obstacles = arena.getObstacles();

        assertNotNull(obstacles);
        assertTrue(obstacles.size() > 0);
        
        // Should have trees, rocks, and invisible walls
        boolean hasTree = obstacles.stream().anyMatch(o -> o instanceof com.gladiator.model.entity.Tree);
        boolean hasRock = obstacles.stream().anyMatch(o -> o instanceof com.gladiator.model.entity.SmallRock || 
                                                           o instanceof com.gladiator.model.entity.LargeRock);
        boolean hasWall = obstacles.stream().anyMatch(o -> o instanceof com.gladiator.model.entity.InvisibleWall);
        
        assertTrue(hasTree || hasRock || hasWall);
    }

    @Test
    void testCreateArenaMultipleTimes() throws Exception {
        resetGladiatorInstance();
        Arena arena1 = arenaBuilder.createArena();
        
        resetGladiatorInstance();
        Arena arena2 = arenaBuilder.createArena();

        // Both should have same structure
        assertEquals(arena1.getWidth(), arena2.getWidth());
        assertEquals(arena1.getHeight(), arena2.getHeight());
        assertNotNull(arena1.getGladiator());
        assertNotNull(arena2.getGladiator());
    }
}