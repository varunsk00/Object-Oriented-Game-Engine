package ooga.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import ooga.controller.EntityWrapper;
import ooga.controller.GameController;
import ooga.util.config.Parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LevelParser extends Parser {
  private static final String ERROR = "NO MATCH";
  private List<Entry<String, Pattern>> mySymbols;
  private static final String TXT_FILEPATH = "src/resources/";
  private GameController mainController;
  private double tileHeight;
  private double tileWidth;

  private JSONObject jsonObject;

  public LevelParser(String fileName, GameController controller) {
    String[] gameAndName = fileName.split("\\.");
    mainController = controller;
    setMyFileName(TXT_FILEPATH + gameAndName[0] + "/levels/" + gameAndName[1] + ".json");
    jsonObject = (JSONObject) readJsonFile();
    tileHeight = Double.parseDouble(jsonObject.get("tileHeight").toString());
    tileWidth = Double.parseDouble(jsonObject.get("tileWidth").toString());
    setUpLevelParser();
  }


  private List<EntityWrapper> readEntities(JSONArray entitiesArray) {
    List<EntityWrapper> entitiesParsed = new ArrayList<>();

    for (int i = 0; i < entitiesArray.size(); i++) {
      JSONObject entityEntry = (JSONObject) entitiesArray.get(i);
      String entityName = (String) entityEntry.get("EntityName");
      JSONArray entityArrangement = (JSONArray) entityEntry.get("Arrangement");

      for (int j = 0; j < entityArrangement.size(); j++) {
        JSONObject entityCoordinates = (JSONObject) entityArrangement.get(j);
        setCoordinates(entitiesParsed, entityName, entityCoordinates);
      }
    }
    return entitiesParsed;
  }

  private void setCoordinates(List<EntityWrapper> entitiesParsed, String entityName,
      JSONObject entityCoordinates) {
    for (Object key : entityCoordinates.keySet()) {
      createCoordinates(entitiesParsed, entityName, entityCoordinates, key);
    }
  }

  private void createCoordinates(List<EntityWrapper> entitiesParsed, String entityName,
      JSONObject entityCoordinates, Object key) {
    String rowCoordinate = key.toString();

    JSONArray columnCoordinateArray = (JSONArray) entityCoordinates.get(key);
    for (int k = 0; k < columnCoordinateArray.size(); k++) {
      String columnCoordinate = (String) columnCoordinateArray.get(k);
      String symbolName = this.getSymbol(columnCoordinate);

      if(symbolName.equals("Single")){
        setXYCoor(entityName, rowCoordinate, entitiesParsed, Integer.parseInt(columnCoordinate));
      }
      else if(symbolName.equals("Group")){

        String[] splitArray = columnCoordinate.split("-");

        for(int start = Integer.parseInt(splitArray[0]); start < Integer.parseInt(splitArray[1]); start++){
          setXYCoor(entityName, rowCoordinate, entitiesParsed, start);
        }
      }
    }
  }

  private void setXYCoor(String entityName, String rowCoordinate,
      List<EntityWrapper> entitiesParsed, int i) {
    EntityWrapper levelEntity = new EntityWrapper(entityName, mainController);
    levelEntity.getModel().setX(i * tileWidth);
    levelEntity.getModel().setY(Integer.parseInt(rowCoordinate) * tileHeight);
    entitiesParsed.add(levelEntity);
  }

  public List<EntityWrapper> parseTileEntities() {
    JSONArray tileArrangement = (JSONArray) jsonObject.get("tileArrangement");
    List<EntityWrapper> tileEntityArray;

    tileEntityArray = readEntities(tileArrangement);
    for(EntityWrapper entity : tileEntityArray){
      entity.getModel().setHeight(tileHeight);
      entity.getModel().setWidth(tileWidth);

    }

    return tileEntityArray;
  }

  public List<EntityWrapper> parseEnemyEntities() {
    JSONArray enemyArrangement = (JSONArray) jsonObject.get("enemyArrangement");
    List<EntityWrapper> enemyEntityArray;

    enemyEntityArray = readEntities(enemyArrangement);

    return enemyEntityArray;
  }

  public String readLevelType() {
    return jsonObject.get("levelType").toString();
  }


  /**
   * Adds the given resource file to this language's recognized types
   */
  private void addPatterns (String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      mySymbols.add(new SimpleEntry<>(key,
              Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  /**
   * Returns language's type associated with the given text if one exists
   */
  private String getSymbol (String text) {
    for (Entry<String, Pattern> e : mySymbols) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    return ERROR;
  }

  /**
   * Returns a boolean if the text matches a regular expression found in the pattern
   */
  private boolean match (String text, Pattern regex) {
    return regex.matcher(text).matches();
  }

  private void setUpLevelParser(){
    mySymbols = new ArrayList<>();
    addPatterns(LevelParser.class.getPackageName() + ".resources." + "LevelParsingRegex");
  }

}