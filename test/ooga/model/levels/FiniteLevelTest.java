
package ooga.model.levels;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.controller.ViewManager;
import ooga.util.GameParser;
import ooga.util.LevelParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class FiniteLevelTest {
  private FiniteLevel testLevel;
  private List<EntityWrapper> testEntityList;
  private List<EntityWrapper> testPlayers;
  private List<EntityWrapper> testTiles;
  private List<EntityWrapper> testEnemies;


  @BeforeEach
  void setUp(){
    String gameName = "unittest";
    testEntityList = new ArrayList<>();
    GameParser testGameParser = new GameParser(gameName, null, false);
    LevelParser testLevelParser = new LevelParser(gameName + ".testLevel1",null);

    testPlayers = testGameParser.getPlayerList();
    testTiles = testLevelParser.parseTileEntities();
    testEnemies = testLevelParser.parseEnemyEntities();
    testLevel = new FiniteLevel(testTiles, testPlayers, testEnemies, testGameParser.parseGameStatusProfile(), gameName);
    testEntityList.addAll(testPlayers);
  }

  @Test
  void testSpawnEntitiesOne() {

    double xPosition = 250;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList, new ArrayList<>());
    assertTrue(testEntityList.size() - testPlayers.size() == 1);
  }

  @Test
  void testSpawnEntitiesTwo() {

    double xPosition = 5000;
    double yPosition = 5000;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList, new ArrayList<>());
    assertTrue(testEntityList.size() - testPlayers.size() == 0);
  }

  @Test
  void testSpawnEntitiesThree() {
    double xPosition = 50000;
    double yPosition = 600;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList, new ArrayList<>());
    assertTrue(testEntityList.size() - testPlayers.size() == 2);
  }

  void setPlayersLocation(double xPosition, double yPosition){
    for(EntityWrapper player : testPlayers){
      player.getModel().setBoundedBelow(true);
      player.getModel().setX(xPosition);
      player.getModel().setY(yPosition);
    }
  }
}
