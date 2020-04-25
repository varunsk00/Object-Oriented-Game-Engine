package ooga.util;

import static org.junit.jupiter.api.Assertions.*;

import com.thoughtworks.xstream.mapper.Mapper.Null;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.exceptions.ParameterMissingException;
import ooga.model.actions.Action;
import ooga.model.controlschemes.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntityJSONParserTest {
  EntityJSONParser entityJSONParser;
  String filePath;

  @BeforeEach
  void setUp() {
    entityJSONParser = new EntityJSONParser("unittest", "UnitTestEntity");
    filePath = "resources/images/mario_fire.png";
  }

  @Test
  void testParseControlsNotNull() {
    assertNotNull(entityJSONParser.parseControls());
  }

  @Test
  void testParseControls() {
    assertEquals(Keyboard.class, entityJSONParser.parseControls().getClass());
  }

  @Test
  void testControlSchemeNotNull() {
    entityJSONParser.updateControlScheme("Pattern");
    entityJSONParser.readJsonFile();
    assertNotNull(entityJSONParser.parseControls());
    entityJSONParser.updateControlScheme("Keyboard");
  }

  @Test
  void testUpdateControlScheme() {
    entityJSONParser.updateControlScheme("Pattern");
    entityJSONParser.readJsonFile();
    assertEquals(Pattern.class, entityJSONParser.parseControls().getClass());
    entityJSONParser.updateControlScheme("Keyboard");
  }

  @Test
  void testUpdateControlsNotNull() {
    assertTrue(entityJSONParser.updateControls("Jump", "G", true) != null);
  }

  @Test
  void testUpdateControlsHasValues() {
    assertTrue(entityJSONParser.updateControls("Jump", "G", true).size() > 0);
  }

  @Test
  void testParseCollisionsNotNull() {
    assertTrue(entityJSONParser.parseCollisions() != null);
  }

  @Test
  void testParseCollisionsHasValues() {
    assertTrue(entityJSONParser.parseCollisions().size() > 0);
  }

  @Test
  void testGenerateImageName() {
    ImageView output = new ImageView(new Image(entityJSONParser.getClass().getClassLoader().getResourceAsStream(filePath)));

    assertEquals("mario_fire.png", entityJSONParser.readImage());
  }

  @Test
  void testGenerateImageLocation() {
    ImageView output = new ImageView(new Image(entityJSONParser.getClass().getClassLoader().getResourceAsStream(filePath)));

    output.setX(0);
    output.setY(0);
    output.setFitHeight(100);
    output.setFitWidth(100);

    assertEquals(output.getX(), entityJSONParser.generateImage().getX());
    assertEquals(output.getY(), entityJSONParser.generateImage().getY());
    assertEquals(output.getFitHeight(), entityJSONParser.generateImage().getFitHeight());
    assertEquals(output.getFitWidth(), entityJSONParser.generateImage().getFitWidth());
  }

  @Test
  void testReadWidth() {
    assertEquals(100, entityJSONParser.readWidth());
  }

  @Test
  void testReadWidthNotNull() {
    assertNotNull(entityJSONParser.readWidth());
  }

  @Test
  void testReadHeight() {
    assertEquals(100, entityJSONParser.readHeight());
  }

  @Test
  void testReadHeightNotNull() {
    assertNotNull(entityJSONParser.readHeight());
  }

  @Test
  void testReadXPosition() {
    assertEquals(0, entityJSONParser.readXPosition());
  }

  @Test
  void testReadXPositionNotNull() {
    assertNotNull(entityJSONParser.readXPosition());
  }

  @Test
  void testReadYPosition() {
    assertEquals(0, entityJSONParser.readYPosition());
  }
@Test
  void testReadYPositionNotNull() {
    assertNotNull(entityJSONParser.readYPosition());
  }

  @Test
  void testReadMaxXVelocity() {
    assertEquals(1000, entityJSONParser.readMaxXVelocity());
  }  @Test
  void testReadMaxXVelocityNotNull() {
    assertNotNull(entityJSONParser.readMaxXVelocity());
  }

  @Test
  void readMaxYVelocity() {
    assertEquals(1000, entityJSONParser.readMaxYVelocity());
  }

  @Test
  void readMaxYVelocityNotNull() {
    assertNotNull(entityJSONParser.readMaxYVelocity());
  }

  @Test
  void readHealth() {
    assertEquals(1, entityJSONParser.readHealth());
  }

  @Test
  void readHealthNotNull() {
    assertNotNull(entityJSONParser.readHealth());
  }

  @Test
  void readFixed() {
    assertEquals(false, entityJSONParser.readFixed());
  }

  @Test
  void readFixedNotNull() {
    assertNotNull(entityJSONParser.readFixed());
  }

  @Test
  void readPermeable() {
    assertEquals(true, entityJSONParser.readPermeable());
  }

  @Test
  void readPermeableNotNull() {
    assertNotNull(entityJSONParser.readPermeable());
  }

  @Test
  public void testControlClassNotFoundIsNull() {

    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("scheme");
      Boolean.parseBoolean(obj.get("scheme").toString());
    });
  }


  @Test
  public void testImageIsNull() {
    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("image");
      obj.get("image").toString();
    });
  }

  @Test
  public void testPermeableIsNull() {

    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("permeable");
      Boolean.parseBoolean(obj.get("permeable").toString());
    });
  }

  @Test
  public void testWidthIsNull() {
    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("width");
      Double.parseDouble(obj.get("width").toString());
    });

    assertThrows(NumberFormatException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.put("width", "hehe");
      Double.parseDouble(obj.get("width").toString());
    });
  }

  @Test
  public void testHeightIsNull() {
    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("height");
      Double.parseDouble(obj.get("height").toString());
    });

    assertThrows(NumberFormatException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.put("height", "hehe");
      Double.parseDouble(obj.get("height").toString());
    });
  }

  @Test
  public void testXPosIsNull() {
    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("xPos");
      Double.parseDouble(obj.get("xPos").toString());
    });

    assertThrows(NumberFormatException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.put("xPos", "hehe");
      Double.parseDouble(obj.get("xPos").toString());
    });
  }

  @Test
  public void testYPosIsNull() {
    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("yPos");
      Double.parseDouble(obj.get("yPos").toString());
    });

    assertThrows(NumberFormatException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.put("yPos", "hehe");
      Double.parseDouble(obj.get("yPos").toString());
    });
  }

  @Test
  public void testMaxXIsNull() {
    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("maxXVel");
      Double.parseDouble(obj.get("maxXVel").toString());
    });

    assertThrows(NumberFormatException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.put("maxXVel", "hehe");
      Double.parseDouble(obj.get("maxXVel").toString());
    });
  }

  @Test
  public void testMaxYIsNull() {
    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("maxYVel");
      Double.parseDouble(obj.get("maxYVel").toString());
    });

    assertThrows(NumberFormatException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.put("maxYVel", "hehe");
      Double.parseDouble(obj.get("maxYVel").toString());
    });
  }
  @Test
  public void testHealthIsNull() {
    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("health");
      Double.parseDouble(obj.get("health").toString());
    });

    assertThrows(NumberFormatException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.put("health", "hehe");
      Double.parseDouble(obj.get("health").toString());
    });
  }

  @Test
  public void testFixedIsNull() {
    assertThrows(NullPointerException.class, () -> {
      JSONObject obj = entityJSONParser.getJSONObject();
      obj.remove("fixed");
      Boolean.parseBoolean(obj.get("fixed").toString());

    });
  }




}