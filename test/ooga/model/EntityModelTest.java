package ooga.model;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import ooga.model.actions.SetX;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntityModelTest {
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);

  }

  @Test
  void testLimitSpeed(){
    double xVelMax = myEntity.getParser().readXVelMax();
    myEntity.getModel().setXVelocity(xVelMax + 100);
    myEntity.getModel().update(1000);
    assertEquals(xVelMax, myEntity.getModel().getXVelocity());
  }

  @Test
  void testActionStack(){
    double newX = 1000;
    myEntity.getModel().getActionStack().push(new SetX("" + newX));
    myEntity.getModel().update(1000);
    assertEquals(newX, myEntity.getModel().getX());
  }

  @Test
  void testSetWidthAndHeight(){
    double newDimension = 10;
    myEntity.getModel().setWidth(newDimension);
    myEntity.getModel().setHeight(newDimension);
    assertEquals(newDimension, myEntity.getModel().getWidth());
    assertEquals(newDimension, myEntity.getModel().getHeight());
  }
}