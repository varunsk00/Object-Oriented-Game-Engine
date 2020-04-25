package ooga.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import java.util.Map.Entry;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.model.actions.Action;
import ooga.model.actions.ActionFactory;
import ooga.model.CollisionKey;
import ooga.model.controlschemes.ControlScheme;
import ooga.exceptions.ParameterInvalidException;
import ooga.exceptions.ParameterMissingException;
import ooga.util.config.Parser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.JSONParser;
//import org.json.simple.parser.ParseException;

public class EntityJSONParser extends Parser {

  private String myGame;
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String RESOURCES = "resources/";
  private static final String IMAGE_PACKAGE = "/images/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String CONTROLS_PREFIX = PACKAGE_PREFIX_NAME + "controlschemes.";

  private JSONObject jsonObject;

  public EntityJSONParser(String game, String fileName) {
    setMyFileName(TXT_FILEPATH + game + "/entities/" + fileName + ".json");
    myGame = game;
    jsonObject = (JSONObject) readJsonFile();
  }


  public ControlScheme parseControls() {
    JSONArray actionBundlesArray = (JSONArray) jsonObject.get("actionBundles");
    List<ActionBundle> controlMap = new ArrayList<ActionBundle>();
    String controlType = (String) jsonObject.get("scheme");

    controlMap = readControlMap(actionBundlesArray);

    Class controlClass = null;
    try{
      controlClass = Class.forName(CONTROLS_PREFIX + controlType);
    } catch (ClassNotFoundException | NullPointerException e) {
      new ParameterMissingException(e, controlClass.toString());
    }

    ControlScheme myScheme = null;

    try{
      myScheme = (ControlScheme) (controlClass.getConstructor(List.class)
          .newInstance(controlMap));
    } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      new ParameterMissingException(e, controlClass.toString());
    }
    return myScheme;
  }

  public void updateControlScheme(String newScheme) {
    JSONObject root = jsonObject;
    root.put("scheme", newScheme);
    write2JSON(root, "Successfully Updated Control Scheme!");
  }

  public List<String> updateControls(String param, String newKeyBind, boolean write) {
    List<String> ret = new ArrayList<>();
    JSONObject root = jsonObject;
    JSONArray actionBundlesArray = (JSONArray) jsonObject.get("actionBundles");
    updateActionBundleArray(actionBundlesArray, param, newKeyBind, ret);
    root.put("actionBundles", actionBundlesArray);
    if(write){
      write2JSON(root, "Successfully Updated Control Scheme!"); }
    return ret;
  }

  private void updateActionBundleArray(JSONArray ja, String param, String newKeyBind, List<String> ret){
    Iterator itr2 = ja.iterator();
    boolean match = false;
    while (itr2.hasNext()){
      Iterator<Map.Entry> itr1 = ((Map) itr2.next()).entrySet().iterator();
      while (itr1.hasNext()) {
        Map.Entry keybind = itr1.next();
        if(keybind.getKey().equals("ID")){
          ret.add((String) keybind.getValue());
          if(match){
            keybind.setValue(newKeyBind);
            match = false; } }
        match = isMatch(param, ret, match, keybind);
      } }
  }

  private boolean isMatch(String param, List<String> ret, boolean match, Entry keybind) {
    if(keybind.getKey().equals("Control")){
      JSONArray controlArray = (JSONArray) keybind.getValue();
      ret.add(controlArray.toString());
      Iterator itr3 = controlArray.iterator();
      while(itr3.hasNext()){
        Iterator<Entry> itr4 = ((Map) itr3.next()).entrySet().iterator();
        while(itr4.hasNext()){
          Entry action = itr4.next();
          if(action.getKey().equals("param")){
            if(action.getValue().equals(param)){
              match = true; } } } } }
    return match;
  }

  public Map<CollisionKey, Action> parseCollisions() {
    JSONArray collisionArray = (JSONArray) jsonObject.get("collisionBundles");
    Map<CollisionKey, Action> collisionMap = new HashMap<CollisionKey, Action>();

    if(collisionArray != null) {

      for (int i = 0; i < collisionArray.size(); i++) {
        JSONObject collisionEntry = (JSONObject) collisionArray.get(i);
        String key = (String) collisionEntry.get("ID");

        JSONArray controlArr = (JSONArray) collisionEntry.get("Control");
        for (int j = 0; j < controlArr.size(); j++) {
          JSONObject controlEntry = (JSONObject) controlArr.get(j);
          ActionFactory actionFactory = new ActionFactory();
          String actionName = (String) controlEntry.get("action");
          String paramName = (String) controlEntry.get("param");
          String orientation = (String) controlEntry.get("orientation");

          Action newAction = actionFactory
              .makeAction(actionName, new Class<?>[]{String.class}, new Object[]{paramName});
          collisionMap.put(new CollisionKey(key, orientation), newAction);
        }
      }
    }
    return collisionMap;

  }

  public ImageView generateImage() {
    String imageName = "missing_texture.jpg";
    try {
      imageName = (String) jsonObject.get("image");
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "image");
    }
    ImageView output = null;

    output = loadImage(imageName);
    output.setX(Double.parseDouble(jsonObject.get("xPos").toString()));
    output.setY(Double.parseDouble(jsonObject.get("yPos").toString()));
    output.setFitHeight(Double.parseDouble(jsonObject.get("height").toString()));
    output.setFitWidth(Double.parseDouble(jsonObject.get("width").toString()));
    return output;
  }

  private ImageView loadImage(String imageName) {
    InputStream is;
    Image entityImage = null;
    try {
       is = this.getClass().getClassLoader().getResourceAsStream(RESOURCES + myGame + IMAGE_PACKAGE + imageName);
      entityImage = new Image(is);
    }
    catch (NullPointerException e) {
      new ParameterInvalidException(e, "imageName");
      entityImage = new Image(RESOURCES + imageName);
    }
    return new ImageView(entityImage);
  }

  private List<ActionBundle> readControlMap(JSONArray actionBundlesArray) {
    List<ActionBundle> controlMap = new ArrayList<ActionBundle>();
    for(int i = 0; i < actionBundlesArray.size(); i++) {
      controlMap.add(readControls((JSONObject) actionBundlesArray.get(i)));
    }
    return controlMap;
  }

  private ActionBundle readControls(JSONObject bundleElement) {
    ActionBundle outputBundle = new ActionBundle();
    JSONArray controlArray = (JSONArray) bundleElement.get("Control");
    outputBundle.setId((String) bundleElement.get("ID"));
    JSONObject temp = null;

    for(int i = 0; i < controlArray.size(); i++) {
      JSONObject controlArrayEntry = (JSONObject) controlArray.get(i);
      ActionFactory actionFactory = new ActionFactory();

      String paramName = (String) controlArrayEntry.get("param");
      String actionName = (String) controlArrayEntry.get("action");
      Action newAction;
      if(!controlArrayEntry.containsKey("coolDown")) {
        newAction = actionFactory.makeAction(actionName, new Class<?>[]{String.class}, new Object[]{paramName});
      }
      else {
        String coolDown = (String) controlArrayEntry.get("coolDown");
        newAction = actionFactory.makeAction(actionName, new Class<?>[]{String.class, String.class}, new Object[]{paramName, coolDown});
      }
      outputBundle.addAction(newAction);
    }
    return outputBundle;
  }

  private void write2JSON(JSONObject root, String message){
    try (FileWriter file = new FileWriter(myFileName, false))
    {
      file.write(root.toString());
      System.out.println(message);
    } catch (IOException e) {
      new ParameterInvalidException(e, root.toString());
    }
  }

//<<<<<<< HEAD
  public String readImage() { return (String) jsonObject.get("image"); }

  public double readWidth() {
    try {
      return Double.parseDouble(jsonObject.get("width").toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "width");
    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, "width");
    }
    return 50; //default
  }
//>>>>>>> c3c36ba4b00f396e0125d39bd184a271aa88ceb6

  public double readHeight() {
    try {
      return Double.parseDouble(jsonObject.get("height").toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "height");
    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, "height");
    }
    return 50; //default
  }

  public double readXPosition() {
    try {
      return Double.parseDouble(jsonObject.get("xPos").toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "xPos");
    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, "xPos");
    }
    return 100;
  }

  public double readYPosition(){
    try {
      return Double.parseDouble(jsonObject.get("yPos").toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "yPos");

    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, "yPos");
    }
    return 100;
  }

  public double readMaxXVelocity(){
    try {
      return Double.parseDouble(jsonObject.get("maxXVel").toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "maxXVel");

    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, "maxXVel");
    } return 500;

  }

  public double readMaxYVelocity(){
    try {
      return Double.parseDouble(jsonObject.get("maxYVel").toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "maxYVel");

    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, "maxYVel");
    }
    return 500;
  }

//<<<<<<< HEAD
//=======
  public double readHealth() {
    try {
      return Double.parseDouble(jsonObject.get("health").toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "health");

    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, "health");
    }
    return 10;
  }

  public boolean readFixed() {
    try {
      return Boolean.parseBoolean(jsonObject.get("fixed").toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "fixed");
      return false;
    }
  }

  public boolean readPermeable() {
    try {
      return Boolean.parseBoolean(jsonObject.get("permeable").toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, "permeable");
      return false;
    }
  }

  public JSONObject getJSONObject() {
    return jsonObject;
  }
}
