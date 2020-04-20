package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetXDirectionTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "forwards";
    myAction = new SetXDirection(param);
    myEntity = new EntityWrapper("UnitTestEntity", null);
  }

  @Test
  void testExecute() {
    myEntity.getModel().setForwards(false);
    boolean forwards = myEntity.getModel().getForwards();
    myAction.execute(myEntity.getModel());
    assertEquals(!forwards, myEntity.getModel().getForwards());
  }
}