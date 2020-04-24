package ooga.view.gui.userinterface;

import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import ooga.util.config.Parser;
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
    return jsonObject.get("gameName").toString();
  }
  public String readGamePreviewGIF() {
    return jsonObject.get("gamePreviewGIF").toString();
  }

  public boolean readSaveStatus() {
    return Boolean.parseBoolean(jsonObject.get("savingEnabled").toString());
  }

  public List<String> readButtonArrangement() {
    List<String> buttonStrings = new ArrayList<>();
    JSONArray buttonJSONArray = (JSONArray) jsonObject.get("buttonArrangement");
    for (int i = 0; i < buttonJSONArray.size(); i++) { //TODO: ask if anything else is needed in making buttons (like EventHandlers?)
      buttonStrings.add(buttonJSONArray.get(i).toString());
    }
    return buttonStrings;
  }
}