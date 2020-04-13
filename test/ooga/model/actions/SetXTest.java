package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetXTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "10";
    myAction = new SetX(param);
    myEntity = new EntityWrapper("UnitTestEntity", null);
  }

  @Test
  void testExecute() {
    myAction.execute(myEntity.getModel());
    assertEquals(Double.parseDouble(param), myEntity.getModel().getX());
  }
}