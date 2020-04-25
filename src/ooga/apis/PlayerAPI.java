package ooga.apis;

public interface PlayerAPI {

  /**
   * @deprecated: moved to the Config API which handles
   * the saving and loading of game data files or saves.
   * Loads a certain game from a file directory of
   * resources.properties and relevant game data for construction into
   * a functioning game engine
   * @param filename the filepath where the game data and rules are stored
   */
  void loadGame(String filename);

  /**
   * @deprecated: handled by the creation of new EntityWrappers with different
   * types of controlschemes. Creating a player would be like giving an EntityWRapper an IO control
   * scheme such as a keyboard or gamepad.
   * Creates a Player object based on the type of player (that would be specified in an XML file)
   * @param type
   */
  void createPlayer(String type);
}
