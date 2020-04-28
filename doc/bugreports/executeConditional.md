## Description

Summary of the feature's bug (without describing implementation details)

## Expected Behavior

Describe the behavior you expect
* When using the "execute conditional" action, the action following it should only execute if a certain conditional is true.
## Current Behavior

Describe the actual behavior
* Instead, the action following the "execute conditional" action is always executing as if the conditional were always true.
## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. Run the application
 2. Press the left arrow 3 times to navigate to the "Smash" game.
 3. Press Select and 1 Player
 4. Rapidly press the left, right, or up arrow on your keyboard. Mario should only use an attack every few seconds but instead tries to do it twice and disappears.

## Failure Logs

Include any relevant print statements or stack traces.
* The execute conditional test class inside test.ooga.model.actions demonstrates how this execute method
is broken:
 ```
org.opentest4j.AssertionFailedError: 
Expected :0.0
Actual   :10.0
```
Essentially, if the conditional is false, the subsequent action remains on the stack instead of
getting removed and not executed. In this case, the action was Move (10) and so the entity still moves
despite execute conditional "cancelling" the queued action for being on the stack.
## Hypothesis for Fixing the Bug

* As mentioned above,the ExecuteConditionalTest successfully identifies the bug. The solution for the bug is add 
a line in the ExecuteConditional's ```execute()``` method before the if statement
```java
public void execute(EntityModel entity) {
    if(entity.getConditional()){
      entity.getActionStack().pop().execute(entity);
    }
  }
```
add
```java
public void execute(EntityModel entity) {
    Action action = entity.getActionStack.().pop();
    if(entity.getConditional()){
      action.execute(entity);
    }
  }
```
In this way, the action will be removed from the actionstack no matter what, and 
then will only execute conditionally as intended.