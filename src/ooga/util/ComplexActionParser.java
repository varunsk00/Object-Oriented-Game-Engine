package ooga.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ooga.model.actions.Action;
import ooga.model.actions.ActionFactory;
import ooga.model.actions.CollisionKey;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import org.json.simple.JSONArray;
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
    myFileName = TXT_FILEPATH + "complexaction/" + fileName + ".json";
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

  public List<Action> createComplexAction(){
    List<Action> output = new ArrayList<Action>();
    JSONArray actionArray = (JSONArray) jsonObject.get("actionList");
    if(actionArray != null) {

      for (int i = 0; i < actionArray.size(); i++) {
        JSONObject actionEntry = (JSONObject) actionArray.get(i);
        ActionFactory actionFactory = new ActionFactory();
        String actionName = (String) actionEntry.get("action");
        String paramName = (String) actionEntry.get("param");
        String orientation = (String) actionEntry.get("duration");

        Action newAction = actionFactory
            .makeAction(actionName, new Class<?>[]{String.class}, new Object[]{paramName});
        output.add(newAction);
      }
    }
    return output;
  }
}
