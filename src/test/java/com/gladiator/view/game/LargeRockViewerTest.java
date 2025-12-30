package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.entity.LargeRock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LargeRockViewerTest {
    private LargeRockViewer viewer;
    private LargeRock largeRock;
    private GUI gui;

    @BeforeEach
    void setUp() {
        viewer = new LargeRockViewer();
        gui = mock(GUI.class);
        largeRock = new LargeRock(100, 100);
    }

    @Test
    void testDraw() throws IOException {
        viewer.draw(largeRock, gui);
        
        verify(gui).drawSprite("sprites/obstacle/large_rock.png", largeRock.getPosition());
    }

    @Test
    void testDrawWithDifferentPosition() throws IOException {
        LargeRock differentRock = new LargeRock(150, 150);
        viewer.draw(differentRock, gui);
        
        verify(gui).drawSprite("sprites/obstacle/large_rock.png", differentRock.getPosition());
    }

    @Test
    void testDrawWithIOException() throws IOException {
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any());
        
        assertThrows(IOException.class, () -> viewer.draw(largeRock, gui));
    }
}

