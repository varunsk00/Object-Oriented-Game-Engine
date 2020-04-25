package ooga.model.controlschemes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.Action;
import ooga.model.actions.VelocityX;
import ooga.model.actions.VelocityY;
import ooga.util.ActionBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GamePadTest {

  private List<ActionBundle> actionBundleList;
  private ControlScheme scheme;

  @BeforeEach
  void setUp() {

    actionBundleList = new ArrayList<>();
    ActionBundle a = new ActionBundle();
    a.setId("THUMBSTICK_UP");
    a.addAction(new VelocityY("100.0"));
    actionBundleList.add(a);
    scheme = new GamePad(actionBundleList);
  }

  @Test
  void testCurrentActionAndHandleKeyInput() {
    scheme.handleKeyInput("THUMBSTICK_UP");
    for (int i = 0; i < scheme.getCurrentAction().size(); i++) {
      assertTrue(scheme.getCurrentAction().get(i).equals(actionBundleList.get(0).getActions().get(i)));
    }
  }

  @Test
  void testKeyReleased() {
    scheme.handleKeyInput("THUMBSTICK_UP");
    for (int i = 0; i < scheme.getCurrentAction().size(); i++) {
      assertTrue(scheme.getCurrentAction().get(i).equals(actionBundleList.get(0).getActions().get(i)));
    }
    scheme.handleKeyReleased("THUMBSTICK_UP");
    assertTrue(scheme.getCurrentAction().size() == 0);
  }

}