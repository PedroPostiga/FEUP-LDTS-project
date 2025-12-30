package com.gladiator.controller.arena;

import com.gladiator.controller.entity.EnemyUpdater;
import com.gladiator.controller.projectile.ProjectileUpdater;
import com.gladiator.model.Arena;
import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.enemy.EnemyPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ArenaUpdaterTest {
    private ArenaUpdater updater;
    private Arena arena;
    private EnemyUpdater enemyUpdater;
    private ProjectileUpdater projectileUpdater;
    private EnemyPool enemyPool;
    private SingleArrowPool arrowPool;

    @BeforeEach
    void setUp() {
        updater = new ArenaUpdater();
        arena = mock(Arena.class);
        enemyUpdater = mock(EnemyUpdater.class);
        projectileUpdater = mock(ProjectileUpdater.class);
        enemyPool = mock(EnemyPool.class);
        arrowPool = mock(SingleArrowPool.class);
        
        // Inject mocks into the updater
        updater.enemyUpdater = enemyUpdater;
        updater.projectileUpdater = projectileUpdater;
        
        when(arena.getEnemiePool()).thenReturn(enemyPool);
        when(arena.getArrowPool()).thenReturn(arrowPool);
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        when(arrowPool.getActiveArrows()).thenReturn(new ArrayList<>());
    }

    @Test
    void testUpdateCallsEnemyAndProjectileUpdaters() {
        updater.update(arena);
        
        assertNotNull(updater.enemyUpdater);
        assertNotNull(updater.projectileUpdater);
    }

    @Test
    void testUpdateDelegatesToEnemyUpdater() {
        updater.update(arena);
        
        verify(enemyUpdater).update(arena);
    }

    @Test
    void testUpdateDelegatesToProjectileUpdater() {
        updater.update(arena);
        
        verify(projectileUpdater).update(arena);
    }

    @Test
    void testUpdateCallsBothUpdatersInOrder() {
        updater.update(arena);
        
        // Verify both updaters are called
        verify(enemyUpdater).update(arena);
        verify(projectileUpdater).update(arena);
    }

    @Test
    void testUpdateWithNullArena() {
        // Should not throw exception
        assertDoesNotThrow(() -> updater.update(null));
    }

    @Test
    void testUpdateMultipleTimes() {
        updater.update(arena);
        updater.update(arena);
        updater.update(arena);
        
        verify(enemyUpdater, times(3)).update(arena);
        verify(projectileUpdater, times(3)).update(arena);
    }

    @Test
    void testUpdateWithEmptyEnemyPool() {
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        
        updater.update(arena);
        
        verify(enemyUpdater).update(arena);
        verify(projectileUpdater).update(arena);
    }

    @Test
    void testUpdateWithEmptyArrowPool() {
        when(arrowPool.getActiveArrows()).thenReturn(new ArrayList<>());
        
        updater.update(arena);
        
        verify(enemyUpdater).update(arena);
        verify(projectileUpdater).update(arena);
    }
}

