package ooga.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.swing.text.html.parser.Entity;
import ooga.controller.Controller;
import ooga.controller.EntityWrapper;
import ooga.model.actions.Action;
import ooga.model.actions.actionExceptions.InvalidActionException;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import ooga.model.levels.Level;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GameParser {

  private String myFileName;
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String IMG_FILEPATH = "resources/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String LEVELS_PREFIX = PACKAGE_PREFIX_NAME + "levels.";
  private String fileName;
  private Controller mainController;
  private int maxPlayers;
  private int selectedPlayers;
  private List<EntityWrapper> playerList;


  private JSONObject jsonObject;

  public GameParser(String gameName) {
    fileName = gameName + "Game";
    myFileName = TXT_FILEPATH + "properties/" + fileName + ".json";
    jsonObject = (JSONObject) readJsonFile();
    selectedPlayers = Integer.parseInt(jsonObject.get("players").toString());
    playerList = parsePlayerEntities();
  }


  public GameParser(String gameName, Controller controller) {
    fileName = gameName + "Game";
    mainController = controller;
    myFileName = TXT_FILEPATH + "properties/" + fileName + ".json";
    jsonObject = (JSONObject) readJsonFile();
    selectedPlayers = Integer.parseInt(jsonObject.get("players").toString());
    playerList = parsePlayerEntities();
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

  public List<EntityWrapper> getPlayerList(){
    return playerList;
  }

  public void updateJSONValue(String key, String newValue){
    JSONObject root = jsonObject;
    String new_val = newValue;
    String old_val = root.get(key).toString();

    if(!new_val.equals(old_val))
    {
      root.put(key,new_val);

      try (FileWriter file = new FileWriter("src/resources/properties/" + fileName + ".json", false))
      {
        file.write(root.toString());
        System.out.println("Successfully updated json object to file");
      } catch (IOException e) {
        e.printStackTrace();//FIXME: TO AVOID FAILING CLASS
      }
    }
  }

  public boolean supportsMultiplayer(){
    return maxPlayers > 1;
  }


  public List<Level> parseLevels() {
    List<Level> levelList = new ArrayList<>();
    JSONArray levelArrangement = (JSONArray) jsonObject.get("levelArrangement");
    JSONObject levels = (JSONObject) levelArrangement.get(0);

    for(Object levelNumber : levels.keySet()){
      LevelParser parsedLevel = new LevelParser(levels.get(levelNumber).toString(), mainController);
      String levelType = parsedLevel.readLevelType();
      List<EntityWrapper> tiles = parsedLevel.parseTileEntities();
      List<EntityWrapper> enemies = parsedLevel.parseEnemyEntities();
      try {
        Level newLevel = (Level) Class.forName(LEVELS_PREFIX + levelType).getDeclaredConstructor(List.class, List.class, List.class).newInstance(tiles, playerList, enemies);

        levelList.add(newLevel);
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          throw new InvalidActionException("Level could not be found.");
      }
    }

    return levelList;
  }




  private List<EntityWrapper> parsePlayerEntities() {
    JSONArray playerArrangement = (JSONArray) jsonObject.get("playerArrangement");
    this.maxPlayers = playerArrangement.size();
    System.out.println(maxPlayers);
    List<EntityWrapper> playerEntityArray = new ArrayList<EntityWrapper>();

    for(int i = 0; i < selectedPlayers; i++){ //TODO: DISCUSS TREATING SINGLEPLAYER MARIO AND MULTIPLAYER MARIO AS DIFF GAMES WITH DIFF DATA FILES
      JSONObject playerInfo = (JSONObject) playerArrangement.get(i);
      String entityName = playerInfo.get("EntityName").toString();
      JSONObject playerLocation = (JSONObject) playerInfo.get("Arrangement");

      EntityWrapper newPlayer = new EntityWrapper(entityName, mainController);
      newPlayer.getModel().setX(Double.parseDouble(playerLocation.get("X").toString()));
      newPlayer.getModel().setY(Double.parseDouble(playerLocation.get("Y").toString()));
      playerEntityArray.add(newPlayer);
    }

    return playerEntityArray;
  }

  public PhysicsProfile parsePhysicsProfile() {
    JSONArray physicsArray = (JSONArray) jsonObject.get("physicsProfile");
    JSONObject physicsConstants = (JSONObject) physicsArray.get(0);
    PhysicsProfile gamePhysics = new PhysicsProfile(physicsConstants);
    return gamePhysics;
  }

}