package ooga.view;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import ooga.view.entity.EntityView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntityViewTest {
  private EntityWrapper myEntity;
  private EntityView myView;

  @BeforeEach
  void setUp() {
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
    myView = new EntityView(myEntity);
  }


  @Test
  void testUpdate() {
    double newX = 1000;
    double newY = 1000;
    myView.update(newX, newY, true);
    assertEquals(myView.getRender().getBoundsInParent().getMinX(), newX);
    assertEquals(myView.getRender().getBoundsInParent().getMinY(), newY);
  }
}