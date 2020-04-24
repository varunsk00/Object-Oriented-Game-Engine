package ooga.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.model.actions.Action;
import ooga.model.actions.ActionFactory;
import ooga.model.actions.CollisionKey;
import ooga.model.controlschemes.ControlScheme;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.json.JSONParser;
//import org.json.simple.parser.ParseException;

public class EntityJSONParser {

  private String myFileName;
  private String myGame;
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String RESOURCES = "resources/";
  private static final String IMAGE_PACKAGE = "/images/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String CONTROLS_PREFIX = PACKAGE_PREFIX_NAME + "controlschemes.";

  private JSONObject jsonObject;

  public EntityJSONParser(String game, String fileName) {
    myFileName = TXT_FILEPATH + game + "/entities/" + fileName + ".json";
    myGame = game;
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

  public ControlScheme parseControls() {
    JSONArray actionBundlesArray = (JSONArray) jsonObject.get("actionBundles");
    List<ActionBundle> controlMap = new ArrayList<ActionBundle>();
    String controlType = (String) jsonObject.get("scheme");

    controlMap = readControlMap(actionBundlesArray);


    Class controlClass = null;
    try{
      controlClass = Class.forName(CONTROLS_PREFIX + controlType);
    } catch (ClassNotFoundException e) {
      //FIXME add error handling
    }

    ControlScheme myScheme = null;

    try{
      myScheme = (ControlScheme) (controlClass.getConstructor(List.class)
          .newInstance(controlMap));
    } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace(); //FIXME to not fail the class
    }
    return myScheme;
  }

  public List<String> updateControls(String param, String newKeyBind, boolean write) { //TODO: REFACTOR
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
        if(keybind.getKey().equals("Control")){
          JSONArray controlArray = (JSONArray) keybind.getValue();
          ret.add(controlArray.toString());
          Iterator itr3 = controlArray.iterator();
          while(itr3.hasNext()){
            Iterator<Map.Entry> itr4 = ((Map) itr3.next()).entrySet().iterator();
            while(itr4.hasNext()){
              Map.Entry action = itr4.next();
              if(action.getKey().equals("param")){
                if(action.getValue().equals(param)){
                  match = true; } } } } } } }
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
    String imageName = (String) jsonObject.get("image");
    ImageView output = null;

    output = loadImage(imageName);
    output.setX(Double.parseDouble(jsonObject.get("xPos").toString()));
    output.setY(Double.parseDouble(jsonObject.get("yPos").toString()));
    output.setFitHeight(Double.parseDouble(jsonObject.get("height").toString()));
    output.setFitWidth(Double.parseDouble(jsonObject.get("width").toString()));
    return output;
  }

  private ImageView loadImage(String imageName) {
//    InputStream is = this.getClass().getClassLoader().getResourceAsStream(RESOURCES + "/"+ imageName);
    InputStream is = this.getClass().getClassLoader().getResourceAsStream(RESOURCES + myGame + IMAGE_PACKAGE + imageName);
    Image entityImage = null;
    entityImage = new Image(is);
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
      e.printStackTrace();//FIXME: TO AVOID FAILING CLASS
    }
  }

  public double readWidth() { return Double.parseDouble(jsonObject.get("width").toString()); }

  public double readHeight() {
    return Double.parseDouble(jsonObject.get("height").toString());
  }

  public double readXPosition() {
    return Double.parseDouble(jsonObject.get("xPos").toString());
  }

  public double readYPosition(){
    return Double.parseDouble(jsonObject.get("yPos").toString());
  }

  public double readMaxXVelocity(){
    return Double.parseDouble(jsonObject.get("maxXVel").toString());
  }

  public double readMaxYVelocity(){
    return Double.parseDouble(jsonObject.get("maxYVel").toString());
  }

  public double readHealth() {
    return Double.parseDouble(jsonObject.get("health").toString());
  }

  public double readXVelMax() {
    return Double.parseDouble(jsonObject.get("maxXVel").toString());
  }

  public double readYVelMax() {
    return Double.parseDouble(jsonObject.get("maxYVel").toString());
  }

  public boolean readFixed() {
    if (jsonObject.get("fixed") != null) {
      return Boolean.parseBoolean(jsonObject.get("fixed").toString());
    }
    return false;
  }

  public boolean readPermeable() {
    if (jsonObject.get("permeable") != null) {
      return Boolean.parseBoolean(jsonObject.get("permeable").toString());
    }
    return false;
  }
}
