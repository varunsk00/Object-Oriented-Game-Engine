package ooga.util;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ooga.controller.EntityWrapper;

import ooga.controller.GameController;

import ooga.exceptions.ParameterInvalidException;
import ooga.model.actions.actionExceptions.InvalidActionException;
import ooga.model.levels.Level;
import ooga.exceptions.ParameterMissingException;
import ooga.util.config.Parser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GameParser extends Parser {

//  private String myFileName;
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String IMG_FILEPATH = "resources/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String LEVELS_PREFIX = PACKAGE_PREFIX_NAME + "levels.";
  private String fileName;
  private String gameName;
  private GameController mainController;
  private int maxPlayers;
  private int selectedPlayers;
  private List<EntityWrapper> playerList;
  private boolean loadedGame;
  private GameStatusProfile gameStatusProfile;
  private final String DEFAULT_GAME_PATH = "src/resources/gamenamemissing/GameNameMissingGame.json";
  private JSONObject defaultObj;


  private JSONObject jsonObject;

  public GameParser(String gameName, GameController controller, boolean loadedGame) {
    mainController = controller;
    this.gameName = gameName;
    this.myFileName = DEFAULT_GAME_PATH;
    defaultObj = (JSONObject) readJsonFile();
    fileName = gameName + "Game";

    setMyFileName(fileName);
    this.loadedGame = loadedGame;

    checkLoadGame(this.loadedGame);
    jsonObject = (JSONObject) readJsonFile();
    gameStatusProfile = parseGameStatusProfile();
    selectedPlayers = readPlayerCount();
    playerList = parsePlayerEntities();
  }
  

  private void checkLoadGame(boolean loadedGame) {
    if (loadedGame) {
      setMyFileName(TXT_FILEPATH + gameName.toLowerCase() + "/" + "saves/" + fileName + "Saved" + ".json");
    } else {
      setMyFileName(TXT_FILEPATH + gameName.toLowerCase() + "/" + fileName + ".json");
    }
  }


  public List<EntityWrapper> getPlayerList(){
    return playerList;
  }


  public void saveGame(String key, JSONArray newValue){
    JSONObject root = jsonObject;
    root.put(key, newValue);
    root.put("playerCount", jsonObject.get("playerCount"));
    String filepath = TXT_FILEPATH + gameName.toLowerCase() + "/" + "saves/" + fileName + "Saved" + ".json";
    try (FileWriter file = new FileWriter(filepath, false)) {
      file.write(root.toString());
      System.out.println("Successfully updated json object to file"); }
    catch (IOException e) {
      new ParameterInvalidException(e, filepath);   }
  }

  public void updateJSONValue(String key, Object newValue){
      JSONObject root = jsonObject;
      root.put(key,newValue);
      try (FileWriter file = new FileWriter(myFileName, false)) {
        file.write(root.toString());
        System.out.println("Successfully updated json object to file"); }
      catch (IOException e) {
        new ParameterInvalidException(e, root.toString()); }
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
    List<String> sortedLevelKeys;
    JSONObject levels;
    try {
      JSONArray levelArrangement = (JSONArray) jsonObject.get("levelArrangement");
      levels = (JSONObject) levelArrangement.get(0);
      sortedLevelKeys = sortLevelKeySet(levels.keySet());
    }
    catch(NullPointerException e){
      new ParameterMissingException(e, "levelArrangement");
      levels = new JSONObject();
      levels.put("Level_1", "gamenamemissing.MissingLevel");
      sortedLevelKeys = sortLevelKeySet(levels.keySet());
    }
    createLevels(levelList, sortedLevelKeys, levels);

    return levelList;
  }

  private void createLevels(List<Level> levelList, List<String> sortedLevelKeys,
      JSONObject levels) {
    for(String levelNumber : sortedLevelKeys){
      String levelName = (String) levels.get(levelNumber);
      LevelParser parsedLevel = new LevelParser(levelName, mainController);
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
  }

  private List<EntityWrapper> parsePlayerEntities() {
    JSONArray playerArrangement = (JSONArray) jsonObject.get("playerArrangement");
    this.maxPlayers = playerArrangement.size();
    List<EntityWrapper> playerEntityArray = new ArrayList<EntityWrapper>();

    createPlayers(playerArrangement, playerEntityArray);

    return playerEntityArray;
  }

  private void createPlayers(JSONArray playerArrangement, List<EntityWrapper> playerEntityArray) {
    for(int i = 0; i < selectedPlayers; i++){ //TODO: DISCUSS TREATING SINGLEPLAYER MARIO AND MULTIPLAYER MARIO AS DIFF GAMES WITH DIFF DATA FILES
      JSONObject playerInfo = (JSONObject) playerArrangement.get(i);
      String entityName = playerInfo.get("EntityName").toString();
      JSONObject playerLocation = (JSONObject) playerInfo.get("Arrangement");
      EntityWrapper newPlayer = new EntityWrapper(entityName, mainController);
      newPlayer.getModel().setX(Double.parseDouble(playerLocation.get("X").toString()));
      newPlayer.getModel().setY(Double.parseDouble(playerLocation.get("Y").toString()));
      playerEntityArray.add(newPlayer);
    }
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

  public int readPlayerCount() {
    return Integer.parseInt(jsonObject.get("playerCount").toString());
  }

}