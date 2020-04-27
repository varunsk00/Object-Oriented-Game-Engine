package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoseHealthTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "1";
    myAction = new LoseHealth(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
  }

  @Test
  void execute() {
    double health = myEntity.getModel().getHealth();
    myAction.execute(myEntity.getModel());
    assertEquals(myEntity.getModel().getHealth(), health - Double.parseDouble(param));
  }
}