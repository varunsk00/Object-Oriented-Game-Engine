package ooga.util;

import org.json.simple.JSONObject;

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