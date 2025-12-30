package com.gladiator.model.entity;

import com.gladiator.model.component.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    private Tree tree;

    @BeforeEach
    void setUp() {
        tree = new Tree(100, 100);
    }

    @Test
    void testTreeCreation() {
        assertNotNull(tree);
        assertEquals(100, tree.getPosition().getX());
        assertEquals(100, tree.getPosition().getY());
    }

    @Test
    void testTreeHitbox() {
        assertNotNull(tree.getHitbox());
        assertEquals(32, tree.getHitbox().width);
        assertEquals(32, tree.getHitbox().height);
    }

    @Test
    void testTreeWithDifferentPositions() {
        Tree tree1 = new Tree(0, 0);
        Tree tree2 = new Tree(200, 300);
        
        assertEquals(0, tree1.getPosition().getX());
        assertEquals(0, tree1.getPosition().getY());
        assertEquals(200, tree2.getPosition().getX());
        assertEquals(300, tree2.getPosition().getY());
    }
}

