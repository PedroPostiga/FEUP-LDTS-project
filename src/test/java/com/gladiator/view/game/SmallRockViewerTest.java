package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.entity.SmallRock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SmallRockViewerTest {
    private SmallRockViewer viewer;
    private SmallRock smallRock;
    private GUI gui;

    @BeforeEach
    void setUp() {
        viewer = new SmallRockViewer();
        gui = mock(GUI.class);
        smallRock = new SmallRock(100, 100);
    }

    @Test
    void testDraw() throws IOException {
        viewer.draw(smallRock, gui);
        
        verify(gui).drawSprite("sprites/obstacle/small_rock.png", smallRock.getPosition());
    }

    @Test
    void testDrawWithDifferentPosition() throws IOException {
        SmallRock differentRock = new SmallRock(150, 150);
        viewer.draw(differentRock, gui);
        
        verify(gui).drawSprite("sprites/obstacle/small_rock.png", differentRock.getPosition());
    }

    @Test
    void testDrawWithIOException() throws IOException {
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any());
        
        assertThrows(IOException.class, () -> viewer.draw(smallRock, gui));
    }
}

