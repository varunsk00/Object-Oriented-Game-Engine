package ooga.view.gui;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import ooga.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class InGameMenuTest extends DukeApplicationTest {
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
    void testPauseandResume() {
        press(KeyCode.ESCAPE);
        Node resume = lookup("#playgame").queryLabeled();
        assertTrue(resume!=null);
        clickOn(resume);
        press(KeyCode.ESCAPE);
        press(KeyCode.Q);
    }

    @Test
    void testMenuCameraTranslate() {
        for(int i = 0; i < 15; i++){
            press(KeyCode.D);
        }
        press(KeyCode.SPACE);
        press(KeyCode.ESCAPE);
    }

    @Test
    void testRestartfromMenu() {
        for(int i = 0; i < 15; i++){
            press(KeyCode.D);
        }
        press(KeyCode.Q);
    }

    @Test
    void testConfigMenu() {
        press(KeyCode.ESCAPE);
        Node configMenu = lookup("#configuration").queryLabeled();
        assertTrue(configMenu!=null);
        clickOn(configMenu);
    }

}