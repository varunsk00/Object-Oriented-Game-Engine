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

class KeyboardTest {

  private List<ActionBundle> actionBundleList;
  private ControlScheme scheme;

  @BeforeEach
  void setUp() {

  actionBundleList =new ArrayList<>();
    ActionBundle a = new ActionBundle();
    a.setId("W");
    a.addAction(new

    VelocityY("100.0"));
    ActionBundle b = new ActionBundle();
    b.setId("A");
    b.addAction(new

    VelocityX("-100.0"));
    ActionBundle c = new ActionBundle();
    c.setId("S");
    c.addAction(new

    VelocityY("-100.0"));
    ActionBundle d = new ActionBundle();
    d.setId("D");
    d.addAction(new

    VelocityX("100.0"));
    actionBundleList.add(a);
    actionBundleList.add(b);
    actionBundleList.add(c);
    actionBundleList.add(d);
    scheme = new Keyboard(actionBundleList);
  }

  @Test
  void testCurrentActionAndHandleKeyInput() {
    scheme.handleKeyInput("w");
    for(int i = 0; i < scheme.getCurrentAction().size(); i++){
      assertTrue(scheme.getCurrentAction().get(i).equals(actionBundleList.get(0).getActions().get(i)));
    }
    scheme.handleKeyInput("a");
    for(int i = 0; i < scheme.getCurrentAction().size(); i++){
      assertTrue(scheme.getCurrentAction().get(i).equals(actionBundleList.get(1).getActions().get(i)));
    }
    scheme.handleKeyInput("s");
    for(int i = 0; i < scheme.getCurrentAction().size(); i++){
      assertTrue(scheme.getCurrentAction().get(i).equals(actionBundleList.get(2).getActions().get(i)));
    }
    scheme.handleKeyInput("d");
    for(int i = 0; i < scheme.getCurrentAction().size(); i++){
      assertTrue(scheme.getCurrentAction().get(i).equals(actionBundleList.get(3).getActions().get(i)));
    }
  }

  @Test
  void testKeyReleased() {
    scheme.handleKeyInput("w");
    for(int i = 0; i < scheme.getCurrentAction().size(); i++){
      assertTrue(scheme.getCurrentAction().get(i).equals(actionBundleList.get(0).getActions().get(i)));
    }
    scheme.handleKeyReleased("w");
    assertTrue(scheme.getCurrentAction().size() == 0);
  }

}