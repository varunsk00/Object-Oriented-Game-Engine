package ooga.util;

import java.io.File;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.controller.EntityWrapper;
import ooga.controller.MainController;
import ooga.model.actions.Action;
import ooga.model.actions.ActionFactory;
import ooga.model.actions.CollisionKey;
import ooga.model.controlschemes.ControlScheme;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import ooga.model.levels.Level;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;
import java.util.Iterator;
import org.w3c.dom.Entity;

public class GameParser {

  private String myFileName;
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String IMG_FILEPATH = "resources/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String ACTIONS_PREFIX = PACKAGE_PREFIX_NAME + "actions.";
  private static final String CONTROLS_PREFIX = PACKAGE_PREFIX_NAME + "controlschemes.";
  public static final String CORRUPTED_FILE = "Error with file input. Check game file or choose another game.";
  public static final String CORRUPTED_FIELD = "XML file has corrupted/missing fields";
  public final String CLASS_NOT_FOUND = "Game not valid";
  private MainController mainController;
  private double tileHeight;
  private double tileWidth;

  private JSONObject jsonObject;

  public GameParser(String fileName, MainController controller) {
    mainController = controller;
    myFileName = TXT_FILEPATH + "properties/" + fileName + ".json";
    jsonObject = (JSONObject) readJsonFile();
    tileHeight = Double.parseDouble(jsonObject.get("tileHeight").toString());
    tileWidth = Double.parseDouble(jsonObject.get("tileWidth").toString());
  }

  //FIXME add error handling
  public Object readJsonFile() {
    try {
      FileReader reader = new FileReader(myFileName);
      JSONParser jsonParser = new JSONParser();
      return jsonParser.parse(reader);
    } catch (IOException | ParseException e) {
      throw new InvalidControlSchemeException(e);
    }
  }

  private List<EntityWrapper> readEntities(JSONArray entitiesArray) {
    List<EntityWrapper> entitiesParsed = new ArrayList<EntityWrapper>();


    for (int i = 0; i < entitiesArray.size(); i++) {
      JSONObject entityEntry = (JSONObject) entitiesArray.get(i);
      String entityName = (String) entityEntry.get("EntityName");
      JSONArray entityArrangement = (JSONArray) entityEntry.get("Arrangement");

      for (int j = 0; j < entityArrangement.size(); j++) {
        JSONObject entityCoordinates = (JSONObject) entityArrangement.get(j);

        for (Object key : entityCoordinates.keySet()) {
          String rowCoordinate = key.toString();

          JSONArray columnCoordinateArray = (JSONArray) entityCoordinates.get(key);
          for (int k = 0; k < columnCoordinateArray.size(); k++) {
            String columnCoordinate = (String) columnCoordinateArray.get(k);
            EntityWrapper levelEntity = new EntityWrapper(entityName, mainController);

            levelEntity.getModel().setX(Double.parseDouble(rowCoordinate) * tileWidth);
            levelEntity.getModel().setY(Double.parseDouble(columnCoordinate) * tileHeight);

            entitiesParsed.add(levelEntity);
          }

        }

      }

    }
    return entitiesParsed;
  }

  public List<EntityWrapper> parseTileEntities() {
    JSONArray tileArrangement = (JSONArray) jsonObject.get("tileArrangement");
    List<EntityWrapper> tileEntityArray = new ArrayList<EntityWrapper>();

    tileEntityArray = readEntities(tileArrangement);
    for(EntityWrapper entity : tileEntityArray){
      System.out.println(entity);
      entity.getModel().setHeight(tileHeight);
      entity.getModel().setWidth(tileWidth);
    }

    return tileEntityArray;
  }
}

