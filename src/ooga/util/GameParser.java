package ooga.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Array;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedMap;
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
  private String gameName;
  private Controller mainController;
  private int maxPlayers;
  private int selectedPlayers;
  private List<EntityWrapper> playerList;
  private boolean loadedGame;
  private GameStatusProfile gameStatusProfile;



  private JSONObject jsonObject;

  public GameParser(String gameName, Controller controller, boolean loadedGame) {
    mainController = controller;
    this.gameName = gameName;
    this.loadedGame = loadedGame;
    fileName = gameName + "Game";

    this.loadedGame = loadedGame;
    jsonObject = (JSONObject) readJsonFile();
    gameStatusProfile = parseGameStatusProfile();
    selectedPlayers = readPlayerCount();
    playerList = parsePlayerEntities();
  }
  

  private void checkLoadGame(boolean loadedGame) {
    if (loadedGame) {
      myFileName = TXT_FILEPATH + gameName.toLowerCase() + "/" + "saves/" + fileName + "Saved" + ".json";
    } else {
      myFileName = TXT_FILEPATH + gameName.toLowerCase() + "/" + fileName + ".json";
    }
  }



  //FIXME add error handling
  public Object readJsonFile() {
    try {
      checkLoadGame(this.loadedGame);
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


  public void saveGame(String key, JSONArray newValue){
    JSONObject root = jsonObject;
    JSONArray new_val = newValue;
    root.put(key, newValue);
    root.put("players", jsonObject.get("players"));
//    String old_val = root.get(key).toString();
//
//    if(!new_val.equals(old_val))
//    {
//      root.put(key,new_val);

      try (FileWriter file = new FileWriter(TXT_FILEPATH + gameName.toLowerCase() + "/" + "saves/" + fileName + "Saved" + ".json", false))
      {
        file.write(root.toString());
        System.out.println("Successfully updated json object to file");
      } catch (IOException e) {
        e.printStackTrace();//FIXME: TO AVOID FAILING CLASS
      }
//    }
  }

  public void updateJSONValue(String key, Object newValue){
    JSONObject root = jsonObject;
    Object new_val = newValue;
    Object old_val = root.get(key).toString();

//    if(!new_val.equals(old_val))
//    {
      root.put(key,new_val);

      try (FileWriter file = new FileWriter("src/resources/" + gameName.toLowerCase() + "/" + fileName + ".json", false))
      {
        file.write(root.toString());
        System.out.println("Successfully updated json object to file");
      } catch (IOException e) {
        e.printStackTrace();//FIXME: TO AVOID FAILING CLASS
      }
    //}
  }

  private List<String> sortLevelKeySet(Set keySet){
    List<String> sortedKeys = new ArrayList<>();
    for(Object key : keySet){
      sortedKeys.add(key.toString());
    }
    Collections.sort(sortedKeys);
    return sortedKeys;
  }

  public boolean supportsMultiplayer(){
    return maxPlayers > 1;
  }


  public List<Level> parseLevels() {
    List<Level> levelList = new ArrayList<>();
    JSONArray levelArrangement = (JSONArray) jsonObject.get("levelArrangement");
    JSONObject levels = (JSONObject) levelArrangement.get(0);
    List<String> sortedLevelKeys = sortLevelKeySet(levels.keySet());


    for(String levelNumber : sortedLevelKeys){
      String levelName = (String) levels.get(levelNumber);
      LevelParser parsedLevel = new LevelParser(levels.get(levelNumber).toString(), mainController);
      String levelType = parsedLevel.readLevelType();
      List<EntityWrapper> tiles = parsedLevel.parseTileEntities();
      List<EntityWrapper> enemies = parsedLevel.parseEnemyEntities();
      try {
        Level newLevel = (Level) Class.forName(LEVELS_PREFIX + levelType).getDeclaredConstructor
            (List.class, List.class, List.class, GameStatusProfile.class, String.class).newInstance(tiles, playerList, enemies, gameStatusProfile, levelName);
        levelList.add(newLevel);
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          throw new InvalidActionException("Level could not be found."); //TODO: change exception heading
      }
    }

    return levelList;
  }




  private List<EntityWrapper> parsePlayerEntities() {
    JSONArray playerArrangement = (JSONArray) jsonObject.get("playerArrangement");
    this.maxPlayers = playerArrangement.size();
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

  public GameStatusProfile parseGameStatusProfile() {
    JSONArray gameStatusArray = (JSONArray) jsonObject.get("gameStatusProfile");
    JSONObject gameStatusVariables = (JSONObject) gameStatusArray.get(0);
    GameStatusProfile gameVariables = new GameStatusProfile(gameStatusVariables);
    return gameVariables;
  }

  private int readPlayerCount() {
    return Integer.parseInt(jsonObject.get("playerCount").toString());
  }

}