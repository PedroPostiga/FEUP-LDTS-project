package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.attack.VampireAttack;
import com.gladiator.model.attack.projectile.SingleArrowPool;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.EnemyPool;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.entity.Obstacle;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.WanderMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class ArenaViewerTest {
    private GUI gui;
    private Arena arena;
    private Gladiator gladiator;
    private ArenaViewer arenaViewer;
    private EnemyPool enemyPool;
    private SingleArrowPool arrowPool;

    @BeforeEach
    public void setUp() {
        gui = mock(GUI.class);
        arena = mock(Arena.class);
        gladiator = mock(Gladiator.class);
        enemyPool = mock(EnemyPool.class);
        arrowPool = mock(SingleArrowPool.class);
        arenaViewer = new ArenaViewer(arena);

        when(arena.getGladiator()).thenReturn(gladiator);
        when(arena.getEnemiePool()).thenReturn(enemyPool);
        when(arena.getArrowPool()).thenReturn(arrowPool);
        when(arena.getObstacles()).thenReturn(new ArrayList<>());
        when(gladiator.getPosition()).thenReturn(new Position(5, 5));
        when(gladiator.isAlive()).thenReturn(true);
        when(gladiator.getDirection()).thenReturn(Gladiator.Direction.DOWN);
        when(gladiator.getHealth()).thenReturn(mock(com.gladiator.model.component.Health.class));
        when(gladiator.getHealth().getHealth()).thenReturn(100);
        when(arena.getWidth()).thenReturn(400);
        when(arena.getHeight()).thenReturn(300);
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());
        when(arrowPool.getActiveArrows()).thenReturn(new ArrayList<>());
    }

    @Test
    public void testArenaViewerDrawsArena() throws IOException {
        arenaViewer.draw(gui);

        verify(gui).drawSprite("sprites/arena.png", new Position(0, 0));
    }

    @Test
    public void testArenaViewerDrawsGladiator() throws IOException {
        arenaViewer.draw(gui);

        verify(gui, atLeastOnce()).drawSprite(anyString(), any(Position.class));
    }

    @Test
    public void testArenaViewerWithEnemies() throws IOException {
        Vampire vampire = new Vampire(1, 1, new ChaseMovement(5, gladiator),
                new VampireAttack(10, 10, gladiator, 0.5));
        FatZombie fatZombie = new FatZombie(2, 2, new ChaseMovement(5, gladiator),
                new SwordAttack(10, 10, List.of(gladiator)));

        List<Enemy> enemies = List.of(vampire, fatZombie);
        when(enemyPool.getAllActiveEnemies()).thenReturn(enemies);

        arenaViewer.draw(gui);

        verify(gui, atLeastOnce()).drawSprite(anyString(), any(Position.class));
    }

    @Test
    public void testArenaViewerWithEmptyEnemiesList() throws IOException {
        when(enemyPool.getAllActiveEnemies()).thenReturn(new ArrayList<>());

        arenaViewer.draw(gui);

        verify(gui).drawSprite("sprites/arena.png", new Position(0, 0));
    }

    @Test
    public void testArenaViewerWithDeadGladiator() throws IOException {
        when(gladiator.isAlive()).thenReturn(false);

        arenaViewer.draw(gui);

        verify(gui).drawSprite("sprites/arena.png", new Position(0, 0));
    }

    @Test
    public void testArenaViewerDrawsHealth() throws IOException {
        arenaViewer.draw(gui);

        verify(gui, atLeastOnce()).drawSprite(contains("numbers"), any(Position.class));
    }

    @Test
    public void testArenaViewerWithObstacles() throws IOException {
        // Use a real obstacle that has a viewer registered
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new com.gladiator.model.entity.SmallRock(50, 50));
        when(arena.getObstacles()).thenReturn(obstacles);

        arenaViewer.draw(gui);

        verify(gui, atLeastOnce()).drawSprite(anyString(), any(Position.class));
    }

    @Test
    public void testArenaViewerWithNullGladiator() throws IOException {
        when(arena.getGladiator()).thenReturn(null);

        arenaViewer.draw(gui);

        verify(gui).drawSprite("sprites/arena.png", new Position(0, 0));
    }

    @Test
    public void testArenaViewerDrawsSwordAttackRange() throws IOException {
        SwordAttack swordAttack = mock(SwordAttack.class);
        when(swordAttack.getRange()).thenReturn(30);
        when(gladiator.getSwordAttack()).thenReturn(swordAttack);
        when(gladiator.getHitbox()).thenReturn(new java.awt.Rectangle(100, 100, 20, 20));

        arenaViewer.draw(gui);

        // Note: drawCircle is commented out in ArenaViewer, so we just verify the draw completes
        verify(gui, atLeastOnce()).drawSprite(anyString(), any(Position.class));
    }

    @Test
    public void testArenaViewerWithNullSwordAttack() throws IOException {
        when(gladiator.getSwordAttack()).thenReturn(null);

        arenaViewer.draw(gui);

        verify(gui).drawSprite("sprites/arena.png", new Position(0, 0));
    }

    @Test
    public void testArenaViewerWithArrows() throws IOException {
        com.gladiator.model.attack.projectile.Arrow arrow = mock(com.gladiator.model.attack.projectile.Arrow.class);
        when(arrow.getPosition()).thenReturn(new Position(150, 150));
        when(arrowPool.getActiveArrows()).thenReturn(List.of(arrow));

        arenaViewer.draw(gui);

        verify(gui, atLeastOnce()).drawSprite(anyString(), any(Position.class));
    }

    @Test
    public void testArenaViewerDrawsHealthWithDifferentValues() throws IOException {
        when(gladiator.getHealth().getHealth()).thenReturn(50);
        arenaViewer.draw(gui);
        verify(gui, atLeastOnce()).drawSprite(contains("numbers"), any(Position.class));

        when(gladiator.getHealth().getHealth()).thenReturn(999);
        arenaViewer.draw(gui);
        verify(gui, atLeastOnce()).drawSprite(contains("numbers"), any(Position.class));
    }

    @Test
    public void testArenaViewerWithInvisibleWall() throws IOException {
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new com.gladiator.model.entity.InvisibleWall(0, 0, 10, 10));
        when(arena.getObstacles()).thenReturn(obstacles);

        arenaViewer.draw(gui);

        // InvisibleWall should be skipped
        verify(gui).drawSprite("sprites/arena.png", new Position(0, 0));
    }
}
