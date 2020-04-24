package ooga.util;

import org.json.simple.JSONObject;

public class GameStatusProfile {

  private JSONObject gameStatusArray;

  public GameStatusProfile(JSONObject parsedProfile) {
    gameStatusArray = parsedProfile;
  }


  public int readScrollingStatusX() {
    return Integer.parseInt(gameStatusArray.get("scrollingStatusX").toString());
  }

  public int readScrollingStatusY() {
    return Integer.parseInt(gameStatusArray.get("scrollingStatusY").toString());
  }

  public int readSpawningInterval() {
    return Integer.parseInt(gameStatusArray.get("spawningInterval").toString());
  }
}
