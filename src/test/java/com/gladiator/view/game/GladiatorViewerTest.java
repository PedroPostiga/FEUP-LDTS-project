package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.attack.BowAttack;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.component.Position;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GladiatorViewerTest {
    private GladiatorViewer viewer;
    private Gladiator gladiator;
    private GUI gui;
    private SwordAttack swordAttack;

    @BeforeEach
    void setUp() throws Exception {
        viewer = new GladiatorViewer();
        gui = mock(GUI.class);
        
        // Reset singleton
        Field instanceField = Gladiator.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        
        gladiator = Gladiator.getInstance(100, 100, 20, 20, 100, 5);
        swordAttack = mock(SwordAttack.class);
        gladiator.setSwordAttack(swordAttack);
    }

    @Test
    void testDrawWithDownDirection() throws IOException {
        gladiator.setDirection(Gladiator.Direction.DOWN);
        when(swordAttack.isAttacking(gladiator)).thenReturn(false);
        
        viewer.draw(gladiator, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/gladiator.png", gladiator.getPosition());
    }

    @Test
    void testDrawWithUpDirection() throws IOException {
        gladiator.setDirection(Gladiator.Direction.UP);
        when(swordAttack.isAttacking(gladiator)).thenReturn(false);
        
        viewer.draw(gladiator, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/gladiator_up.png", gladiator.getPosition());
    }

    @Test
    void testDrawWithLeftDirection() throws IOException {
        gladiator.setDirection(Gladiator.Direction.LEFT);
        when(swordAttack.isAttacking(gladiator)).thenReturn(false);
        
        viewer.draw(gladiator, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/gladiator_left.png", gladiator.getPosition());
    }

    @Test
    void testDrawWithRightDirection() throws IOException {
        gladiator.setDirection(Gladiator.Direction.RIGHT);
        when(swordAttack.isAttacking(gladiator)).thenReturn(false);
        
        viewer.draw(gladiator, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/gladiator_right.png", gladiator.getPosition());
    }

    @Test
    void testDrawWithAttackDown() throws IOException {
        gladiator.setDirection(Gladiator.Direction.DOWN);
        when(swordAttack.isAttacking(gladiator)).thenReturn(true);
        
        viewer.draw(gladiator, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/gladiator_attack.png", gladiator.getPosition());
    }

    @Test
    void testDrawWithAttackUp() throws IOException {
        gladiator.setDirection(Gladiator.Direction.UP);
        when(swordAttack.isAttacking(gladiator)).thenReturn(true);
        
        viewer.draw(gladiator, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/gladiator_up_attack.png", gladiator.getPosition());
    }

    @Test
    void testDrawWithAttackLeft() throws IOException {
        gladiator.setDirection(Gladiator.Direction.LEFT);
        when(swordAttack.isAttacking(gladiator)).thenReturn(true);
        
        viewer.draw(gladiator, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/gladiator_left_attack.png", gladiator.getPosition());
    }

    @Test
    void testDrawWithAttackRight() throws IOException {
        gladiator.setDirection(Gladiator.Direction.RIGHT);
        when(swordAttack.isAttacking(gladiator)).thenReturn(true);
        
        viewer.draw(gladiator, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/gladiator_right_attack.png", gladiator.getPosition());
    }

    @Test
    void testDrawWithNullSwordAttack() throws Exception {
        Field instanceField = Gladiator.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
        
        gladiator = Gladiator.getInstance(100, 100, 20, 20, 100, 5);
        gladiator.setSwordAttack(null);
        gladiator.setDirection(Gladiator.Direction.DOWN);
        
        viewer.draw(gladiator, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/gladiator.png", gladiator.getPosition());
    }

    @Test
    void testDrawWithIOException() throws IOException {
        gladiator.setDirection(Gladiator.Direction.DOWN);
        when(swordAttack.isAttacking(gladiator)).thenReturn(false);
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any(Position.class));
        
        assertThrows(IOException.class, () -> viewer.draw(gladiator, gui));
    }
}

