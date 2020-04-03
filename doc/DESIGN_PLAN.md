# DESIGN_PLAN
Final Team 15
### NetIDs: vsk10, ft39, as866, axo, sk593

## Introduction
The focus of this project will be upon 2D platformer games. These include games such as Mario, Flappy Bird, and Metroid. We will focus on creating an abstract game engine which can handle all sorts of 2D platformers, beginning with infinite sidescrollers, to levelled sidescrollers, and finally, exploration/dungeon platformers.

## Overview
The game will be divided into four main components: View, Model, Controller, and Configuration(Data Management). The four components will contain:

View:
- Controller View: Handles displaying the views and animations. The view will contain a homescreen that players can use to choose their game. Once a game has been chosen, this component will handle displaying the animations. 
    

Model:
- Game Model: The Game Model will handle the implementation of the game logic. This includes having level builders and different engines (i.e. Physics Engine) that execute behaviors for each player. 
    - This class will receive data from the Controller and then pass back updated values based on the action chosen by the user
- Keybinds: This will handle the communication between the computer and the user. The user will interact with the game using Entity objects and the keybinds for the playable character.
    - This creates an interaction between the front end and back end that will be handled through the Controller. Once the Keybindings are set up, the Controller will take in inputs from the user and pass it to the Keybinds so that the entity action can be updated.
- Entity: This class would represent all the different game objects that exist in the game. This includes the playable character, obstacles, terrain, enemies, etc.
    - This would have dependencies on the external View API. It would call on the external View API to update the view and GUI. 
    

Controller: This will be the class that instantiates the necessary objects in a game. It will serve as the mediator between the back end and the front end. It will take in requests from the front end and then pass the information to the back end to update the model. Then it will take the updates from the front end and return the view that should be rendered. 

Configuration:
-  XML Reader: Reads in the XML file data 
-  XML Writer: This will update data files as the game values get updated. For example, if the damage of a player increases, this will value be updated. 
- Parsers: This will handle parsing through the XML files and assigning data values to actual classes contained in them.
- XML Exception: Will create XML exceptions to be used/thrown if XML files are populated incorrectly. 
    

## Design Details
View:
- Controller View
    - Game Cabinet: The Game Cabinet is the home screen of the application. Users will be able to choose their game type, and load or save their game.
- Gameplay View
    - EntityView: Entity View is the graphical representation of each entity object. This class will contain data about how the entity should look (in the form of images or ImageViews) and it will update the GUI animations based on the needed actions
    - Game Level View: This will be the graphical representation of the level. It will contain graphics for any non-playable characters, backgrounds of the levels, etc. It will likely also interact with EntityView so that each entity that the user cannot use will be displayed in this Game Level View
    
        
Model:
- Game Model:  
    - Level Engine: The Level Engine handles building the physical level based on the XML configuration file. It will use a parser in order to read the XML files and translate that into the level config. 
    - Physics Engine: This physics engine would define how the different Entities would react to things in the environment and inherent constants in the game, such as gravity. This class would be the class where calculations go into determining the end position of different Entities. This would mean that we would apply the physics logic to every Entity object, along with the timeline elapsed time, in order to have accurate movement of Entity objects, like the character, enemies, flying projectiles, etc.
- Keybinds: 
    - Local Player: uses WASD to move
    - Co-Op Player: uses arrow keys to move
    - Online Player: uses WASD and interact with network 
- Entity: Every game object in the game will be considered as an Entity object. This is because of the various similarities that exist in anything that actually exists in the game, such as the playable character, enemies, and inanimate objects. These similarities are seen as the following:
    - General Characteristics: Every Entity object has a general set of characteristics that they need to be displayed in the game. This includes things like an images, a width, a height, etc.
    - Movement: There are three main types of movements that an Entity can have: user-controlled movement, AI movement, and inanimate objects. This specification of type of movement would be defined by the properties file. If a Entity is definted to have user-controlled movement, a set of keybindings will be assigned so that the player can directly control the Entity. For enemies and objects that move, a set of pre-defined movement scheme will be generated for the specific enemy, and for inanimate object, they do not move as part of the background.
    - Collision Triggers: All the entities that exist in the game will have some sort of cause-and-effect as a result of a collision with other entities in the game. For example, if a "Fireball" entity collides with something, the "Fireball" disappears and will cause damage depending on if the something was an enemy. As all entities have some sort of collision effect, the Entity class will have collisions as a innate part of its structure.


Controller: The Controller will handle all interactions between the front end and the back end. It will take user inputs and pass them to the back end so that the game enginer can update based on the game logic. The updates will be passed back to the front end so that the view can be updated. This interaction will happen using external APIs from the front end and back end.
- ViewController: This will be a sub-controller than handles the front end side of integrating the project. This class will implement a front end external API and then be used by a main controller.
- ModelController: Like the front end, this sub-controller will handle the back end side communication. This will be implemented by the main controller.
    - Note: The Main Controller will instantiate both these controllers and will use them to interact with both sides and pass through values/resources.

Configuration:
- XML Reader: Reads in the XML file data 
    - XML files will contain data about different objects of the game (i.e. fireball will contain data about duration, speed, damage; flappy bird tubes will contain data about length, image)
- XML Writer: This will update data files as the game values get updated. For example, if the damage of a player increases, this will value be updated. 
- Parsers: This will handle parsing through the XML files and assigning data values to actual classes contained in them.
    - Game State Parser: This parser will handle reading in data from a config file about level set up and game states (ie number of lives, power ups, etc). Game State Parser will pass through data to the Level Builder and Game Engine so that the starting configuration for each level or new game is rendered/update  
    - Entity Parser: The Entity parser will handle reading in data about each entity and its different behaviors. The entity behaviors are the biggest part of this. Each entity will have it's own set of behaviors so this class will be responsible for updating the data and variables in the Entity Model 
- XML Exception: Will create XML exceptions to be used/thrown if XML files are populated incorrectly. 
        
        

## Example Games
We chose to implement the Scrolling Platformer genre in this final project. This genre of games is defined as being viewed
from the side and having the player progress through the game's level(s) by 
moving a playable character on the screen, often through obstacles and terrain. This genre of games often has a variety of different features for different games, such as enemies, attacks, power-ups, and even boss fights, but we found the core commonalities between all the different types of games in the genre to be the following:

1. Playable characters - These characters are controlled by the player to avoid obstacles and other features to prevent the character from dying. Characters must be able to move, have health, and interact when colliding with objects in the game.
2. Obstacles and Terrain - Every scrolling platformer game needs to have terrain and obstacles that the playable character must traverse through in order to progress in the game
3. End Goal - Each scrolling platformer game has an end goal that the player is trying to achieve. This end goal can be to defeat a boss, clear through all the levels, or just trying to survive as long as possible. 

### Example 1 - Infinite Side-Scroller
* In this example, we have an infinite side-scroller game where players can only travel in one direction and the objective is to get as far as possible in the game. This genre of side-scroller includes games like Flappy Bird, Jetpack Joyride, Google Dino Run, etc. In this genre of games, the most common objective is trying to achieve a high score by surviving as long as possible in the game by avoiding obstacles/enemies. Points are generally awarded for how much time a player has survived in the game or for the amount of distance that they traveled in the side-scroller. 

### Example 2 - 1D Leveled Side-Scroller
* Our definition of a 1D leveled side-scroller entails a linearized game progression wherin the player moves mainly in the x direction with little y movement of the camera and a defined end goal. There should be little need for backtracing through the level as the goal is to generally reach the end by traversing obstacles. Games in this genre include the eariler iterations of Super Mario Bros, Sonic the Hedgehog, Duke Nukem, etc.

### Example 3 - 2D Leveled Side-Scroller
* Our 2D leveled side-scroller would incorporate a more nonlinearized design, allowing the player exploration in both the x and y directions, and takes inspiration from the metroidvania subgenre. In this case, the map is configured specifically to encourage exploration in order to progress and eventually reach the end goal. This can include features such as inaccessible areas, new items, etc. Games in this genre include Super Metroid, Castlevania, Hollow Knight, etc.

### Flexibilty
With these different game types in the scrolling platformer genre, there are a lot of features that are functionally different from each other in the different games. For example, in some games, there are computer enemies in addition to the game's obstacles, or there are levels instead of an infinite game plane. Our design is able to handle these differences as described below:
* Infinite vs. Finite Game Plane - From the three different game types, we can see that the level design for all three examples differ from one and another. Our design will be able to handle this by taking in a properties file that makes the game's levels and indicate the boundaries set for the level. 
    * For an infinite side scroller, this would entail bounding off the y-component of the level to a fixed value and making the x-component as infinite. This properties file would then indicate to the level builder that the view of the game is fixed on the y-bounds, but new obstacles have to be generated as the player progresses in the x-direction.
    * For the 1D and 2D Leveled Platformer, the same properties file would then have the specific bounds for the x- and the y- components of the level. Whereas the 1D Leveled platformer would have a bound y-component that would not allow the screen to move up (fixing the camera as to not go above the y-bound to fix the game as one-dimensional), the 2D leveled version would have a y-component that is larger than the game screen, allowing the player to move up, moving the camera view with the character. So, this difference is defined with setting the bounds of the game's levels and how the camera moves with the character.

* Enemy and Combat System - In some of these games, there are enemies and a combat system in place. This combat system is not found in some games, but this is not an issue because we are using an Entity Object that encapsulates every object found in the game, including enemies. So, while every game has Entity objects representing things like the Player, obstacles, and terrain, not every game will have entities that will represent enemies. This Entity object is created by passing in a properties file that will designate the characteristics of that Entity. This way, we can easily not have a combat system in a game like Flappy Bird but have a system implemented for Mario. 

## Design Considerations
* Hierarchy of Entity actions (discussed 3 options of where we wanted individual player actions to be executed and how we would handle passing the data through)
    *  XML tags
        *  This option would entail defining actual Java code in the XML file with a tag that specified the action. This way, each config file would match a specific entity and then it's actions (the physical code) would be defined in the file. 
    *  Action Factories
        *  The XML files would contain info on how to build each action (ie by providing duration, damage, or other features). Each feature is then parsed and built using an action factory 
    *  Psuedocode
        *  Kind of like the SLogo program but we would pass through pseudocode in an XML tag. Then similar to SLogo, we would use the pseudocode to build actions
    * Ended up choosing actions factories because we can use reflection to determine which action needs to be implemented (this would be specified in an XML tag). We also avoid having to translate XML code/pseudocode into Java code  

* ViewExternal API (how to handle updating each entity)
    *   One update method that all entities would use
        *   This implementation allows for all of the common API that entities would need to be contained in one class that manages all entities. While the functionality would be essentially the same as creating an interface that the Entity class would override, this iteration would keep Entity from having access to API that the program needs, but an actual in-game entity may not, such as spawn or die. Would also contribute to refactoring the controller class.
    *   An interface that each entity would override depending on it's needs
        *   The Entity class overrides methods in an interface such as addListener, updateCoordinates, etc. Allows for fewer nested calls as there is no need for an extra class to manage views as well as other program needs (spawning vs updating coordinates). 
*   Model View dichotomy in reference to entities
    *  Wrapper class
        *  Holds both entity model and view implementations allowing for simple communication, but potentially obfuscating the distinction between model and view 
    *  Unique IDs
        *  Applies a unique ID to each entity allowing for specificity of interaction and maintaining distinction between model and view

## Team Responsibilities
* In planning this project, we felt a majority of the early work would occur in the back end of the project, as we developed our abstract game engine and properties hierarchies. Later on, the work will definitely become more and more frontend-oriented as we start plugging using our game engine to create working games within a flexible UI. For these reasons, the roles we assign here are highly-backend focused, with the expectation that team members transition to front end jobs as they finish their work.
#### Alex
* Backend
    * Entity class design and its composition architecture
        * Necessary composition classes (control scheme)
    * Entity Model/View setup
#### Shruthi
* Primary Controller:
    * View-Model interactions
* Secondary Backend:
    * Creating action factories + interfaces
* Configuration:
    * Game State Configuration 
    * Game State/ Level Parser
     
#### Frank
* Primarily backend:
    * Managing the physics and game logic that occrs in our program
    * XML to Entity object creation
    * Helping with determining how Entity behaviors are made and interact with the rest of the project.

#### Abhijay
* Frontend
    * GUI (HUD, stats, etc), Entity visuals, Level visuals
* Backend
    * Entity properties parsing

#### Varun
* Primary: Front-end
    * View-Model Interactions
    * User-Available configuration (GUI Settings)
    * Assembly of views (Front-End/Controller interaction)
* Secondary: Controller
    * Facilitating model-view dependencies
    * Asset Loading and View
* Happy to Help in the early stages: Key-bindings and Actions to be executed on **Entity** objects