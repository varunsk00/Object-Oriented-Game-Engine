package ooga.view.gui.userinterface;

import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import ooga.util.config.ParameterMissingException;
import ooga.util.config.Parser;
import ooga.view.gui.ProgramLauncher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class GameSelectParser extends Parser {

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
      return "GameNameMissing";
    }
  }
  public String readGamePreviewGIF() {
    try {
      return jsonObject.get("gamePreviewGIF").toString();
    }
    catch (NullPointerException e){
     new ParameterMissingException(e, "gamePreviewGif");
     return "defaultGif";
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
      for (int i = 0; i < buttonJSONArray.size(); i++) { //TODO: ask if anything else is needed in making buttons (like EventHandlers?)
        buttonStrings.add(buttonJSONArray.get(i).toString());
      }
      return buttonStrings;
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "buttonArrangement");
      buttonStrings.add("1 Player"); //default
      return buttonStrings;
    }

  }
}