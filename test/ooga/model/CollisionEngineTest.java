package ooga.model;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollisionEngineTest {
  private EntityWrapper myEntity1;
  private EntityWrapper myEntity2;
  private CollisionEngine myEngine;

  @BeforeEach
  void setUp() {
    myEntity1 = new EntityWrapper("UnitTestEntity", null);
    myEntity2 = new EntityWrapper("UnitTestEntity", null);
    myEngine = new CollisionEngine();
  }

  @Test
  void testProduceCollisionActions() {
    myEntity1.getModel().setY(myEntity1.getModel().getY()+20);
    double e2Y = myEntity2.getModel().getY();
    myEngine.produceCollisionActions(myEntity2.getModel(), myEntity1.getModel());

    assertTrue(e2Y == myEntity2.getModel().getY()+10);
  }
}