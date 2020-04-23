package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveYTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "10";
    myAction = new MoveY(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
  }

  @Test
  void testExecute() {
    double prevY = myEntity.getModel().getY();
    myAction.execute(myEntity.getModel());
    assertEquals(prevY + Double.parseDouble(param), myEntity.getModel().getY());
  }

  @Test
  void testExecuteTwice(){
    testExecute();
    testExecute();
  }
}