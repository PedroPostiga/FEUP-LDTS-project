package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.attack.VampireAttack;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.movement.ChaseMovement;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VampireViewerTest {
    private VampireViewer viewer;
    private Vampire vampire;
    private GUI gui;
    private AttackStrategy attackStrategy;

    @BeforeEach
    void setUp() {
        viewer = new VampireViewer();
        gui = mock(GUI.class);
        
        Gladiator gladiator = mock(Gladiator.class);
        attackStrategy = mock(VampireAttack.class);
        vampire = new Vampire(100, 100, 
            new ChaseMovement(2.0, gladiator),
            new VampireAttack(15, 30, gladiator, 0.3));
        vampire.setAttack(attackStrategy);
    }

    @Test
    void testDrawWhenNotAttacking() throws IOException {
        when(attackStrategy.isAttacking(vampire)).thenReturn(false);
        
        viewer.draw(vampire, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/vampire.png", vampire.getPosition());
    }

    @Test
    void testDrawWhenAttacking() throws IOException {
        when(attackStrategy.isAttacking(vampire)).thenReturn(true);
        
        viewer.draw(vampire, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/vampire_attack.png", vampire.getPosition());
    }

    @Test
    void testDrawWithNullAttackStrategy() throws IOException {
        vampire.setAttack(null);
        
        viewer.draw(vampire, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/vampire.png", vampire.getPosition());
    }

    @Test
    void testDrawWithIOException() throws IOException {
        when(attackStrategy.isAttacking(vampire)).thenReturn(false);
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any());
        
        assertThrows(IOException.class, () -> viewer.draw(vampire, gui));
    }

    @Test
    void testDrawMultipleTimes() throws IOException {
        when(attackStrategy.isAttacking(vampire)).thenReturn(false);
        
        viewer.draw(vampire, gui);
        viewer.draw(vampire, gui);
        
        verify(gui, times(2)).drawSprite("sprites/movingEntity/vampire.png", vampire.getPosition());
    }
}

