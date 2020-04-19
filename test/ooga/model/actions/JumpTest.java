package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class JumpTest extends DukeApplicationTest {

  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "100";
    myAction = new Jump(param);
    myEntity = new EntityWrapper("UnitTestEntity", null);
    myEntity.getModel().setOnGround(true);
  }

  @Test
  void testExecute() {
    double preVelocity = myEntity.getModel().getYVelocity();
    myAction.execute(myEntity.getModel());
    System.out.println(preVelocity);
    System.out.println(myEntity.getModel().getYVelocity());
    assertTrue(preVelocity != myEntity.getModel().getYVelocity());
    assertTrue(myEntity.getModel().getYVelocity() == Double.parseDouble(param));
    assertTrue(!myEntity.getModel().isOnGround());
  }

  @Test
  void testExecuteTwice(){
    double preVelocity = myEntity.getModel().getYVelocity();
    myAction.execute(myEntity.getModel());
    assertTrue(preVelocity != myEntity.getModel().getYVelocity());
    assertTrue(myEntity.getModel().getYVelocity() == Double.parseDouble(param));
    assertTrue(!myEntity.getModel().isOnGround());

    preVelocity = myEntity.getModel().getYVelocity();
    myAction.execute(myEntity.getModel());
    assertTrue(preVelocity == myEntity.getModel().getYVelocity());
    assertTrue(!myEntity.getModel().isOnGround());
  }

  @Test
  void testExecuteResetExecute(){
    double preVelocity = myEntity.getModel().getYVelocity();
    myAction.execute(myEntity.getModel());
    assertTrue(preVelocity != myEntity.getModel().getYVelocity());
    assertTrue(myEntity.getModel().getYVelocity() == Double.parseDouble(param));
    assertTrue(!myEntity.getModel().isOnGround());

    myEntity.getModel().setOnGround(true);
    myEntity.getModel().setYVelocity(0);
    assertTrue(myEntity.getModel().isOnGround());

    preVelocity = myEntity.getModel().getYVelocity();
    myAction.execute(myEntity.getModel());
    assertTrue(preVelocity != myEntity.getModel().getYVelocity());
    assertTrue(myEntity.getModel().getYVelocity() == Double.parseDouble(param));
    assertTrue(!myEntity.getModel().isOnGround());
  }

}