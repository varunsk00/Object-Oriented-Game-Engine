package ooga.util;


import ooga.exceptions.ParameterMissingException;
import org.json.simple.JSONObject;

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