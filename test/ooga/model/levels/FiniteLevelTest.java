
package ooga.model.levels;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import javax.swing.text.html.parser.Entity;
import ooga.Main;
import ooga.controller.EntityWrapper;
import ooga.controller.GameController;
import ooga.controller.ViewManager;
import ooga.model.CollisionEngine;
import ooga.util.GameParser;
import ooga.util.LevelParser;
import ooga.view.gui.managers.AudioVideoManager;
import ooga.view.gui.managers.StageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class FiniteLevelTest extends DukeApplicationTest{
  private FiniteLevel testLevel;
  private ViewManager testManager;
  private List<EntityWrapper> testEntityList;
  private List<EntityWrapper> testPlayers;
  private List<EntityWrapper> testTiles;
  private List<EntityWrapper> testEnemies;


  @BeforeEach
  void setUp() throws Exception {
//    launch(Main.class);
    String gameName = "unittest";
    testEntityList = new ArrayList<>();
    GameParser testGameParser = new GameParser(gameName, null, false);
    LevelParser testLevelParser = new LevelParser(gameName + ".testLevel1",null);

    testPlayers = testGameParser.getPlayerList();
    testTiles = testLevelParser.parseTileEntities();
    testEnemies = testLevelParser.parseEnemyEntities();
    //StageManager stageManager = new StageManager(null, null);
    //testManager = new ViewManager(stageManager, testPlayers);
    testLevel = new FiniteLevel(testTiles, testPlayers, testEnemies, testGameParser.parseGameStatusProfile(), gameName);
    testEntityList.addAll(testPlayers);
  }

  @Test
  void testSpawnEntitiesOne() {
    assertTrue(testEntityList.size() - testPlayers.size() == 0);
    double xPosition = 250;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() - testPlayers.size() == 1);
  }

  @Test
  void testSpawnEntitiesNone() {
    assertTrue(testEntityList.size() - testPlayers.size() == 0);
    double xPosition = 5000;
    double yPosition = 5000;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() - testPlayers.size() == 0);
  }

  void setPlayersLocation(double xPosition, double yPosition){
    for(EntityWrapper player : testPlayers){
      player.getModel().setBoundedBelow(true);
      player.getModel().setX(xPosition);
      player.getModel().setY(yPosition);
    }
  }
}
