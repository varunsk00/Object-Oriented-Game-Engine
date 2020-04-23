package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplexActionTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "unittest.SampleComplexAction";
    myAction = new ComplexAction(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
  }

  @Test
  void testExecute() {
    myAction.execute(myEntity.getModel());
    myEntity.getModel().update(0);
    assertEquals(myEntity.getModel().getX(), 10);
    assertEquals(myEntity.getModel().getXVelocity(), 10);
  }
}