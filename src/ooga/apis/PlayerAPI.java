package ooga.apis;

public interface PlayerAPI {

  /**
   * Loads a certain game from a file directory of
   * resources.properties and relevant game data for construction into
   * a functioning game engine
   * @param filename the filepath where the game data and rules are stored
   */
  void loadGame(String filename);
}
