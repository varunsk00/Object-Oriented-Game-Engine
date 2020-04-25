package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExecuteConditionalTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "none";
    myAction = new ExecuteConditional(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
  }

  @Test
  void testExecuteConditionalFalse() {
    double oldX = myEntity.getModel().getX();
    myEntity.getModel().getActionStack().push(new MoveX("10"));
    myEntity.getModel().setConditional(false);
    myAction.execute(myEntity.getModel());
    assertEquals(oldX, myEntity.getModel().getX());
  }

  @Test
  void testExecuteConditionalTrue() {
    double oldX = myEntity.getModel().getX();
    myEntity.getModel().getActionStack().push(new MoveX("10"));
    myEntity.getModel().setConditional(true);
    myAction.execute(myEntity.getModel());
    assertEquals(oldX + 10, myEntity.getModel().getX());
  }
}