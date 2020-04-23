package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccelerateYTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "10";
    myAction = new AccelerateY(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
  }

  @Test
  void testExecute() {
    double yVelInit = myEntity.getModel().getYVelocity();
    double yVelFinal = yVelInit + Double.parseDouble(param);
    myAction.execute(myEntity.getModel());
    assertTrue(yVelFinal == myEntity.getModel().getYVelocity());
  }
}