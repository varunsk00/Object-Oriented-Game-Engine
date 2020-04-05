package ooga.apis;

import javax.swing.text.html.parser.Entity;

/**
 * Handles the parsing of game data files to create
 * unique games using a data-driven strategy.
 */
public interface DataAPI {

  /**
   * Validates game data files to ensure that they can create valid
   * game entities for the game.
   * @param fileName - the directory containing all game data properties files
   */
  void validateProperties(String fileName);

  /**
   * Creates an entity of the specified type and returns it for
   * the Game Engine to use in its game.
   * @param entityName - the name of the entity to be created corresponding
   *                   to the name in the game data properties files
   */
  Entity createEntity(String entityName);
}
