package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetGroundStatusTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "false";
    myAction = new SetGroundStatus(param);
    myEntity = new EntityWrapper("UnitTestEntity", null);
  }

  @Test
  void testExecute() {
    myEntity.getModel().setBoundedBelow(true);
    assertTrue(myEntity.getModel().getBoundedBelow());

    myAction.execute(myEntity.getModel());
    assertTrue(!myEntity.getModel().getBoundedBelow());
  }
}