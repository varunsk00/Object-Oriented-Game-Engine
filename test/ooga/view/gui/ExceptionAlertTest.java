package ooga.view.gui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import ooga.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * ExceptionAlert Test to ensure exceptions are being handled correctly.
 * Note: Must make starter game (as of this commit, Flappy Bird) have a
 * bad data file so the alert can be generated
 * @author Alex Oesterling, axo
 */
class ExceptionAlertTest extends DukeApplicationTest {
  Button playButton;
  Button selectButton;

  @BeforeEach
  void setUp() throws Exception{
    launch(Main.class);
    playButton = (Button) lookup("#play").queryLabeled();
  }

  @Test
  void testAlert() throws InterruptedException {
    clickOn(playButton);
    selectButton = (Button) lookup("#selectGameButton").queryLabeled();
    clickOn(selectButton);
    Button alertButton = lookup("OK").queryButton();
    clickOn(alertButton);
  }
}