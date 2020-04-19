package ooga.util;

import java.io.FileReader;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PhysicsProfile {

  private JSONObject physicsArray;

  public PhysicsProfile(JSONObject parsedProfile) {
    physicsArray = parsedProfile;
  }

  public double readFriction(){
    return Double.parseDouble(physicsArray.get("Friction").toString());
  }

  public double readDrag(){
    return Double.parseDouble(physicsArray.get("Drag").toString());
  }

  public double readGravity(){
    return Double.parseDouble(physicsArray.get("Gravity").toString());
  }
}