package ooga.util;


import ooga.util.config.ParameterMissingException;
import org.json.simple.JSONObject;

public class PhysicsProfile {

  private JSONObject physicsArray;
  private final static int DEFAULT_ZERO = 0;

  public PhysicsProfile(JSONObject parsedProfile) {
    physicsArray = parsedProfile;
  }

  public double readFriction() {
    String friction = "friction";
    try {
      return Integer.parseInt(physicsArray.get(friction).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, friction);
      return DEFAULT_ZERO;
    }
  }

  public double readDrag() {
    String drag = "drag";
    try {
      return Integer.parseInt(physicsArray.get(drag).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, drag);
      return DEFAULT_ZERO;
    }
  }

  public double readGravity() {
    String gravity = "gravity";
    try {
      return Integer.parseInt(physicsArray.get(gravity).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, gravity);
      return DEFAULT_ZERO;
    }
  }
}