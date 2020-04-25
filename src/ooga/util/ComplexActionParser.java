package ooga.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ooga.model.actions.Action;
import ooga.model.actions.ActionFactory;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import ooga.util.config.Parser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ComplexActionParser extends Parser {
  private static final String TXT_FILEPATH = "src/resources/";

  private JSONObject jsonObject;

  public ComplexActionParser(String gameName, String fileName) {
    setMyFileName(TXT_FILEPATH + gameName + "/" + "complexactions/" + fileName + ".json");
    jsonObject = (JSONObject) readJsonFile();
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
        String duration = (String) actionEntry.get("duration");
        Action newAction = null;
        if(duration != null){
          newAction = actionFactory
              .makeAction(actionName, new Class<?>[]{String.class}, new Object[]{paramName, duration});
        } else {
          newAction = actionFactory
              .makeAction(actionName, new Class<?>[]{String.class}, new Object[]{paramName});
        }

        output.add(newAction);
      }
    }
    return output;
  }
}
