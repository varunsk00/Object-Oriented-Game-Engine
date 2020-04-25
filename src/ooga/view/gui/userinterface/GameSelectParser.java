package ooga.view.gui.userinterface;

import ooga.exceptions.ParameterMissingException;
import ooga.util.config.Parser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class GameSelectParser extends Parser {
  private final String RESOURCES_PACKAGE = "resources.guiText";
  private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE);
  private static final String REGEX_SYNTAX = "Syntax";
  private List<Entry<String, Pattern>> mySymbols;
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String IMG_FILEPATH = "resources/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String ACTIONS_PREFIX = PACKAGE_PREFIX_NAME + "actions.";
  private static final String CONTROLS_PREFIX = PACKAGE_PREFIX_NAME + "controlschemes.";
  public final String CORRUPTED_FILE = myResources.getString("corruptedFile");
  public final String CORRUPTED_FIELD = myResources.getString("corruptedField");
  public final String CLASS_NOT_FOUND = myResources.getString("classNotFound");
  private final String DEFAULT_GAME_NAME = "GameNameMissing"; //filename
  private final String DEFAULT_GAME_PREVIEW_GIF = "defaultGif"; //filename

  private JSONObject jsonObject;

  public GameSelectParser(String gameName) {
    String gameFile = gameName + "Select";
    setMyFileName(TXT_FILEPATH + gameName.toLowerCase() + "/" + gameFile + ".json");
    jsonObject = (JSONObject) readJsonFile();
  }


  public String readGameName() {
    try {
      return jsonObject.get("gameName").toString();
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "gameName");
      return DEFAULT_GAME_NAME;
    }
  }
  public String readGamePreviewGIF() {
    try {
      return jsonObject.get("gamePreviewGIF").toString();
    }
    catch (NullPointerException e){
     new ParameterMissingException(e, "gamePreviewGif");
     return DEFAULT_GAME_PREVIEW_GIF;
    }
  }

  public boolean readSaveStatus() {
    try {
      return Boolean.parseBoolean(jsonObject.get("savingEnabled").toString());
    }
    catch (NullPointerException e){
      new ParameterMissingException(e, "savingEnabled");
      return false;
    }
  }

  public List<String> readButtonArrangement() {
    List<String> buttonStrings = new ArrayList<>();
    try {
      JSONArray buttonJSONArray = (JSONArray) jsonObject.get("buttonArrangement");
      for (int i = 0; i < buttonJSONArray.size(); i++) {
        buttonStrings.add(buttonJSONArray.get(i).toString());
      }
      return buttonStrings;
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "buttonArrangement");
      buttonStrings.add(myResources.getString("defaultPlayer")); //default
      return buttonStrings;
    }

  }
}