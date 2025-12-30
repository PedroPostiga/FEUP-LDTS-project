package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.attack.AttackStrategy;
import com.gladiator.model.attack.SwordAttack;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.movement.WanderMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LightZombieViewerTest {
    private LightZombieViewer viewer;
    private LightZombie lightZombie;
    private GUI gui;
    private AttackStrategy attackStrategy;

    @BeforeEach
    void setUp() {
        viewer = new LightZombieViewer();
        gui = mock(GUI.class);
        
        attackStrategy = mock(SwordAttack.class);
        lightZombie = new LightZombie(100, 100, 
            new WanderMovement(),
            new SwordAttack(8, 30, new ArrayList<>()));
        lightZombie.setAttack(attackStrategy);
    }

    @Test
    void testDrawWhenNotAttacking() throws IOException {
        when(attackStrategy.isAttacking(lightZombie)).thenReturn(false);
        
        viewer.draw(lightZombie, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/light_zombie.png", lightZombie.getPosition());
    }

    @Test
    void testDrawWhenAttacking() throws IOException {
        when(attackStrategy.isAttacking(lightZombie)).thenReturn(true);
        
        viewer.draw(lightZombie, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/light_zombie_attack.png", lightZombie.getPosition());
    }

    @Test
    void testDrawWithNullAttackStrategy() throws IOException {
        lightZombie.setAttack(null);
        
        viewer.draw(lightZombie, gui);
        
        verify(gui).drawSprite("sprites/movingEntity/light_zombie.png", lightZombie.getPosition());
    }

    @Test
    void testDrawWithIOException() throws IOException {
        when(attackStrategy.isAttacking(lightZombie)).thenReturn(false);
        doThrow(new IOException("Test exception")).when(gui).drawSprite(anyString(), any());
        
        assertThrows(IOException.class, () -> viewer.draw(lightZombie, gui));
    }

    @Test
    void testDrawMultipleTimes() throws IOException {
        when(attackStrategy.isAttacking(lightZombie)).thenReturn(false);
        
        viewer.draw(lightZombie, gui);
        viewer.draw(lightZombie, gui);
        
        verify(gui, times(2)).drawSprite("sprites/movingEntity/light_zombie.png", lightZombie.getPosition());
    }
}

