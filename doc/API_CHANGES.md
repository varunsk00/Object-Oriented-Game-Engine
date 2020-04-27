###API Changes:
Alex Oesterling, Shruthi Kumar, Frank Tang, Abhijay Suhag, Varun Kosgi

#####Model:
* The ModelExternal API is almost completely deprecated. The reason for this is we expected our model to
handle things such as selecting games and tracking a running list of Entities, however, we actually
put a lot of this functionality in our controller: There is a higher-up application which selects a game
and then creates the new MVC architecture for that game: inside the MVC is much less functionality than 
we had expected.
* Methods changed:
    * ```addEntity()``` -- Now contained within the MainController class, called by Model when it wants to spawn new entities
    * ```setUpGameModel()``` -- Game selection logic is now contained within the StageManager, AVManager, and Game classes which
    display a cycling visual of games based on data files and then creates new Game() objects with the correct
    game specified in the constructor.
    * ```sendUserData()``` -- This method was a generic data transfer method to cover necessary communication between the front
    end and back end. At this point, it has been replaced by a multitude of methods, most primarily the ```EntityWrapper.getModel()```
    method which allows the view to access necessary data such as position, dimension, and velocity for each entity.
    * ```collide()``` -- This method has been renamed ```produceCollisions()``` in our project, and does the same thing.
    the reason for not continuing the use the ```collide()``` method is that this change was called external to model
    actually -- our team started calling produceCollisions() rather than someone renaming collide() beforehand.
* The ModelInternal API is almost completely deprecated as well. There is not much to say as I will explain what we did in the 
following bullet:
* Methods changed:
    * ```getCurrentAction()``` -- This method would be called on entities to ask what
    their current action was. However, many times, the entities have multiple actions acting on them.
    We have switched to a "stack" of actions. Each entity calls their entire stack of actions to 
    execute on themselves each frame.
    * ```execute()``` -- this method still persists in spirit in our code, in the Action inheritance
    hierarchy. Each action has an execute method which takes in an entitymodel. This is the entity
    on which it operates, changing states such as position, velocity, image rendering, boundary conditions,
    and more.
    
#####View:
* The ViewExternal API did not change a lot, although its methods took in more generalized
EntityWrappers instead of JavaFX Nodes. The reason for this is that we wanted to remove the objects
from our tracking (our list of active entities) and not just despawn their renderings or delete
their renderings. For example, removing the rendering of a character may cause the desired effect
on the user side, but if the object is still being iterated over for when checking collisions and
actions, it could still affect the scene as and invisible object, spawning fireballs or colliding
with enemies.
* Methods changed:
    * ```removeEntity()``` -- This method remained the same, and just took in an EntityWrapper instead of a Node
    * ```addEntity()``` -- This method was refactored to be called spawnEntity() and took in an EntityWrapper
    instead of a Node. Although this violates the contract of the API, it was changed by those calling on the API
    rather than by the writers of the API, which means they were expecting and requesting this change.
* The ViewInternal API remained the same, except that it also took in a boolean representing what direction
the entity was facing. The reason for this is that certain entities needed to spawn forwards or backwards depending
on the orientation of their spawner (think an archer shooting an arrow forwards or backwards). For this reason,
each Entity had a direction, and so an arrow would move with an "Absolute Velocity" and a direction determined
by its "facing forwards" boolean.
* Methods changed:
    * ```update()``` -- remained the same, even in functionality, but added an additional boolean parameter
    specifying the direction of propagation.
    
#####Additional APIS:
* The Config API turned out to be much smaller than needed: as our project became more and more data driven, more and 
more methods were required for parsing through this data. However, the main principle of saving and loading games remained the same.
    * ```saveState()``` -- This method was refactored to be called saveGame, but operates the exact same: it saves the data of the game
    into a file.
    * ```loadGame()``` -- This method was deleted and rolled into the generic startup method for a game in the Game and StageManager classes: if this
    method received a filepath as a parameter, it would go read a data file for a save state. If not, it would
    read the default save state file (ie. a new game).
    
* The Data API, in combination with the Config API, took on a large burden of parsing files to create the data
driven aspects of our projects. These methods were generally split into multiple parts: instead of parsing
an Entity, the Data API would parse health, position, velocity, and collisions separately to allow for
more modular design.
    * ```validateProperties()``` -- this method was never used and instead we handle errors directly in the
    parser and display errors if certain properties are missing. This comes more from a lack of knowledge with 
    validating JSON data files: Although team members had skills with XML files, we felt that trying JSON would
    be a good way to challenge ourselves and expand our knowledge. Here, however, it prevented us from
    adding an additional program failsafe.
    * ```createEntity()``` -- This method was essentially split into a multitude of parsing methods which
    parse each unique aspect of the entity. Furthermore, instead of calling on the parser to spawn an entity,
    a newly-instantiated EntityWrapper will create its own instance of a parser and populate its data values
    from the parser using its many parsing methods.
* The Engine API, although included in our submission of our plan, was insubstantial and so was deleted.
However, we created two engine classes, one to handle the collision between entities and one to apply
physical forces to entities. Both of these entities were heavily used and included methods such as ```applyGravity()```,
and ```produceCollisionActions()``` which would take in entities and operate on them.
* The Player API was something we envisioned similar to the Hangman lab we did in class where there were different
types of players and executioners. However, the player eventually became simplified to a controlscheme: each entity
has a control scheme (using composition), and different players were specified based on their
different control schemes such as keybindings or controller handling.
    * ```loadGame()``` -- this method was extracted and moved to the Config API because it made more sense for the Config
    to handle the saving and loading of game data files.
    * ```createPlayer()``` -- this method was removed because in our architecture, creating an entity is like
    creating a player: however, most entities aren't controlled by actual people unless you specify their
    controlscheme to be a scheme such as Keyboard input or GamePad input. 