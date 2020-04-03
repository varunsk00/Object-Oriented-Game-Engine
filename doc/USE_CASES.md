# USE_CASES
Final Team 15
### NetIDs: vsk10, ft39, as866, axo, sk593

### Use Case 1
Move the Player to the right using the "D" key.
1. **KeyBinds** class receives the keyboard input of "D"
2. Based on an XML file that associates key input of "D" with a specific Action (for example ForwardAction), the **Entity** class creates a map from KeyPresses (String) to **Action** objects.
3. **Entity.java** calls a method of **KeyBinds** within its *updateEntity()* method that returns the currently pressed key bind as an **Action** object and executes the change to the model accordingly.

### Use Case 2
The User selects a Game from the Game Select Screen
1. The **GameCabinet** class passes the currently selected game (as a String) into the primary **Controller** class via a method call within the frame step, *Controller.sendCurrentGame()*
2. The **Controller** class assembles all the front-end assets and back-end game logic for the currently selected game.

### Use Case 3
The User selects 2-Players in the Game's title screen
1. The **Controller** class instantiates two **Avatar** objects, as opposed to just 1. The second **Avatar** object is instantiated with the Player2.xml file to have the appropriate keybinds.

### Use Case 4
The User goes Home from a current game by pressing "H"
1. **KeyBinds** class receives the keyboard input of "H"
2. The **GameLibrary** class's *goHome()* method is invoked within the **Controller** class, which displays the Game Select screen over the game.

### Use Case 5
The User changes the Control Configuration/Binding within the GUI

### Use Case 6
The Avatar can break a brick via collision from the bottom
1. **Controller** class loops through the EntityList of the game
2. If the Entity object representing the Avatar and a Brick are colliding, we go to the collisionMap to see what action needs to be taken
3. With the Avatar colliding from the bottom of the Brick, the Brick is destroyed. We would differentiate this for if we are above or next to the brick, as colliding with the brick from the top or side will only result in preventing the Avatar from clipping through the Brick.

### Use Case 7
The Player gains a powerup that doubles their size

### Use Case 8
The Player hits a checkpoint flag, causing the next stage of the level to intiate
1. The state of the Flag **Entity** will be set to FlagUp = true
2. Within the **Controller** class, there wil be conditional model logic based on the state of the Flag **Entity**.

### Use Case 9
The User presses "Z" or whatever keybinding is used for the attacking and the Avatar will produce an attack that can damage enemy Entities.

## Use Case 10 
The Player can launch projectiles in at least four directions.

### Use Case 11
The User presses "X" or whatever keybinding is used for throwing a projectile (both physics and non-physics obeying) that can damage enemy Entities.

### Use Case 12
The User wishes to replay a level before it has ended, selecting the restart level option from the game's pause menu. 

### Use Case 13
The User wishes to go Home to switch games while already in the middle of playing. 

### Use Case 14
A specific entity property is missing from its respective file i.e skeleton's health.

### Use Case 15
The Avatar will begin to fall in accordance to gravity if it is not being supported by a block underneath it.

### Use Case 16
An enemy will be knocked back a certain distance when it is hit by an attack from the Avatar.

### Use Case 17
The Avatar picks up a power-up that increases their damage. The User then saves and exits the game. When the User returns to the game, the Avatar still has the added damage power-up, as if the User never left the game.

### Use Case 18
The User enters a different type of environment that affects general movement characteristics and images i.e. switching from land to water making player more floaty, switching run image to swim image

### Use Case 19
A specific config value is missing from the game setup file and that game is selected by the user.

### Use Case 20
The Player Presses Space to Jump, and gains a powerup that allows them to jump higher (mention the gravity physics)

### Use Case 21
The User chooses a different skin for their Avatar

### Use Case 22
The User presses a toggle for dark mode

### Use Case 23
The User is able to save the state of their game into a file (on the internet or locally) that can be later loaded.

### Use Case 24
The User presses "ESC" to open up the game menu, pausing the current game in the background.

### Use Case 25
The user loses a life and returns to the most recent checkpoint.

### Use Case 26
The User runs out of lives and dies, causing the game to either completely reset or give the option to load a Save file.

### Use Case 27
The user can select the difficulty of the game from the title screen/game menu.

### Use Case 28
The User can access the Options Menu from the Game Menu in order to change or stop the background music from playing.

### Use Case 29
The User presses "E" or some action key in order to interact with a button/switch in game, causing some blocks to appear/become solid/invisible.

### Use Case 30
The Avatar walks on special terrain blocks, such as tar or a bouncy gel, that affect the movement of the Avatar by either slowing down their speed or causing the Avatar to involuntarily bounce.

### Use Case 31
The Avatar walks over a pressure plated block, triggering an arrow trap to fire an Arrow at the Avatar until they no longer trigger the pressure plate.

### Use Case 32
The User is able to skip ahead levels upon discovering a secret powerup (Think the secret pipes in World 1-2 Super Mario Bros)

### Use Case 33
As the Avatar moves/stands, the Avatar "animates" its movement.

### Use Case 34
The User can "compete" with a Ghost player to try and beat the ghost's high score. The high score attempts are recorded after the player beats the game or dies.

### Use Case 35
The User wishes to stop playing a game in co-op and finish it in singleplayer. 

### Use Case 36
The User attempts to break the game by creating too many entities i.e. repeatedly launching projectiles for an extended period of time.

### Use Case 37
The User inputs a series of Keys that correspond to a cheat code to give the player an advantage (such as unclipping)

### Use Case 38
The User can use a gaming controller that supports the XInput standard instead of a keyboard.

### Use Case 39
Avatar collects pieces of a weapon, and once all the pieces have been gathered, the Avatar's weapon is upgraded with the new stats. (You could also theoretically do this with a key or any piece of equipment i.e. pieces of key -> whole key)

### Use Case 40
After completing a level, the User will be prompted to increase either the base speed, health, or damage of the Avatar.

### Use Case 41
The Player becomes trapped between a wall and an enemy moving in the same direction.

### Use Case 42
In a co-op scenario, a player standing on top of a platform attempts to jump off the head of another player that is in the middle of a jump.

### Use Case 43
In a co-op scenario, one player loses a life while the other progresses through enough of the level to initiate a camera scroll, and the dead player respawns.