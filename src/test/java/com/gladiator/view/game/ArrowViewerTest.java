package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.attack.projectile.Arrow;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.movement.WanderMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArrowViewerTest {
    private ArrowViewer viewer;
    private Arrow arrow;
    private GUI gui;

    @BeforeEach
    void setUp() {
        viewer = new ArrowViewer();
        gui = mock(GUI.class);
        
        LightZombie target = new LightZombie(200, 200, 
            new WanderMovement(),
            new SwordAttack(8, 30, new ArrayList<>()));
        
        arrow = new Arrow(new Position(100, 100), 5, 10, 200, target);
    }

    @Test
    void testDraw() throws IOException {
        viewer.draw(arrow, gui);
        
        verify(gui).drawSprite("sprites/projectile/arrow.png", arrow.getPosition());
    }

    @Test
    void testDrawWithDifferentPosition() throws IOException {
        arrow.setPosition(new Position(150, 150));
        viewer.draw(arrow, gui);
        
        verify(gui).drawSprite("sprites/projectile/arrow.png", new Position(150, 150));
    }

    @Test
    void testDrawMultipleTimes() throws IOException {
        viewer.draw(arrow, gui);
        arrow.setPosition(new Position(120, 120));
        viewer.draw(arrow, gui);
        
        verify(gui, times(2)).drawSprite(eq("sprites/projectile/arrow.png"), any(Position.class));
    }

    @Test
    void testDrawWithIOException() throws IOException {
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any(Position.class));
        
        assertThrows(IOException.class, () -> viewer.draw(arrow, gui));
    }
}

