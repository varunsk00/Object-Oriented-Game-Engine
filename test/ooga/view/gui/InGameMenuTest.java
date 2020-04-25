package ooga.view.gui;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import ooga.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class InGameMenuTest extends DukeApplicationTest {
    private Button playButton;
    private Button selectButton;
    private Button player1;

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
    void testPause() throws InterruptedException {
        press(KeyCode.ESCAPE);
        Thread.sleep(2000);
    }

    @Test
    void testResume() throws InterruptedException {
        press(KeyCode.ESCAPE);
        Thread.sleep(1000);
        press(KeyCode.Q);
        Thread.sleep(1000);
    }

    @Test
    void testEscapeCounter() throws InterruptedException {
        for(int i=0; i< 15; i++){
            press(KeyCode.ESCAPE);
            press(KeyCode.Q);
        }
    }

    @Test
    void testMenuCameraTranslate() throws InterruptedException {
        for(int i = 0; i < 15; i++){
            press(KeyCode.D);
        }
        press(KeyCode.SPACE);
        Thread.sleep(4000);
        press(KeyCode.ESCAPE);
    }

}