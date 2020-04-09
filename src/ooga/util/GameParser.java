package ooga.util;

import java.io.File;

import org.w3c.dom.Document;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;
import java.util.Iterator;

public class GameParser {
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String IMG_FILEPATH = "resources/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String ACTIONS_PREFIX = PACKAGE_PREFIX_NAME + "actions.";
  private static final String CONTROLS_PREFIX = PACKAGE_PREFIX_NAME + "controlschemes.";
  public static final String CORRUPTED_FILE = "Error with file input. Check game file or choose another game.";
  public static final String CORRUPTED_FIELD = "XML file has corrupted/missing fields";
  public final String CLASS_NOT_FOUND = "Game not valid";

  private File myFile;
  private Document myDoc;


  public GameParser (String entityName){
    JSONObject gameConfig = new JSONObject();
    myFile = new File(TXT_FILEPATH + "properties/" + entityName + ".xml");
  }



}
