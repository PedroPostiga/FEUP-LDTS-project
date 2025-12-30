package com.gladiator.model.attack.projectile;

import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SingleArrowPoolTest {
    private SingleArrowPool pool;
    private Enemy target;

    @BeforeEach
    void setUp() {
        pool = new SingleArrowPool();
        target = mock(Enemy.class);
    }

    @Test
    void testInitialState() {
        assertEquals(0, pool.getActiveArrows().size());
    }

    @Test
    void testAcquireArrow() {
        Position start = new Position(100, 100);
        pool.acquireArrow(start, 10, 5, 100.0, target);
        
        assertEquals(1, pool.getActiveArrows().size());
        Arrow arrow = pool.getActiveArrows().get(0);
        assertEquals(start.getX(), arrow.getPosition().getX());
        assertEquals(start.getY(), arrow.getPosition().getY());
        assertEquals(10, arrow.getSpeed());
        assertEquals(5, arrow.getDamage());
        assertEquals(100.0, arrow.getMaxDistance());
        assertSame(target, arrow.getTarget());
    }

    @Test
    void testReleaseArrow() {
        Position start = new Position(100, 100);
        pool.acquireArrow(start, 10, 5, 100.0, target);
        
        Arrow arrow = pool.getActiveArrows().get(0);
        pool.releaseArrow(arrow);
        
        assertEquals(0, pool.getActiveArrows().size());
    }

    @Test
    void testReleaseNullArrow() {
        pool.releaseArrow(null);
        
        assertEquals(0, pool.getActiveArrows().size());
    }

    @Test
    void testAcquireArrowReusesReleasedArrow() {
        Position start1 = new Position(100, 100);
        pool.acquireArrow(start1, 10, 5, 100.0, target);
        
        Arrow arrow1 = pool.getActiveArrows().get(0);
        pool.releaseArrow(arrow1);
        
        Position start2 = new Position(200, 200);
        pool.acquireArrow(start2, 15, 8, 200.0, target);
        
        Arrow arrow2 = pool.getActiveArrows().get(0);
        assertSame(arrow1, arrow2);
        assertEquals(start2.getX(), arrow2.getPosition().getX());
        assertEquals(start2.getY(), arrow2.getPosition().getY());
    }

    @Test
    void testResetProjectileState() {
        Position start = new Position(100, 100);
        pool.acquireArrow(start, 10, 5, 100.0, target);
        
        Arrow arrow = pool.getActiveArrows().get(0);
        Arrow resetArrow = pool.resetProjectileState(arrow);
        
        assertSame(arrow, resetArrow);
        assertEquals(0, resetArrow.getPosition().getX());
        assertEquals(0, resetArrow.getPosition().getY());
        assertEquals(0, resetArrow.getSpeed());
        assertEquals(0, resetArrow.getDamage());
        assertEquals(0.0, resetArrow.getMaxDistance());
        assertNull(resetArrow.getTarget());
    }

    @Test
    void testMultipleAcquireRelease() {
        Position start1 = new Position(100, 100);
        pool.acquireArrow(start1, 10, 5, 100.0, target);
        
        Arrow arrow1 = pool.getActiveArrows().get(0);
        pool.releaseArrow(arrow1);
        
        Position start2 = new Position(200, 200);
        pool.acquireArrow(start2, 15, 8, 200.0, target);
        
        assertEquals(1, pool.getActiveArrows().size());
    }

    @Test
    void testGetActiveArrows() {
        Position start1 = new Position(100, 100);
        pool.acquireArrow(start1, 10, 5, 100.0, target);
        
        assertEquals(1, pool.getActiveArrows().size());
        assertTrue(pool.getActiveArrows().contains(pool.getActiveArrows().get(0)));
    }

    @Test
    void testAcquireArrowWithZeroSpeed() {
        Position start = new Position(100, 100);
        pool.acquireArrow(start, 0, 5, 100.0, target);
        
        Arrow arrow = pool.getActiveArrows().get(0);
        assertEquals(0, arrow.getSpeed());
    }

    @Test
    void testAcquireArrowWithZeroDamage() {
        Position start = new Position(100, 100);
        pool.acquireArrow(start, 10, 0, 100.0, target);
        
        Arrow arrow = pool.getActiveArrows().get(0);
        assertEquals(0, arrow.getDamage());
    }

    @Test
    void testAcquireArrowWithZeroMaxDistance() {
        Position start = new Position(100, 100);
        pool.acquireArrow(start, 10, 5, 0.0, target);
        
        Arrow arrow = pool.getActiveArrows().get(0);
        assertEquals(0.0, arrow.getMaxDistance());
    }

    @Test
    void testAcquireArrowWithNullTarget() {
        Position start = new Position(100, 100);
        pool.acquireArrow(start, 10, 5, 100.0, null);
        
        Arrow arrow = pool.getActiveArrows().get(0);
        assertNull(arrow.getTarget());
    }

    @Test
    void testResetProjectileStateWithNullArrow() {
        assertNull(pool.resetProjectileState(null));
    }

    @Test
    void testAcquireArrowMultipleTimesReusesSameArrow() {
        Position start1 = new Position(100, 100);
        pool.acquireArrow(start1, 10, 5, 100.0, target);
        Arrow arrow1 = pool.getActiveArrows().get(0);
        pool.releaseArrow(arrow1);
        
        Position start2 = new Position(200, 200);
        pool.acquireArrow(start2, 15, 8, 200.0, target);
        Arrow arrow2 = pool.getActiveArrows().get(0);
        
        assertSame(arrow1, arrow2);
    }

    @Test
    void testGetActiveArrowsReturnsSameListInstance() {
        Position start = new Position(100, 100);
        pool.acquireArrow(start, 10, 5, 100.0, target);
        
        var list1 = pool.getActiveArrows();
        var list2 = pool.getActiveArrows();
        
        // getActiveArrows() returns the actual list instance, not a copy
        assertSame(list1, list2);
        assertEquals(list1.size(), list2.size());
    }
}

