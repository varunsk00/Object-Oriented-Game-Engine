package ooga.view.gui.userinterface;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import ooga.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GUIFeatures extends DukeApplicationTest {
    private Button playButton;
    private Button selectButton;

    @BeforeEach
    void launchGame() throws Exception{
        launch(Main.class);
        playButton = (Button) lookup("#play").queryLabeled();
        assertTrue(playButton != null);
        clickOn(playButton);
        press(KeyCode.A);
        selectButton = (Button) lookup("#selectGameButton").queryLabeled();
        assertTrue(selectButton!=null);
        clickOn(selectButton);
        Node p1 = lookup("#player1").query();
        assertTrue(p1!= null);
        clickOn(p1);
    }

    @Test
    void testGameOver() {
        for(int i = 0; i < 15; i++){
            press(KeyCode.D);
        }
    }

    @Test
    void testSuspendPointPauseMenu() throws InterruptedException {
        press(KeyCode.ESCAPE);
        Node home = lookup("#gameselect").queryLabeled();
        assertTrue(home!=null);
        clickOn(home);
        selectButton = (Button) lookup("#selectGameButton").queryLabeled();
        assertTrue(selectButton!=null);
        clickOn(selectButton);
        Node play = lookup("#playgame").queryLabeled();
        assertTrue(play!=null);
        clickOn(play);
        Thread.sleep(1000);
    }

    @Test
    void testResetSystem() {
        press(KeyCode.ESCAPE);
        Node reset = lookup("#rebootsystem").queryLabeled();
        assertTrue(reset!=null);
        clickOn(reset);
    }
}
