package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetBoundedLeftTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "true";
    myAction = new SetBoundedLeft(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
  }

  @Test
  void testExecute() {
    myEntity.getModel().setXVelocity(-100);
    myAction.execute(myEntity.getModel());

    myEntity.update(0);

    assertTrue(myEntity.getModel().getXVelocity() == 0);
  }
}