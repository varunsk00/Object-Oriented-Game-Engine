package ooga.util;

import ooga.exceptions.ParameterMissingException;
import org.json.simple.JSONObject;

public class GameStatusProfile {

  private JSONObject gameStatusArray;
  private static final int DEFAULT_ZERO = 0;
  private static final int DEFAULT_ONE = 1;

  public GameStatusProfile(JSONObject parsedProfile) {
    gameStatusArray = parsedProfile;
  }


  public int readScrollingStatusX() {
    String scrollingStatusX = "scrollingStatusX";
    try {
      return Integer.parseInt(gameStatusArray.get(scrollingStatusX).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, scrollingStatusX);
      return DEFAULT_ONE;
    }
  }

  public int readScrollingStatusY() {
    String scrollingStatusY = "scrollingStatusY";
    try {
      return Integer.parseInt(gameStatusArray.get(scrollingStatusY).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, scrollingStatusY);
      return DEFAULT_ONE;
    }
  }

  public int readSpawningInterval() {
    String spawningInterval = "spawningInterval";
    try {
      return Integer.parseInt(gameStatusArray.get(spawningInterval).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, spawningInterval);
      return DEFAULT_ZERO;
    }
  }

  public int readLevelSpawnOffset() {
    String levelSpawnOffset = "levelSpawnOffset";
    try {
      return Integer.parseInt(gameStatusArray.get(levelSpawnOffset).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, levelSpawnOffset);
      return DEFAULT_ZERO;
    }
  }

  public int readStartingLevelIndex() {
    String startingLevelIndex = "startingLevelIndex";
    try {
      return Integer.parseInt(gameStatusArray.get(startingLevelIndex).toString());
    } catch (NullPointerException e) {
      new ParameterMissingException(e, startingLevelIndex);
      return DEFAULT_ZERO;
    }
  }
}
