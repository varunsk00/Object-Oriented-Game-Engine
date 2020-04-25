package ooga.util;

import org.json.simple.JSONObject;

public class GameStatusProfile {

  private JSONObject gameStatusArray;
  private static final int DEFAULT_ZERO = 0;
  private static final int DEFAULT_ONE = 1;

  public GameStatusProfile(JSONObject parsedProfile) {
    gameStatusArray = parsedProfile;
  }


  public int readScrollingStatusX() {
    try {
      return Integer.parseInt(gameStatusArray.get("scrollingStatusX").toString());
    } catch (NullPointerException e) {
      return DEFAULT_ONE;
    }
  }

  public int readScrollingStatusY() {
    try {
      return Integer.parseInt(gameStatusArray.get("scrollingStatusY").toString());
    } catch (NullPointerException e) {
      return DEFAULT_ONE;
    }
  }

  public int readSpawningInterval() {
    try {
      return Integer.parseInt(gameStatusArray.get("spawningInterval").toString());
    } catch (NullPointerException e) {
      return DEFAULT_ZERO;
    }
  }

  public int readLevelSpawnOffset() {
    return Integer.parseInt(gameStatusArray.get("levelSpawnOffset").toString());
  } catch (NullPointerException e) {
    return DEFAULT_ZERO;
  }
  }

  public int readStartingLevelIndex() {
    return Integer.parseInt(gameStatusArray.get("startingLevelIndex").toString());
  } catch (NullPointerException e) {
    return DEFAULT_ZERO;
    }
  }

}
