package apis;

import javax.swing.text.html.parser.Entity;

public interface DataAPI {

  /**
   * Creates an entity of the specified type and returns it for
   * the Game Engine to use in its game.
   * @param entityName - the name of the entity to be created corresponding
   *                   to the name in the game data properties files
   */
  Entity createEntity(String entityName);
}
