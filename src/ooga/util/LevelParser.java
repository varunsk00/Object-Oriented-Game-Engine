package ooga.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import ooga.controller.Controller;
import ooga.controller.EntityWrapper;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;

public class LevelParser {

  private String myFileName;
  private static final String REGEX_SYNTAX = "Syntax";
  private List<Entry<String, Pattern>> mySymbols;
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String IMG_FILEPATH = "resources/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String ACTIONS_PREFIX = PACKAGE_PREFIX_NAME + "actions.";
  private static final String CONTROLS_PREFIX = PACKAGE_PREFIX_NAME + "controlschemes.";
  public static final String CORRUPTED_FILE = "Error with file input. Check game file or choose another game.";
  public static final String CORRUPTED_FIELD = "XML file has corrupted/missing fields";
  public final String CLASS_NOT_FOUND = "Game not valid";
  private Controller mainController;
  private double tileHeight;
  private double tileWidth;

  private JSONObject jsonObject;

  public LevelParser(String gameName, String fileName, Controller controller) {
    mainController = controller;
    myFileName = TXT_FILEPATH + gameName + "/levels/" + fileName + ".json";
    jsonObject = (JSONObject) readJsonFile();
    tileHeight = Double.parseDouble(jsonObject.get("tileHeight").toString());
    tileWidth = Double.parseDouble(jsonObject.get("tileWidth").toString());
    setUpLevelParser();
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

  public void updateJSONValue(String key, String newValue){
    JSONObject root = jsonObject;
    String new_val = newValue;
    String old_val = root.get(key).toString();

    if(!new_val.equals(old_val))
    {
      root.put(key,new_val);

      try (FileWriter file = new FileWriter("src/resources/properties/MarioGame.json", false)) //FIXME: MULT FILES
      {
        file.write(root.toString());
        System.out.println("Successfully updated json object to file");
      } catch (IOException e) {
        e.printStackTrace();//FIXME: TO AVOID FAILING CLASS
      }
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
            String symbolName = this.getSymbol(columnCoordinate);

            if(symbolName.equals("Single")){
              EntityWrapper levelEntity = new EntityWrapper(entityName, mainController);
              levelEntity.getModel().setX(Integer.parseInt(columnCoordinate) * tileWidth);
              levelEntity.getModel().setY(Integer.parseInt(rowCoordinate) * tileHeight);
              entitiesParsed.add(levelEntity);
            }
            else if(symbolName.equals("Group")){

              String[] splitArray = columnCoordinate.split("-");

              for(int start = Integer.parseInt(splitArray[0]); start < Integer.parseInt(splitArray[1]); start++){
                EntityWrapper levelEntity = new EntityWrapper(entityName, mainController);
                levelEntity.getModel().setX(start * tileWidth);
                levelEntity.getModel().setY(Integer.parseInt(rowCoordinate) * tileHeight);

                entitiesParsed.add(levelEntity);
              }

            }
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
      System.out.print(entity+ "   ");
      System.out.print(entity.getModel().getHeight()+ "   ");
      System.out.println(entity.getModel().getWidth());

      entity.getModel().setHeight(tileHeight);
      entity.getModel().setWidth(tileWidth);
      System.out.print("After " + entity+ "   ");
      System.out.print(entity.getModel().getHeight()+ "   ");
      System.out.println(entity.getModel().getWidth());

    }

    return tileEntityArray;
  }

  public List<EntityWrapper> parsePlayerEntities() {
    JSONArray playerArrangement = (JSONArray) jsonObject.get("playerArrangement");
    List<EntityWrapper> playerEntityArray = new ArrayList<EntityWrapper>();
    playerEntityArray = readEntities(playerArrangement);
    return playerEntityArray;
  }

  public List<EntityWrapper> parseEnemyEntities() {
    JSONArray enemyArrangement = (JSONArray) jsonObject.get("enemyArrangement");
    List<EntityWrapper> enemyEntityArray = new ArrayList<EntityWrapper>();

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
    final String ERROR = "NO MATCH";
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