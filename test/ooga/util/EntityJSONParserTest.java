package ooga.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.model.actions.Action;
import ooga.model.controlschemes.*;
import org.json.simple.JSONArray;
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
  void testParseControls() {
    assertEquals(Keyboard.class, entityJSONParser.parseControls().getClass());
  }

  @Test
  void testUpdateControlScheme() {
    entityJSONParser.updateControlScheme("Pattern");
    entityJSONParser.readJsonFile();
    assertEquals(Pattern.class, entityJSONParser.parseControls().getClass());
    entityJSONParser.updateControlScheme("Keyboard");
  }

  @Test
  void testUpdateControls() {
    assertTrue(entityJSONParser.updateControls("Jump", "G", true) != null
      && entityJSONParser.updateControls("Jump", "G", true).size() > 0);

  }

  @Test
  void testParseCollisions() {
    assertTrue(entityJSONParser.parseCollisions() != null
      && entityJSONParser.parseCollisions().size() > 0);
  }

  @Test
  void testGenerateImage() {
    ImageView output = new ImageView(new Image(entityJSONParser.getClass().getClassLoader().getResourceAsStream(filePath)));
    output.setX(0);
    output.setY(0);
    output.setFitHeight(100);
    output.setFitWidth(100);

    assertEquals(output.getX(), entityJSONParser.generateImage().getX());
    assertEquals(output.getY(), entityJSONParser.generateImage().getY());
    assertEquals(output.getFitHeight(), entityJSONParser.generateImage().getFitHeight());
    assertEquals(output.getFitWidth(), entityJSONParser.generateImage().getFitWidth());
    assertEquals("mario_fire.png", entityJSONParser.readImage());

  }

  @Test
  void testReadWidth() {
    assertEquals(100, entityJSONParser.readWidth());
  }

  @Test
  void testReadHeight() {
    assertEquals(100, entityJSONParser.readHeight());
  }

  @Test
  void testReadXPosition() {
    assertEquals(0, entityJSONParser.readXPosition());
  }

  @Test
  void testReadYPosition() {
    assertEquals(0, entityJSONParser.readYPosition());
  }

  @Test
  void testReadMaxXVelocity() {
    assertEquals(1000, entityJSONParser.readMaxXVelocity());

  }

  @Test
  void readMaxYVelocity() {
    assertEquals(1000, entityJSONParser.readMaxYVelocity());
  }

  @Test
  void readHealth() {
    assertEquals(1, entityJSONParser.readHealth());
  }

  @Test
  void readFixed() {
    assertEquals(false, entityJSONParser.readFixed());
  }

  @Test
  void readPermeable() {
    assertEquals(true, entityJSONParser.readPermeable());
  }
}