package com.gladiator.model.enemy;

import com.gladiator.model.component.Position;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}

