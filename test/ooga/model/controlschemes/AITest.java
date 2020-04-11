package ooga.model.controlschemes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import ooga.model.actions.Action;
import ooga.model.actions.VelocityX;
import ooga.model.actions.VelocityY;
import ooga.util.ActionBundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AITest {
  private List<ActionBundle> actionBundleList;
  private ControlScheme scheme;

  @BeforeEach
  void setUp() {
    actionBundleList =new ArrayList<>();
    ActionBundle a = new ActionBundle();
    a.setId("10");
    a.addAction(new VelocityY("100.0"));
    ActionBundle b = new ActionBundle();
    b.setId("10");
    b.addAction(new VelocityX("-100.0"));
    ActionBundle c = new ActionBundle();
    c.setId("10");
    c.addAction(new VelocityY("-100.0"));
    ActionBundle d = new ActionBundle();
    d.setId("10");
    d.addAction(new

        VelocityX("100.0"));
    actionBundleList.add(a);
    actionBundleList.add(b);
    actionBundleList.add(c);
    actionBundleList.add(d);
    scheme = new AI(actionBundleList);
  }

  @Test
  void getCurrentAction() {
    List<Action> randomActions = scheme.getCurrentAction();
    boolean out = false;
    for(int i = 0; i < actionBundleList.size(); i++){
      if(actionBundleList.get(i).getActions().equals(randomActions)){
        out = true;
      }
      System.out.println(actionBundleList.get(i).getActions());
      System.out.println(randomActions);
    }

    assertTrue(out);
  }
}