package ooga.view.gui.userinterface;

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

public class GameSelectParser {

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

  private JSONObject jsonObject;

  public GameSelectParser(String gameName) {
    String gameFile = gameName + "Select";
    myFileName = TXT_FILEPATH + gameName.toLowerCase() + "/" + gameFile + ".json";
    jsonObject = (JSONObject) readJsonFile();
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

  public String readGameName() {
    return jsonObject.get("gameName").toString();
  }
  public String readGamePreviewGIF() {
    return jsonObject.get("gamePreviewGIF").toString();
  }

  public boolean readSaveStatus() {
    return Boolean.parseBoolean(jsonObject.get("savingEnabled").toString());
  }


  public int readButtonArrangement() {
    return ((Integer) jsonObject.get("buttonArrangement")).intValue();
  }
}