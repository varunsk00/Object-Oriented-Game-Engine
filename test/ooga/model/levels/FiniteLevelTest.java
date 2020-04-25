package ooga.model.levels;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import javax.swing.text.html.parser.Entity;
import ooga.controller.EntityWrapper;
import ooga.controller.GameController;
import ooga.model.CollisionEngine;
import ooga.util.GameParser;
import ooga.util.LevelParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FiniteLevelTest {
  private FiniteLevel testLevel;
  private EntityWrapper myEntity2;
  private CollisionEngine myEngine;

  @BeforeEach
  void setUp() {
    String gameName = "unittest";
    GameParser testGameParser = new GameParser(gameName, null, false);
    LevelParser testLevelParser = new LevelParser("testLevel1",null);

    List<EntityWrapper> players = testGameParser.getPlayerList();
    List<EntityWrapper> tiles = testLevelParser.parseTileEntities();
    List<EntityWrapper> enemies = testLevelParser.parseEnemyEntities();

    testLevel = new FiniteLevel(tiles, players, enemies, testGameParser.parseGameStatusProfile(), gameName)
  }

  @Test
  void testSpawnEntities() {

  }
}