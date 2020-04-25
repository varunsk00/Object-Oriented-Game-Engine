package ooga.util;

import static org.junit.jupiter.api.Assertions.*;

import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import ooga.controller.GameController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameParserTest {
  GameParser gameParser;

  @BeforeEach
  void setUp() throws XInputNotLoadedException {
    gameParser = new GameParser("UnitTest", null, false);
  }

  @Test
  void testGetPlayerList() {
    assertTrue(gameParser.getPlayerList().size() == 2);
    assertEquals("unittest.UnitTestEntity", gameParser.getPlayerList().get(0).getEntityID());
    assertEquals("unittest.UnitTestEntity2", gameParser.getPlayerList().get(1).getEntityID());
    assertEquals(100, gameParser.getPlayerList().get(0).getModel().getX());
    assertEquals(100, gameParser.getPlayerList().get(0).getModel().getY());
    assertEquals(0, gameParser.getPlayerList().get(1).getModel().getX());
    assertEquals(100, gameParser.getPlayerList().get(1).getModel().getY());
  }

  @Test
  void testSaveGame() {
    assertEquals(2, gameParser.parseLevels().size());
    JSONArray arr = new JSONArray();
    JSONObject obj = new JSONObject();
    obj.put("Level_1", "unittest.testLevel1");
    arr.add(obj);
    gameParser.saveGame("levelArrangement", arr);
    assertEquals(1, gameParser.parseLevels().size());
  }

  @Test
  void testUpdateJSONValue() {
    assertEquals(2, gameParser.readPlayerCount());
    gameParser.updateJSONValue("playerCount", 1);
    assertEquals(1, gameParser.readPlayerCount());
    gameParser.updateJSONValue("playerCount", 2);
  }

  @Test
  void supportsMultiplayer() {
    assertTrue(gameParser.supportsMultiplayer());
  }

  @Test
  void parseLevels() {
    assertEquals("unittest.testLevel1", gameParser.parseLevels().get(0).getLevelName());
    assertEquals("unittest.testLevel2", gameParser.parseLevels().get(1).getLevelName());
    assertEquals(2, gameParser.parseLevels().size());
  }

  @Test
  void parsePhysicsProfile() {
    assertEquals(20, gameParser.parsePhysicsProfile().readFriction());
    assertEquals(1, gameParser.parsePhysicsProfile().readDrag());
    assertEquals(20, gameParser.parsePhysicsProfile().readGravity());
  }

  @Test
  void parseGameStatusProfile() {
    assertEquals(1, gameParser.parseGameStatusProfile().readScrollingStatusY());
    assertEquals(0, gameParser.parseGameStatusProfile().readSpawningInterval());
    assertEquals(1, gameParser.parseGameStatusProfile().readScrollingStatusX());
    assertEquals(0, gameParser.parseGameStatusProfile().readLevelSpawnOffset());
  }
}