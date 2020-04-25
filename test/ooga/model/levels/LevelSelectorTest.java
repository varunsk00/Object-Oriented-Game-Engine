
package ooga.model.levels;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import ooga.controller.EntityWrapper;
import ooga.util.GameParser;
import ooga.util.LevelParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LevelSelectorTest {
  private LevelSelector testLevelSelector;
  private List<EntityWrapper> testEntityList;
  private List<EntityWrapper> testPlayers;


  @BeforeEach
  void setUp(){
    String gameName = "unittest";
    testEntityList = new ArrayList<>();
    String[] testLevelStrings = {".testLevel1", ".testLevel2"};
    GameParser testGameParser = new GameParser(gameName, null, false);
    testPlayers = testGameParser.getPlayerList();
    testEntityList.addAll(testPlayers);

    List<Level> testLevelList = new ArrayList<>();
    for(String levelName : testLevelStrings){
      LevelParser testLevelParser = new LevelParser(gameName + levelName,null);
      Level testLevel = new FiniteLevel(testLevelParser.parseTileEntities(), testPlayers, testLevelParser.parseEnemyEntities(), testGameParser.parseGameStatusProfile(), gameName);
      testLevelList.add(testLevel);
    }
    testLevelSelector = new LevelSelector(testLevelList, testEntityList, testGameParser.parseGameStatusProfile(), null);


  }

  @Test
  void testSwitchLevel() {

  }

  @Test
  void testSpawnEntitiesTwo() {


  }

  @Test
  void testSpawnEntitiesThree() {

  }

  void setPlayersLocation(double xPosition, double yPosition){
    for(EntityWrapper player : testPlayers){
      player.getModel().setBoundedBelow(true);
      player.getModel().setX(xPosition);
      player.getModel().setY(yPosition);
    }
  }
}
