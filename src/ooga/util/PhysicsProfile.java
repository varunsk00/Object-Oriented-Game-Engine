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
import ooga.util.config.ParameterMissingException;
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
    String friction = "Friction";
    try {
      return Integer.parseInt(physicsArray.get(friction).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, friction);
      return DEFAULT_ZERO;
    }
  }

  public double readDrag() {
    String drag = "Drag";
    try {
      return Integer.parseInt(physicsArray.get(drag).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, drag);
      return DEFAULT_ZERO;
    }
  }

  public double readGravity() {
    String gravity = "Gravity";
    try {
      return Integer.parseInt(physicsArray.get(gravity).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, gravity);
      return DEFAULT_ZERO;
    }
  }
}