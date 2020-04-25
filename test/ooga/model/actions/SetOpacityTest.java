package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import ooga.model.CollisionEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetOpacityTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "10";
    myAction = new SetOpacity(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);

  }

  @Test
  void testExecute() {
    double opacity = myEntity.getRender().getOpacity();
    assertTrue(myEntity.getRender().getOpacity() == opacity);

    myAction.execute(myEntity.getModel());
    assertTrue(myEntity.getRender().getOpacity() == Double.parseDouble(param));
  }
}