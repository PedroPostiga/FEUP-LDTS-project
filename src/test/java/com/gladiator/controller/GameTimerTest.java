package com.gladiator.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTimerTest {
    private GameTimer timer;

    @BeforeEach
    void setUp() {
        timer = new GameTimer(60);
    }

    @Test
    void testShouldTickInitially() {
        assertTrue(timer.shouldTick());
    }

    @Test
    void testShouldTickAfterInterval() throws InterruptedException {
        timer.shouldTick();
        
        Thread.sleep(20);
        
        assertTrue(timer.shouldTick());
    }

    @Test
    void testShouldNotTickBeforeInterval() throws InterruptedException {
        timer.shouldTick();
        
        Thread.sleep(5);
        
        assertFalse(timer.shouldTick());
    }

    @Test
    void testReset() throws InterruptedException {
        timer.shouldTick();
        Thread.sleep(10);
        
        timer.reset();
        
        assertTrue(timer.shouldTick());
    }

    @Test
    void testDifferentTickRates() {
        GameTimer fastTimer = new GameTimer(120);
        GameTimer slowTimer = new GameTimer(30);
        
        assertTrue(fastTimer.shouldTick());
        assertTrue(slowTimer.shouldTick());
    }
}

