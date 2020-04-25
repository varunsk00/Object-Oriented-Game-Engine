package ooga.util;

import com.thoughtworks.xstream.mapper.Mapper.Null;
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
  private final static int DEFAULT_ZERO = 0;

  public PhysicsProfile(JSONObject parsedProfile) {
    physicsArray = parsedProfile;
  }

  public double readFriction() {
    try {
      return Double.parseDouble(physicsArray.get("Friction").toString());
    } catch (NullPointerException e) {
      return DEFAULT_ZERO;
    }
  }

  public double readDrag() {
    try {
      return Double.parseDouble(physicsArray.get("Drag").toString());
    } catch (NullPointerException e) {
      return DEFAULT_ZERO;
    }
  }

  public double readGravity() {
    try {
      return Double.parseDouble(physicsArray.get("Gravity").toString());
    } catch (NullPointerException e) {
      return DEFAULT_ZERO;
    }
  }
}