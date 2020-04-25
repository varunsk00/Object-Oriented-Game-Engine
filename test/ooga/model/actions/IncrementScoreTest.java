package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import ooga.controller.EntityWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncrementScoreTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;

  @BeforeEach
  void setUp() {
    param = "1";
    myAction = new IncrementScore(param);
    myEntity = new EntityWrapper("unittest.UnitTestEntity", null);
  }

  @Test
  void execute() {
    double oldscore = myEntity.getModel().getScore();
    myAction.execute(myEntity.getModel());
    assertEquals(myEntity.getModel().getScore(), oldscore + Double.parseDouble(param));
  }
}