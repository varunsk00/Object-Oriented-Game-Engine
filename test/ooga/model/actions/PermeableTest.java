package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import ooga.model.CollisionEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PermeableTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;
  private CollisionEngine myEngine;


  @BeforeEach
  void setUp() {
    param = "true";
    myAction = new SetPermeable(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity2", null);
    myEngine = new CollisionEngine();

  }

  @Test
  void testNonPermeable() {
    EntityWrapper mySecondEntity = new EntityWrapper("unittest.UnitTestEntity2", null);
    mySecondEntity.getModel().setX(mySecondEntity.getModel().getX()+10);
    double oldX = mySecondEntity.getModel().getX();
    myEngine.produceCollisionActions(mySecondEntity.getModel(), myEntity.getModel());
    assertTrue(oldX != mySecondEntity.getModel().getX());
  }

  @Test
  void testPermeable() {
    myAction.execute(myEntity.getModel());
    EntityWrapper mySecondEntity = new EntityWrapper("unittest.UnitTestEntity2", null);
    mySecondEntity.getModel().setX(mySecondEntity.getModel().getX()+10);
    double oldX = mySecondEntity.getModel().getX();
    myEngine.produceCollisionActions(mySecondEntity.getModel(), myEntity.getModel());
    assertTrue(oldX == mySecondEntity.getModel().getX());
  }
}