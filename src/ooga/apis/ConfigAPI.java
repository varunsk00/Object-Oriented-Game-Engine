package ooga.apis;

import java.io.File;

/**
 * Handles the loading and saving of existing game states with existing
 * entities.
 */
public interface ConfigAPI {
  /**
   * @deprecated: called saveGame() now in config.
   * Saves the current game state into an XML
   * file. ALlows the user to quit and load a
   * different name.
   */
  void saveState();

  /**
   * @deprecated: part of the Game() class,
   * which if given a filepath, will load a
   * game from that file.
   * Loads a new game state based on an XML
   * file. Constructs a working game engine
   * and loads game save data to allow the
   * user to resume playing.
   */
  void loadState(File file);
}
