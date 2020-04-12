package ooga.util;

import java.io.FileReader;
import java.io.IOException;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ComplexActionParser {
  private String myFileName;
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String IMG_FILEPATH = "resources/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String ACTIONS_PREFIX = PACKAGE_PREFIX_NAME + "actions.";
  private static final String CONTROLS_PREFIX = PACKAGE_PREFIX_NAME + "controlschemes.";

  private JSONObject jsonObject;

  public ComplexActionParser(String fileName) {
    myFileName = TXT_FILEPATH + "properties/" + fileName + ".json";
    jsonObject = (JSONObject) readJsonFile();
  }

  //FIXME add error handling
  public Object readJsonFile() {
    try {
      FileReader reader = new FileReader(myFileName);
      JSONParser jsonParser = new JSONParser();
      return jsonParser.parse(reader);
    } catch (IOException | ParseException e){
      throw new InvalidControlSchemeException(e);
    }
  }


}
