package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccelerateXTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "10";
    myAction = new AccelerateX(param);
    myEntity = new EntityWrapper("UnitTestEntity", null);
  }

  @Test
  void testExecute() {
    double xVelinit = myEntity.getModel().getXVelocity();
    double xVelFinal = xVelinit + Double.parseDouble(param);
    myAction.execute(myEntity.getModel());
    assertTrue(xVelFinal == myEntity.getModel().getXVelocity());
  }
}