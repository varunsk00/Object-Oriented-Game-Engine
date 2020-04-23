package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveXTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "10";
    myAction = new MoveX(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
  }

  @Test
  void testExecute() {
    double prevX = myEntity.getModel().getX();
    myAction.execute(myEntity.getModel());
    assertEquals(prevX + Double.parseDouble(param), myEntity.getModel().getX());
  }

  @Test
  void testExecuteTwice(){
    testExecute();
    testExecute();
  }
}