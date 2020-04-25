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
  public static final String ACTION_BUNDLES = "actionBundles";
  public static final String CONTROL_FIELD = "Control";
  public static final String PARAM_FIELD = "param";
  public static final String ID_FIELD = "ID";
  public static final String SCHEME_FIELD = "scheme";
  public static final String COLLISION_BUNDLE_FIELD = "collisionBundles";
  public static final String ACTION_FIELD = "action";
  public static final String ORIENTATION_FIELD = "orientation";
  public static final String IMAGE_FIELD = "image";
  public static final String COOLDOWN_FIELD = "coolDown";
  public static final String WIDTH_FIELD = "width";
  public static final String HEIGHT_FIELD = "height";
  public static final String XPOS_FIELD = "xPos";
  public static final String YPOS_FIELD = "yPos";
  public static final String MAX_X_VEL_FIELD = "maxXVel";
  public static final String MAX_Y_VEL_FIELD = "maxYVel";
  public static final String HEALTH_FIELD = "health";
  public static final String FIXED_FIELD = "fixed";
  public static final String PERMEABLE_FIELD = "permeable";
  private String myGame;
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String RESOURCES = "resources/";
  private static final String IMAGE_PACKAGE = "/images/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String CONTROLS_PREFIX = PACKAGE_PREFIX_NAME + "controlschemes.";
  private static final int DEFAULT_DIMENSION = 50;
  private static final int DEFAULT_POSITION = 100;
  private static final int DEFAULT_MAX_VELOCITY = 500;
  private static final int DEFAULT_HEALTH = 10;

  private JSONObject jsonObject;

  public EntityJSONParser(String game, String fileName) {
    setMyFileName(TXT_FILEPATH + game + "/entities/" + fileName + ".json");
    myGame = game;
    jsonObject = (JSONObject) readJsonFile();
  }


  public ControlScheme parseControls() {
    JSONArray actionBundlesArray = (JSONArray) jsonObject.get(ACTION_BUNDLES);
    List<ActionBundle> controlMap = new ArrayList<ActionBundle>();
    String controlType = (String) jsonObject.get(SCHEME_FIELD);

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
    root.put(SCHEME_FIELD, newScheme);
    write2JSON(root, "Successfully Updated Control Scheme!");
  }

  public List<String> updateControls(String param, String newKeyBind, boolean write) {
    List<String> ret = new ArrayList<>();
    JSONObject root = jsonObject;
    JSONArray actionBundlesArray = (JSONArray) jsonObject.get(ACTION_BUNDLES);
    updateActionBundleArray(actionBundlesArray, param, newKeyBind, ret);
    root.put(ACTION_BUNDLES, actionBundlesArray);
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
        if(keybind.getKey().equals(ID_FIELD)){
          ret.add((String) keybind.getValue());
          if(match){
            keybind.setValue(newKeyBind);
            match = false; } }
        match = isMatch(param, ret, match, keybind);
      } }
  }

  private boolean isMatch(String param, List<String> ret, boolean match, Entry keybind) {
    if(keybind.getKey().equals(CONTROL_FIELD)){
      JSONArray controlArray = (JSONArray) keybind.getValue();
      ret.add(controlArray.toString());
      Iterator itr3 = controlArray.iterator();
      while(itr3.hasNext()){
        Iterator<Entry> itr4 = ((Map) itr3.next()).entrySet().iterator();
        while(itr4.hasNext()){
          Entry action = itr4.next();
          if(action.getKey().equals(PARAM_FIELD)){
            if(action.getValue().equals(param)){
              match = true; } } } } }
    return match;
  }

  public Map<CollisionKey, Action> parseCollisions() {
    JSONArray collisionArray = (JSONArray) jsonObject.get(COLLISION_BUNDLE_FIELD);
    Map<CollisionKey, Action> collisionMap = new HashMap<CollisionKey, Action>();

    if(collisionArray != null) {

      for (int i = 0; i < collisionArray.size(); i++) {
        JSONObject collisionEntry = (JSONObject) collisionArray.get(i);
        String key = (String) collisionEntry.get(ID_FIELD);

        JSONArray controlArr = (JSONArray) collisionEntry.get(CONTROL_FIELD);
        for (int j = 0; j < controlArr.size(); j++) {
          JSONObject controlEntry = (JSONObject) controlArr.get(j);
          ActionFactory actionFactory = new ActionFactory();
          String actionName = (String) controlEntry.get(ACTION_FIELD);
          String paramName = (String) controlEntry.get(PARAM_FIELD);
          String orientation = (String) controlEntry.get(ORIENTATION_FIELD);

          Action newAction = actionFactory
              .makeAction(actionName, new Class<?>[]{String.class}, new Object[]{paramName});
          collisionMap.put(new CollisionKey(key, orientation), newAction);
        }
      }
    }
    return collisionMap;

  }

  public ImageView generateImage() {
    String imageName = "missing_texture.png";
    try {
      imageName = (String) jsonObject.get(IMAGE_FIELD);
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, IMAGE_FIELD);
    }
    ImageView output = null;

    output = loadImage(imageName);
    output.setX(Double.parseDouble(jsonObject.get(XPOS_FIELD).toString()));
    output.setY(Double.parseDouble(jsonObject.get(YPOS_FIELD).toString()));
    output.setFitHeight(Double.parseDouble(jsonObject.get(HEIGHT_FIELD).toString()));
    output.setFitWidth(Double.parseDouble(jsonObject.get(WIDTH_FIELD).toString()));
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
    JSONArray controlArray = (JSONArray) bundleElement.get(CONTROL_FIELD);
    outputBundle.setId((String) bundleElement.get(ID_FIELD));
    JSONObject temp = null;

    for(int i = 0; i < controlArray.size(); i++) {
      JSONObject controlArrayEntry = (JSONObject) controlArray.get(i);
      ActionFactory actionFactory = new ActionFactory();

      String paramName = (String) controlArrayEntry.get(PARAM_FIELD);
      String actionName = (String) controlArrayEntry.get(ACTION_FIELD);
      Action newAction;
      if(!controlArrayEntry.containsKey(COOLDOWN_FIELD)) {
        newAction = actionFactory.makeAction(actionName, new Class<?>[]{String.class}, new Object[]{paramName});
      }
      else {
        String coolDown = (String) controlArrayEntry.get(COOLDOWN_FIELD);
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

  public String readImage() { return (String) jsonObject.get(IMAGE_FIELD); }

  public double readWidth() {
    try {
      return Double.parseDouble(jsonObject.get(WIDTH_FIELD).toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, WIDTH_FIELD);
    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, WIDTH_FIELD);
    }
    return DEFAULT_DIMENSION;
  }

  public double readHeight() {
    try {
      return Double.parseDouble(jsonObject.get(HEIGHT_FIELD).toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, HEIGHT_FIELD);
    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, HEIGHT_FIELD);
    }
    return DEFAULT_DIMENSION;
  }

  public double readXPosition() {
    try {
      return Double.parseDouble(jsonObject.get(XPOS_FIELD).toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, XPOS_FIELD);
    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, XPOS_FIELD);
    }
    return DEFAULT_POSITION;
  }

  public double readYPosition(){
    try {
      return Double.parseDouble(jsonObject.get(YPOS_FIELD).toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, YPOS_FIELD);

    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, YPOS_FIELD);
    }
    return DEFAULT_DIMENSION;
  }

  public double readMaxXVelocity(){
    try {
      return Double.parseDouble(jsonObject.get(MAX_X_VEL_FIELD).toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, MAX_X_VEL_FIELD);

    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, MAX_X_VEL_FIELD);
    } return DEFAULT_MAX_VELOCITY;

  }

  public double readMaxYVelocity(){
    try {
      return Double.parseDouble(jsonObject.get(MAX_Y_VEL_FIELD).toString());
    }
    catch (NullPointerException e) {
      throw new ParameterMissingException(e, MAX_Y_VEL_FIELD);

    } catch (NumberFormatException e) {
      throw new ParameterInvalidException(e, MAX_Y_VEL_FIELD);
    } finally {
      return DEFAULT_MAX_VELOCITY;
    }
  }

  public double readHealth() {
    try {
      return Double.parseDouble(jsonObject.get(HEALTH_FIELD).toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, HEALTH_FIELD);

    }
    catch (NumberFormatException e) {
      new ParameterInvalidException(e, HEIGHT_FIELD);
    }
    return DEFAULT_HEALTH;
  }

  public boolean readFixed() {
    try {
      return Boolean.parseBoolean(jsonObject.get(FIXED_FIELD).toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, FIXED_FIELD);
      return false;
    }
  }

  public boolean readPermeable() {
    try {
      return Boolean.parseBoolean(jsonObject.get(PERMEABLE_FIELD).toString());
    }
    catch (NullPointerException e) {
      new ParameterMissingException(e, PERMEABLE_FIELD);
      return false;
    }
  }

  public JSONObject getJSONObject() {
    return jsonObject;
  }
}
