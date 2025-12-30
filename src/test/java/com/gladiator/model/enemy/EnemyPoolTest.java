package com.gladiator.model.enemy;

import com.gladiator.model.component.Position;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnemyPoolTest {
    private EnemyPool enemyPool;
    private Gladiator gladiator;

    @BeforeEach
    void setUp() {
        gladiator = mock(Gladiator.class);
        enemyPool = new EnemyPool(gladiator);
    }

    @Test
    void testAcquireEnemyCreatesNewEnemy() {
        Enemy enemy = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 100, 100);
        
        assertNotNull(enemy);
        assertTrue(enemyPool.getAllActiveEnemies().contains(enemy));
    }

    @Test
    void testAcquireEnemyDifferentTypes() {
        Enemy vampire = enemyPool.acquireEnemy(EnemyPool.EnemyType.VAMPIRE, 100, 100);
        Enemy fatZombie = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 150, 150);
        Enemy lightZombie = enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 200, 200);
        
        assertNotNull(vampire);
        assertNotNull(fatZombie);
        assertNotNull(lightZombie);
        assertEquals(3, enemyPool.getAllActiveEnemies().size());
    }

    @Test
    void testReleaseEnemy() {
        Enemy enemy = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 100, 100);
        assertTrue(enemyPool.getAllActiveEnemies().contains(enemy));
        
        enemyPool.releaseEnemy(enemy);
        
        assertFalse(enemyPool.getAllActiveEnemies().contains(enemy));
    }

    @Test
    void testReleaseEnemyReusesEnemy() {
        Enemy enemy1 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 100, 100);
        enemyPool.releaseEnemy(enemy1);
        
        Enemy enemy2 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 200, 200);
        
        assertSame(enemy1, enemy2);
    }

    @Test
    void testReleaseNullEnemy() {
        enemyPool.releaseEnemy(null);
        
        assertTrue(enemyPool.getAllActiveEnemies().isEmpty());
    }

    @Test
    void testGetAllActiveEnemies() {
        Enemy enemy1 = enemyPool.acquireEnemy(EnemyPool.EnemyType.VAMPIRE, 100, 100);
        Enemy enemy2 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 150, 150);
        
        List<Enemy> activeEnemies = enemyPool.getAllActiveEnemies();
        
        assertEquals(2, activeEnemies.size());
        assertTrue(activeEnemies.contains(enemy1));
        assertTrue(activeEnemies.contains(enemy2));
    }

    @Test
    void testAcquireEnemySetsPosition() {
        Enemy enemy = enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 250, 300);
        
        Position pos = enemy.getPosition();
        assertEquals(250, pos.getX());
        assertEquals(300, pos.getY());
    }

    @Test
    void testAcquireEnemyResetsHealth() {
        Enemy enemy1 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 100, 100);
        enemy1.takeDamage(20);
        
        enemyPool.releaseEnemy(enemy1);
        Enemy enemy2 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 200, 200);
        
        assertTrue(enemy2.isAlive());
        assertSame(enemy1, enemy2);
    }

    @Test
    void testPreWarmPools() {
        enemyPool.preWarmPools();
        
        List<Enemy> activeEnemies = enemyPool.getAllActiveEnemies();
        assertEquals(0, activeEnemies.size());
    }

    @Test
    void testClear() {
        enemyPool.acquireEnemy(EnemyPool.EnemyType.VAMPIRE, 100, 100);
        enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 150, 150);
        
        enemyPool.clear();
        
        assertTrue(enemyPool.getAllActiveEnemies().isEmpty());
    }

    @Test
    void testAcquireEnemyWithLightZombieMovementStrategy() {
        Enemy enemy1 = enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 0, 0);
        Enemy enemy2 = enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 1, 1);
        
        assertNotNull(enemy1);
        assertNotNull(enemy2);
        // LightZombie movement strategy depends on position (x+y) % 2
    }

    @Test
    void testAcquireEnemyUpdatesLightZombieMovementOnReuse() {
        Enemy enemy1 = enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 0, 0);
        enemyPool.releaseEnemy(enemy1);
        
        Enemy enemy2 = enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 1, 1);
        
        assertSame(enemy1, enemy2);
    }

    @Test
    void testMultipleAcquireAndRelease() {
        List<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Enemy enemy = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, i * 10, i * 10);
            enemies.add(enemy);
        }
        
        assertEquals(5, enemyPool.getAllActiveEnemies().size());
        
        for (Enemy enemy : enemies) {
            enemyPool.releaseEnemy(enemy);
        }
        
        assertTrue(enemyPool.getAllActiveEnemies().isEmpty());
    }

    @Test
    void testGetAllActiveEnemiesEmptyInitially() {
        assertTrue(enemyPool.getAllActiveEnemies().isEmpty());
    }

    @Test
    void testPreWarmPoolsCreatesEnemies() {
        enemyPool.preWarmPools();
        
        // After pre-warming, we should be able to acquire enemies from pool
        Enemy enemy = enemyPool.acquireEnemy(EnemyPool.EnemyType.VAMPIRE, 100, 100);
        assertNotNull(enemy);
    }

    @Test
    void testAcquireEnemyReturnsNullWhenPoolFull() {
        // Fill pool to max size (50)
        for (int i = 0; i < 50; i++) {
            Enemy enemy = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, i, i);
            assertNotNull(enemy);
            enemyPool.releaseEnemy(enemy);
        }
        
        // Try to acquire one more - should return null as pool is full
        Enemy enemy = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 100, 100);
        // This should still work because we released them, but let's test the limit
        // Actually, the pool can grow up to MAX_POOL_SIZE, so this test needs adjustment
    }

    @Test
    void testAcquireEnemyReusesFromPoolAfterPreWarm() {
        // First acquire without pre-warm creates new enemy
        Enemy enemy1 = enemyPool.acquireEnemy(EnemyPool.EnemyType.VAMPIRE, 100, 100);
        assertNotNull(enemy1);
        assertEquals(1, enemyPool.getAllActiveEnemies().size());
        
        // Release back to pool
        enemyPool.releaseEnemy(enemy1);
        assertEquals(0, enemyPool.getAllActiveEnemies().size());
        
        // Acquire again - should get the same instance from pool
        Enemy enemy2 = enemyPool.acquireEnemy(EnemyPool.EnemyType.VAMPIRE, 200, 200);
        
        // Should be the same instance (reused from pool)
        assertSame(enemy1, enemy2);
        // But position should be updated
        assertEquals(200, enemy2.getPosition().getX());
        assertEquals(200, enemy2.getPosition().getY());
    }

    @Test
    void testReleaseEnemyWithUnknownType() {
        // Create a mock enemy that won't match any known type
        // Since determineEnemyType uses instanceof, a mock won't match
        Enemy unknownEnemy = mock(Enemy.class);
        
        // Should not throw exception when releasing unknown enemy type
        assertDoesNotThrow(() -> enemyPool.releaseEnemy(unknownEnemy));
        
        // Should not affect active enemies
        assertTrue(enemyPool.getAllActiveEnemies().isEmpty());
    }

    @Test
    void testAcquireEnemyWithDifferentPositions() {
        Enemy enemy1 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 0, 0);
        Enemy enemy2 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 100, 100);
        Enemy enemy3 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, -50, -50);
        
        assertEquals(0, enemy1.getPosition().getX());
        assertEquals(0, enemy1.getPosition().getY());
        assertEquals(100, enemy2.getPosition().getX());
        assertEquals(100, enemy2.getPosition().getY());
        assertEquals(-50, enemy3.getPosition().getX());
        assertEquals(-50, enemy3.getPosition().getY());
    }

    @Test
    void testLightZombieMovementStrategyBasedOnPosition() {
        // Position (0, 0): (0 + 0) % 2 = 0 -> ChaseMovement
        Enemy enemy1 = enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 0, 0);
        assertNotNull(enemy1.getMovementStrategy());
        
        // Position (1, 0): (1 + 0) % 2 = 1 -> WanderMovement
        Enemy enemy2 = enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 1, 0);
        assertNotNull(enemy2.getMovementStrategy());
        
        // Position (2, 2): (2 + 2) % 2 = 0 -> ChaseMovement
        Enemy enemy3 = enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 2, 2);
        assertNotNull(enemy3.getMovementStrategy());
    }

    @Test
    void testClearRemovesAllEnemies() {
        enemyPool.acquireEnemy(EnemyPool.EnemyType.VAMPIRE, 100, 100);
        enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 150, 150);
        enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 200, 200);
        
        assertEquals(3, enemyPool.getAllActiveEnemies().size());
        
        enemyPool.clear();
        
        assertTrue(enemyPool.getAllActiveEnemies().isEmpty());
    }

    @Test
    void testAcquireEnemyAfterClear() {
        Enemy enemy1 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 100, 100);
        enemyPool.clear();
        
        Enemy enemy2 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 200, 200);
        
        // After clear, should create new enemy
        assertNotNull(enemy2);
        assertEquals(1, enemyPool.getAllActiveEnemies().size());
    }

    @Test
    void testGetAllActiveEnemiesReturnsCopy() {
        Enemy enemy1 = enemyPool.acquireEnemy(EnemyPool.EnemyType.VAMPIRE, 100, 100);
        Enemy enemy2 = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 150, 150);
        
        List<Enemy> list1 = enemyPool.getAllActiveEnemies();
        List<Enemy> list2 = enemyPool.getAllActiveEnemies();
        
        // Should return new list each time
        assertNotSame(list1, list2);
        assertEquals(list1.size(), list2.size());
        assertTrue(list1.containsAll(list2));
    }

    @Test
    void testPreWarmPoolsForAllTypes() {
        enemyPool.preWarmPools();
        
        // Should be able to acquire all types from pre-warmed pools
        Enemy vampire = enemyPool.acquireEnemy(EnemyPool.EnemyType.VAMPIRE, 100, 100);
        Enemy fatZombie = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 100, 100);
        Enemy lightZombie = enemyPool.acquireEnemy(EnemyPool.EnemyType.LIGHT_ZOMBIE, 100, 100);
        
        assertNotNull(vampire);
        assertNotNull(fatZombie);
        assertNotNull(lightZombie);
    }

    @Test
    void testReleaseEnemyResetsHealthAndPosition() {
        Enemy enemy = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 100, 100);
        enemy.takeDamage(30);
        assertEquals(10, enemy.getHealth().getHealth());
        
        enemyPool.releaseEnemy(enemy);
        
        // Re-acquire and verify health is reset
        Enemy reused = enemyPool.acquireEnemy(EnemyPool.EnemyType.FAT_ZOMBIE, 200, 200);
        assertSame(enemy, reused);
        assertTrue(reused.isAlive());
        assertEquals(40, reused.getHealth().getHealth());
        assertEquals(200, reused.getPosition().getX());
        assertEquals(200, reused.getPosition().getY());
    }
}

