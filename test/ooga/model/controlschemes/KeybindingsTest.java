package ooga.model.controlschemes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import ooga.model.actions.VelocityX;
import ooga.model.actions.VelocityY;
import ooga.util.ActionBundle;
import org.junit.jupiter.api.Test;

class KeybindingsTest {
  private List<ActionBundle> actionBundleList;

  protected void setUp(){
    actionBundleList = new ArrayList<>();
    ActionBundle a = new ActionBundle();
    a.setId("W");
    a.addAction(new VelocityY("100.0"));
    ActionBundle b = new ActionBundle();
    b.setId("A");
    b.addAction(new VelocityX("-100.0"));
    ActionBundle c = new ActionBundle();
    c.setId("S");
    c.addAction(new VelocityY("-100.0"));
    ActionBundle d = new ActionBundle();
    d.setId("D");
    d.addAction(new VelocityX("100.0"));
    actionBundleList.add(a);
    actionBundleList.add(b);
    actionBundleList.add(c);
    actionBundleList.add(d);
  }

  @Test
  void getCurrentAction() {
    ControlScheme scheme = new Keybindings(actionBundleList);
    scheme.handleKeyInput(new KeyEvent(new Button(), 1, 20, 1, 10, 'a', 1));
    assertTrue(scheme.getCurrentAction() = );
  }

  @Test
  void handleKeyInput() {
  }

  @Test
  void handleKeyReleased() {
  }
}