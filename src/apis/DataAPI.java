package apis;

public interface DataAPI {

  /**
   *
   * @param filepath
   */
  void loadProperties(String filepath);

  /**
   * Saves the current game state into an XML
   * file. ALlows the user to quit and load a
   * different name.
   */
  void saveState();

  /**
   * Loads a new game state based on an XML
   * file. Constructs a working game engine
   * and
   */
  void loadState();
}
