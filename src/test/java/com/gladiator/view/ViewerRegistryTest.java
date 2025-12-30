package com.gladiator.view;

import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.view.game.EntityViewer;
import com.gladiator.view.game.FatZombieViewer;
import com.gladiator.view.game.LightZombieViewer;
import com.gladiator.view.game.VampireViewer;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ViewerRegistryTest {

    @Test
    public void testGetViewerForVampire() {
        Vampire vampire = new Vampire(0, 0, new ChaseMovement(5, null), null);
        EntityViewer<Vampire> viewer = ViewerRegistry.getViewer(vampire);

        assertNotNull(viewer);
        assertTrue(viewer instanceof VampireViewer);
    }

    @Test
    public void testGetViewerForFatZombie() {
        FatZombie fatZombie = new FatZombie(0, 0, new ChaseMovement(5, null),
                new SwordAttack(10, 10, List.of()));
        EntityViewer<FatZombie> viewer = ViewerRegistry.getViewer(fatZombie);

        assertNotNull(viewer);
        assertTrue(viewer instanceof FatZombieViewer);
    }

    @Test
    public void testGetViewerForLightZombie() {
        LightZombie lightZombie = new LightZombie(0, 0, null,
                new SwordAttack(10, 10, List.of()));
        EntityViewer<LightZombie> viewer = ViewerRegistry.getViewer(lightZombie);

        assertNotNull(viewer);
        assertTrue(viewer instanceof LightZombieViewer);
    }

    @Test
    public void testSameViewerInstanceReturnedForSameEnemyType() {
        Vampire vampire1 = new Vampire(0, 0, new ChaseMovement(5, null), null);
        Vampire vampire2 = new Vampire(1, 1, new ChaseMovement(5, null), null);

        EntityViewer<Vampire> viewer1 = ViewerRegistry.getViewer(vampire1);
        EntityViewer<Vampire> viewer2 = ViewerRegistry.getViewer(vampire2);

        assertSame(viewer1, viewer2);
    }

    @Test
    public void testGetViewerForObstacles() {
        com.gladiator.model.entity.SmallRock smallRock = new com.gladiator.model.entity.SmallRock(0, 0);
        com.gladiator.model.entity.LargeRock largeRock = new com.gladiator.model.entity.LargeRock(0, 0);
        com.gladiator.model.entity.Tree tree = new com.gladiator.model.entity.Tree(0, 0);

        com.gladiator.view.game.EntityViewer<com.gladiator.model.entity.SmallRock> smallRockViewer = 
            ViewerRegistry.getViewer(smallRock);
        com.gladiator.view.game.EntityViewer<com.gladiator.model.entity.LargeRock> largeRockViewer = 
            ViewerRegistry.getViewer(largeRock);
        com.gladiator.view.game.EntityViewer<com.gladiator.model.entity.Tree> treeViewer = 
            ViewerRegistry.getViewer(tree);

        assertNotNull(smallRockViewer);
        assertNotNull(largeRockViewer);
        assertNotNull(treeViewer);
    }

    @Test
    public void testGetViewerForObstacleReturnsSameInstance() {
        com.gladiator.model.entity.SmallRock rock1 = new com.gladiator.model.entity.SmallRock(0, 0);
        com.gladiator.model.entity.SmallRock rock2 = new com.gladiator.model.entity.SmallRock(10, 10);

        com.gladiator.view.game.EntityViewer<com.gladiator.model.entity.SmallRock> viewer1 = 
            ViewerRegistry.getViewer(rock1);
        com.gladiator.view.game.EntityViewer<com.gladiator.model.entity.SmallRock> viewer2 = 
            ViewerRegistry.getViewer(rock2);

        assertSame(viewer1, viewer2);
    }
}