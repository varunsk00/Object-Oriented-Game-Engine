## Description

Summary of the feature's bug (without describing implementation details)
 * This bug causes entities to remain on screen when when their Die action is called (i.e. a goomba or koopa in 
 Mario should die/no longer show up on screen when Mario jumps on them). The entity is still displayed on the screen and the user can still 
 interact with it even though this should not be the case. 
 
 This feature was originally working as of commit (https://coursework.cs.duke.edu/compsci308_2020spring/final_team15/-/commit/be23598f71aa6eb82f539611619812f9a91576a3) [Commit be23598f] 
 
 and seems to have been caused by refactoring during commit (https://coursework.cs.duke.edu/compsci308_2020spring/final_team15/-/commit/ae847056e3bc3c718b1d62f6e80cc6ed5fdc44d2) [Commit ae847056]

## Expected Behavior

Describe the behavior you expect
When the entity is supposed to die during a collision even, the Die action is triggered. This should remove the entity from our entity lists
and then when the view is updated, the entity should no longer display on screen. An example would be when Mario jumps on a Goomba, the 
Goomba is removed from the screen and Mario can no longer interact with it. Thus, it is essentially as if the entity dies when hit.

## Current Behavior

Describe the actual behavior
When the entity is supposed to die, their image remains on screen and the user can still interact with them. Thus, they are essentially not
dying and being removed from the screen as they should be. 

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. Run Main.java and press "Play"
 2. On the game screen, scroll until you reach the Mario game. Select the Mario game by clicking on the game cartridge icon.
 3. On the Title Screen, choose "1 Player"
 4. When the game loads, attempt to jump on the Goomba. Use the space bar to jump and the D key to move to the right
 5. When the Gommba is hit, it should still show up on the screen
 
 --Note: if you hit the side of the Goomba too many times, you will die and have to restart the level so make sure that you are only jumping
 on top of the Goomba

## Failure Logs

Include any relevant print statements or stack traces.
N/A -- this is a code logic issue 

## Hypothesis for Fixing the Bug

Describe the test you think will verify this bug and the code fix you believe will solve this issue.

To fix this code, we would just need to revert back to our old way of handling deaths as of commit ____. The bug is happening because we 
respawn and despawn everything in our view in our step method. Thus, all we need to do is ensure that our dead entities do not respawn when 
the new view is rendered. We need to initialize a List that will keep track of all the entities that "die." When the spawnEntities method is 
called when we update the view of the current level, we should not respawn the dead entity. The required code changes for this bug are:

 1. GameController will need a new List called entityRemoved that keeps track of dead entities and it will need to implement a killEntity
 function that adds entities to this list and removes them from the view. 
        entityRemoved = new ArrayList<>(); (on line 66)
        
        public void killEntity(EntityWrapper entityWrapper) {
            entityRemoved.add(entityWrapper);
            myViewManager.removeEntity(entityWrapper.getRender());
          }
          
 2. The Die Action will need to add the dead entity to the entityRemoved list by calling the killEntity() function from GameController
            entity.killEntity();
            
 3. The spawnEntity() method in the level classes will need to take in entityRemoved as a parameter. Then, in the loop that respawns enemy entities, 
 there needs to be a conditional that checks if the entity is in entityRemoved. If it is in this list, then it is not respawned. 
 
 Line 33 of FiniteLevel.java should go from: 
 
 if (!playerEntities.contains(enemyEntity) && isInRange(player.getModel(), enemyEntity.getModel()) && !currentEntityList.contains(enemyEntity))
           
 to 
 
 if (!playerEntities.contains(enemyEntity) && isInRange(player.getModel(), enemyEntity.getModel()) && !currentEntityList.contains(enemyEntity)
           && !entityRemoved.contains(enemyEntity))
           
Lines 34-37 of InfiniteLevel.java should have an if conditional around them with the invariant

if(!entityRemoved.contains(enemyEntity))      
