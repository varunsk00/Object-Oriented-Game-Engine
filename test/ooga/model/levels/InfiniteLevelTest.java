
package ooga.model.levels;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.util.GameParser;
import ooga.util.LevelParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InfiniteLevelTest {
  private Level testLevel;
  private List<EntityWrapper> testEntityList;
  private List<EntityWrapper> testPlayers;
  private List<EntityWrapper> testTiles;
  private List<EntityWrapper> testEnemies;


  @BeforeEach
  void setUp(){
    String gameName = "unittestinfinitelevels";
    testEntityList = new ArrayList<>();
    GameParser testGameParser = new GameParser(gameName, null, false);
    LevelParser testLevelParser = new LevelParser(gameName + ".testInfiniteLevel1",null);

    testPlayers = testGameParser.getPlayerList();
    testTiles = testLevelParser.parseTileEntities();
    testEnemies = testLevelParser.parseEnemyEntities();
    testLevel = new InfiniteLevel(testTiles, testPlayers, testEnemies, testGameParser.parseGameStatusProfile(), gameName);
    testEntityList.addAll(testPlayers);
  }

  @Test
  void testSpawnEntitiesOne() {
    double xPosition = 0;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() - testPlayers.size() == 4);
  }

  @Test
  void testSpawnEntitiesTwo() {
    double xPosition = 600;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() - testPlayers.size() == 4);
  }

  @Test
  void testSpawnEntitiesThree() {
    double xPosition = 1100;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() - testPlayers.size() == 4);
  }

  @Test
  void testSpawnEntitiesFour() {
    double xPosition = 100;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() - testPlayers.size() == 4);
    xPosition = 600;
    yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() - testPlayers.size() == 8);
  }

  void setPlayersLocation(double xPosition, double yPosition){
    for(EntityWrapper player : testPlayers){
      player.getModel().setBoundedBelow(true);
      player.getModel().setX(xPosition);
      player.getModel().setY(yPosition);
    }
  }
}
