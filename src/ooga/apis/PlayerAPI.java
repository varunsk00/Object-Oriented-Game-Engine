package ooga.apis;

public interface PlayerAPI {

  /**
   * Loads a certain game from a file directory of
   * resources.properties and relevant game data for construction into
   * a functioning game engine
   * @param filename the filepath where the game data and rules are stored
   */
  void loadGame(String filename);

  /**
   * Creates a Player object based on the type of player (that would be specified in an XML file)
   * @param type
   */
  void createPlayer(String type);
}
