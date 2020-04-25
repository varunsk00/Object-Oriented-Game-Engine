package ooga.model.actions;

import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.EntityWrapper;
import ooga.view.gui.managers.StageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * @Deprecated -- cant work with our current class structure.
 */
class SpawnTest extends DukeApplicationTest {
  private String param;
  private Action myAction;
  private EntityWrapper myEntity;
  private Controller myController;

//  @BeforeEach
//  void setUp() {
//    param = "UnitTestEntity";
//    myAction = new Spawn(param);
//    myController = new MainController(new StageManager(new Stage()));
//    myEntity = new EntityWrapper("UnitTestEntity", myController);
//  }
//
//  @Test
//  void testExecute() {
//    int entitySize = myController.getEntityList().size();
//    myAction.execute(myEntity.getModel());
//    assertEquals(entitySize+1, myController.getEntityList().size());
//  }
}