package ooga;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class GuiTest extends DukeApplicationTest {
  private Button playButton;
  private Button selectButton;

  @BeforeEach
  void setUp() throws Exception{
    launch(Main.class);
    playButton = (Button) lookup("#Play").queryLabeled();
  }

  @Test
  void testSelectGame() {
    assertTrue(playButton != null);
    clickOn(playButton);
    selectButton = (Button) lookup("#Select").queryLabeled();
    assertTrue(selectButton != null);
    clickOn(selectButton);
  }

  @Test
  void testPlayGame() throws InterruptedException {
    clickOn(playButton);
    selectButton = (Button) lookup("#Select").queryLabeled();
    clickOn(selectButton);
    press(KeyCode.SPACE);
    press(KeyCode.D);
  }

  @Test
  void testMenuArrows() throws InterruptedException {
    clickOn(playButton);
    Node leftArrow = lookup("#LeftArrow").query();
    Node rightArrow = lookup("#RightArrow").query();
    assertTrue(rightArrow != null);
    assertTrue(leftArrow != null);
    for(int i = 0; i < 10; i++){
      clickOn(leftArrow);
      Thread.sleep(800);
    }
    for(int i = 0; i < 10; i++){
      clickOn(rightArrow);
      Thread.sleep(800);
    }
    selectButton = (Button) lookup("#Select").queryLabeled();
    clickOn(selectButton);
  }

  @Test
  void testHome(){
    clickOn(playButton);
    Node leftArrow = lookup("#LeftArrow").query();
    Node rightArrow = lookup("#RightArrow").query();
    selectButton = (Button) lookup("#Select").queryLabeled();
    assertTrue(rightArrow != null);
    assertTrue(leftArrow != null);
    clickOn(selectButton);
    press(KeyCode.H);
    assertTrue(rightArrow != null);
    assertTrue(leftArrow != null);
    assertTrue(selectButton != null);

  }
}