
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
  private final int[] expectedStartingPositionsX = new int[]{1, 1, 1, 1, 2};
  private final int[] expectedStartingPositionsY = new int[]{3, 4, 5, 6, 6};
  private final int spawningInterval = 500;
  private final int levelSpawnOffset = 2;
  private final int tileHeight = 100;
  private final int tileWidth = 100;

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

  }

  @Test
  void testSpawnEntitiesOne() {
    double xPosition = 0;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() == 5);
  }

  @Test
  void testSpawnEntitiesTwo() {
    double xPosition = 600;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() == 5);
  }

  @Test
  void testSpawnEntitiesThree() {
    double xPosition = 1100;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() == 5);
  }

  @Test
  void testSpawnEntitiesFour() {
    double xPosition = 100;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() == 5);
    xPosition = 600;
    yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    assertTrue(testEntityList.size() == 10);
  }

  @Test
  void testSpawnEntitiesPosition() {
    double xPosition = 100;
    double yPosition = 150;
    setPlayersLocation(xPosition, yPosition);
    testLevel.spawnEntities(testEntityList);
    int tileInterval = testLevel.getCurrentPlayerInterval() + levelSpawnOffset;
    for(int i = 0; i < testEntityList.size(); i++){
      try {
        assertTrue(testEntityList.get(i).getModel().getX()
            == spawningInterval * tileInterval + tileWidth * expectedStartingPositionsX[i]);
        assertTrue(
            testEntityList.get(i).getModel().getY() == tileHeight * expectedStartingPositionsY[i]);
      }
      catch(AssertionError e){
        System.out.println("entityList Index: " + i);
        int expectedX = spawningInterval * tileInterval + tileWidth * expectedStartingPositionsX[i];
        int actualX = (int) testEntityList.get(i).getModel().getX();
        int expectedY = tileHeight * expectedStartingPositionsY[i];
        int actualY = (int) testEntityList.get(i).getModel().getY();

        System.out.println("Expected X: " + expectedX + "        " + "Actual X: " + actualX);
        System.out.println("Expected Y: " + expectedY + "        " + "Actual Y: " + actualY);
        throw e;
      }
    }
  }

  void setPlayersLocation(double xPosition, double yPosition){
    for(EntityWrapper player : testPlayers){
      player.getModel().setBoundedBelow(true);
      player.getModel().setX(xPosition);
      player.getModel().setY(yPosition);
    }
  }
}
