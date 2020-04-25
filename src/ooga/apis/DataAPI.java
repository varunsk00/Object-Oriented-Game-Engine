package ooga.apis;

import javax.swing.text.html.parser.Entity;

/**
 * Handles the parsing of game data files to create
 * unique games using a data-driven strategy.
 */
public interface DataAPI {

  /**
   * @deprecated: no validation scheme was able to be written for JSON files
   * because of the group's inexperience with JSON. Instead we use exception handling
   * and provide default cases for our properties.
   * Validates game data files to ensure that they can create valid
   * game entities for the game.
   * @param fileName - the directory containing all game data properties files
   */
  void validateProperties(String fileName);

  /**
   * @deprecated: this method has been split into many individual data-parsing
   * methods such as parsePosition(), parseHealth(), and more. In addition,
   * instead of the Data API creating entities, created entities reach out
   * to the Data API and ask it for information to populate its stats with.
   * Creates an entity of the specified type and returns it for
   * the Game Engine to use in its game.
   * @param entityName - the name of the entity to be created corresponding
   *                   to the name in the game data properties files
   */
  Entity createEntity(String entityName);
}
