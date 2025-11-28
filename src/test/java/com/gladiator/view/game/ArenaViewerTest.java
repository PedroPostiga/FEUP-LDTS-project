package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.Arena;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.attack.VampireAttack;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.Enemy;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.gladiator.Gladiator;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.movement.WanderMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

public class ArenaViewerTest {

    private GUI gui;
    private Arena arena;
    private Gladiator gladiator;
    private ArenaViewer arenaViewer;

    @BeforeEach
    public void setUp() {
        gui = mock(GUI.class);
        arena = mock(Arena.class);
        gladiator = mock(Gladiator.class);
        arenaViewer = new ArenaViewer(arena);

        when(arena.getGladiator()).thenReturn(gladiator);
        when(gladiator.getPosition()).thenReturn(new Position(5, 5));
    }

    @Test
    public void testArenaViewerDrawsAllEntities() throws IOException {
        Vampire vampire = new Vampire(1, 1, new ChaseMovement(5, gladiator),
                new VampireAttack(10, 10, gladiator, 0.5));
        FatZombie fatZombie = new FatZombie(2, 2, new ChaseMovement(5, gladiator),
                new SwordAttack(10, 10, List.of(gladiator)));

        List<Enemy> enemies = Arrays.asList(vampire, fatZombie);
        when(arena.getEnemies()).thenReturn(enemies);

        arenaViewer.draw(gui);

        verify(gui).clear();
        verify(gui).refresh();
        verify(gui).drawGladiator(gladiator.getPosition());
    }

    @Test
    public void testArenaViewerWithEmptyEnemiesList() throws IOException {
        List<Enemy> enemies = Arrays.asList();
        when(arena.getEnemies()).thenReturn(enemies);

        arenaViewer.draw(gui);

        verify(gui).clear();
        verify(gui).refresh();
        verify(gui).drawGladiator(gladiator.getPosition());
    }

    @Test
    public void testArenaViewerWithMultipleEnemyTypes() throws IOException {
        Vampire vampire = new Vampire(1, 1, new ChaseMovement(5, gladiator),
                new VampireAttack(10, 10, gladiator, 0.5));
        FatZombie fatZombie = new FatZombie(2, 2, new ChaseMovement(5, gladiator),
                new SwordAttack(10, 10, List.of(gladiator)));
        LightZombie lightZombie = new LightZombie(3, 3, new WanderMovement(),
                new SwordAttack(10, 10, List.of(gladiator)));

        List<Enemy> enemies = Arrays.asList(vampire, fatZombie, lightZombie);
        when(arena.getEnemies()).thenReturn(enemies);

        arenaViewer.draw(gui);

        verify(gui).clear();
        verify(gui).refresh();
        verify(gui).drawGladiator(gladiator.getPosition());
    }

    @Test
    public void testArenaViewerCallsCorrectDrawMethods() throws IOException {
        Vampire vampire = new Vampire(1, 1, new ChaseMovement(5, gladiator),
                new VampireAttack(10, 10, gladiator, 0.5));
        FatZombie fatZombie = new FatZombie(2, 2, new ChaseMovement(5, gladiator),
                new SwordAttack(10, 10, List.of(gladiator)));

        List<Enemy> enemies = Arrays.asList(vampire, fatZombie);
        when(arena.getEnemies()).thenReturn(enemies);

        arenaViewer.draw(gui);

        verify(gui).drawGladiator(gladiator.getPosition());
        verify(gui).drawVampire(vampire.getPosition());
        verify(gui).drawFatZombie(fatZombie.getPosition());
    }

    @Test
    public void testArenaViewerWithDeadEnemy() throws IOException {
        Vampire vampire = new Vampire(1, 1, new ChaseMovement(5, gladiator),
                new VampireAttack(10, 10, gladiator, 0.5));

        vampire.takeDamage(1000);

        List<Enemy> enemies = Arrays.asList(vampire);
        when(arena.getEnemies()).thenReturn(enemies);

        arenaViewer.draw(gui);

        verify(gui).drawVampire(vampire.getPosition());
        verify(gui).drawGladiator(gladiator.getPosition());
    }

}