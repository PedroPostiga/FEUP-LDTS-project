package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.entity.Tree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TreeViewerTest {
    private TreeViewer viewer;
    private Tree tree;
    private GUI gui;

    @BeforeEach
    void setUp() {
        viewer = new TreeViewer();
        gui = mock(GUI.class);
        tree = new Tree(100, 100);
    }

    @Test
    void testDraw() throws IOException {
        viewer.draw(tree, gui);
        
        verify(gui).drawSprite("sprites/obstacle/tree.png", tree.getPosition());
    }

    @Test
    void testDrawWithDifferentPosition() throws IOException {
        Tree differentTree = new Tree(150, 150);
        viewer.draw(differentTree, gui);
        
        verify(gui).drawSprite("sprites/obstacle/tree.png", differentTree.getPosition());
    }

    @Test
    void testDrawWithIOException() throws IOException {
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any());
        
        assertThrows(IOException.class, () -> viewer.draw(tree, gui));
    }
}

