package ooga.model.controlschemes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import ooga.model.actions.VelocityX;
import ooga.model.actions.VelocityY;
import ooga.util.ActionBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatternTest {
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
    scheme = new Keybindings(actionBundleList);
  }

  @Test
  void getCurrentAction() {
  }
}