package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class InfiniteJumpTest extends DukeApplicationTest {

  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "10";
    myAction = new InfiniteJump(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
  }

  @Test
  void testExecute() {
    double preVelocity = myEntity.getModel().getYVelocity();
    myAction.execute(myEntity.getModel());
    assertTrue(preVelocity != myEntity.getModel().getYVelocity());
    assertTrue(myEntity.getModel().getYVelocity() == Double.parseDouble(param));
    assertTrue(!myEntity.getModel().getBoundedBelow());
  }

  @Test
  void testExecuteTwice(){
    double preVelocity = myEntity.getModel().getYVelocity();
    myAction.execute(myEntity.getModel());
    assertTrue(preVelocity != myEntity.getModel().getYVelocity());
    assertTrue(myEntity.getModel().getYVelocity() == Double.parseDouble(param));
    assertTrue(!myEntity.getModel().getBoundedBelow());

    preVelocity = myEntity.getModel().getYVelocity();
    myAction.execute(myEntity.getModel());
    assertTrue(preVelocity == myEntity.getModel().getYVelocity());
    assertTrue(!myEntity.getModel().getBoundedBelow());
  }
}