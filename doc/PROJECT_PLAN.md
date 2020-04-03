# PROJECT_PLAN
Final Team 15
### NetIDs: vsk10, ft39, as866, axo, sk593

* Our initial impression is that a majority of the work in the beginning will be focused on the back end and getting the game set up. Once the framework is there, we expect that the work will become more front end oriented. As a result, the following responsibilities are made on the assumption that all of us will have to contribute to the back end in some way. We expect that after the first Sprint our responsibilities will change. 
* In terms of ordering the work, we have a product backlog that has a list of all the tasks/bugs that need to fixed/worked on. Each task/bug will have a priority associated with it (high, medium, low) that will designate how important a task. We will continually update this backlog as tasks finish or become bigger/smaller problems. 

#### Alex
* Primary Backend
    * Entity class
        * Necessary composition classes (control scheme)
    * External Model API (Game Model API): This will handle implementing the functions that the Controller will need to integrate the front end and back end.  This will contain methods that get data from the game engine or entity models for the front end to use.  
* Secondary Configuration
    * Entity Parser implementation (taking entity XML files and translating them to Action objects)  
        * Data API: Handles reading in and writing to an  XML file. This will also handle how the XML data will be given to the Parser. The Data API will be implemented by  an Entity XML Reader/Writer that will specify how the data in the XML files will be used to populate different entities
     
#### Shruthi
* Primary Controller:
    * View-Model interactions and dependencies
        * ViewController: This will implement the external view API. The External View API will have methods that take data from the front end (about coordinates, images, etc) and pass it back to the back end
        * ModelController: This will implement the external model API so that it can use methods that will pass model data to the front end about actions and behaviors
* Secondary Backend:
    * Action Factory API: This class will create the basic structure of each action. It will be implemented by other specific classes (i.e. smash, throw, etc) that define a specific action
* Tertiary Configuration:
    * Game State Configuration : sets up each level
    * Game State/Level Parser: uses level set up XML files to define each level config
     * Config API: Handles reading in and writing to an  XML file. This will also handle how the XML data will be given to the Parser. This will be implemented by a Game State XML Reader/Writer that will specify how to store and load game states  
     
#### Frank
* Primarily backend:
    * Game Engine: Handles game logic and rule implementation 
        * Level Builder: Creates each level based on configs
        * Physics Engine: Implements physics of each action
* Secondary Configuration:  
    * Entity Configuration : sets up each entity
    * Entity Parser Implementation: XML to Entity object creation
        * Data API: Handles reading in and writing to an  XML file. This will also handle how the XML data will be given to the Parser. The Data API will be implemented by  an Entity XML Reader/Writer that will specify how the data in the XML files will be used to populate different entities
* Tertiary View-Model: 
    * External Model API (Game Model API): Helping with determining how Entity behaviors are made and interact with the rest of the project

#### Abhijay
* Primary: Frontend
    * GUI (HUD, stats, etc), Entity visuals, Level visuals
    * Internal View API: Handles internal implementation of the View
* Secondary: Backend
    * Entity Configuration : sets up each entity
    * Entity Parser Implementation: XML to Entity object creation
        * Data API: Handles reading in and writing to an  XML file. This will also handle how the XML data will be given to the Parser. The Data API will be implemented by  an Entity XML Reader/Writer that will specify how the data in the XML files will be used to populate different entities

#### Varun
* Primary: Front-end
    * User-Available configuration (GUI Settings)
    * External View API: The External View API will have methods that take data from the front end (about coordinates, images, etc) and pass it back to the back end  
    * Asset Loading and View
    * Internal View API: Handles internal implementation of the View
* Secondary: Back-end
    * KeyBindings: 
        * Player API: Handles how the player interacts with the computer/game 
* Tertiary: Controller
    * View-Model Interactions (Front-End/Controller interaction)
        * ViewController: This will implement the external view API. The External View API will have methods that take data from the front end (about coordinates, images, etc) and pass it back to the back end 
        * ModelController: This will implement the external model API so that it can use methods that will pass model data to the front end about actions and behaviors  
* Happy to Help in the early stages: Key-bindings and Actions to be executed on **Entity** objects