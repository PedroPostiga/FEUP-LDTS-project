package com.gladiator.view.game;

import com.gladiator.gui.GUI;
import com.gladiator.model.component.Position;
import com.gladiator.model.enemy.enemy_types.FatZombie;
import com.gladiator.model.enemy.enemy_types.LightZombie;
import com.gladiator.model.enemy.enemy_types.Vampire;
import com.gladiator.model.gladiator.Gladiator;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.mockito.Mockito.*;

public class EntityViewerTest {

    @Test
    public void testGladiatorViewerDraw() throws IOException {
        GUI gui = mock(GUI.class);
        GladiatorViewer viewer = new GladiatorViewer();
        Gladiator gladiator = mock(Gladiator.class);
        Position position = new Position(100, 100);

        when(gladiator.getPosition()).thenReturn(position);
        when(gladiator.getDirection()).thenReturn(Gladiator.Direction.DOWN);
        when(gladiator.getSwordAttack()).thenReturn(null);

        viewer.draw(gladiator, gui);

        verify(gui).drawSprite(anyString(), eq(position));
    }

    @Test
    public void testFatZombieViewerDraw() throws IOException {
        GUI gui = mock(GUI.class);
        FatZombieViewer viewer = new FatZombieViewer();
        FatZombie fatZombie = mock(FatZombie.class);
        Position position = new Position(50, 50);

        when(fatZombie.getPosition()).thenReturn(position);

        viewer.draw(fatZombie, gui);

        verify(gui).drawSprite(anyString(), eq(position));
    }

    @Test
    public void testLightZombieViewerDraw() throws IOException {
        GUI gui = mock(GUI.class);
        LightZombieViewer viewer = new LightZombieViewer();
        LightZombie lightZombie = mock(LightZombie.class);
        Position position = new Position(75, 75);

        when(lightZombie.getPosition()).thenReturn(position);

        viewer.draw(lightZombie, gui);

        verify(gui).drawSprite(anyString(), eq(position));
    }

    @Test
    public void testVampireViewerDraw() throws IOException {
        GUI gui = mock(GUI.class);
        VampireViewer viewer = new VampireViewer();
        Vampire vampire = mock(Vampire.class);
        Position position = new Position(25, 25);

        when(vampire.getPosition()).thenReturn(position);

        viewer.draw(vampire, gui);

        verify(gui).drawSprite(anyString(), eq(position));
    }

    @Test
    public void testGladiatorViewerWithDifferentDirections() throws IOException {
        GUI gui = mock(GUI.class);
        GladiatorViewer viewer = new GladiatorViewer();
        Gladiator gladiator = mock(Gladiator.class);
        Position position = new Position(100, 100);

        when(gladiator.getPosition()).thenReturn(position);
        when(gladiator.getSwordAttack()).thenReturn(null);

        when(gladiator.getDirection()).thenReturn(Gladiator.Direction.LEFT);
        viewer.draw(gladiator, gui);
        verify(gui).drawSprite(contains("left"), eq(position));

        when(gladiator.getDirection()).thenReturn(Gladiator.Direction.RIGHT);
        viewer.draw(gladiator, gui);
        verify(gui).drawSprite(contains("right"), eq(position));

        when(gladiator.getDirection()).thenReturn(Gladiator.Direction.UP);
        viewer.draw(gladiator, gui);
        verify(gui).drawSprite(contains("up"), eq(position));
    }
}
