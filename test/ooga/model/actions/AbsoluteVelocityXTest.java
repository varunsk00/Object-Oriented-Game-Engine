package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbsoluteVelocityXTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "10";
    myAction = new AbsoluteVelocityX(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
  }
  @Test
  void testExecuteForwards() {
    myAction.execute(myEntity.getModel());
    assertEquals(Double.parseDouble(param), myEntity.getModel().getXVelocity());  }
  @Test
  void testExecuteBackwards() {
    myEntity.getModel().setForwards(false);
    myAction.execute(myEntity.getModel());
    assertEquals(Double.parseDouble(param), -1*myEntity.getModel().getXVelocity());
  }
}