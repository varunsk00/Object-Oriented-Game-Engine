## Description

Summary of the feature's bug (without describing implementation details)
* When loading an incorrect datafile, the exception is being caught but no information is
displayed onscreen for the programmer/user
## Expected Behavior

Describe the behavior you expect
* An Alert action should display with the correct error message as described 
in the ```displayAlert()``` method of our exceptions in our exception package.
## Current Behavior

Describe the actual behavior
* When a bad file is loaded, when the user presses "start" game on a specific game,
the application appears frozen. What is happening is these exceptions are being thrown (firstly
inside the game loop which causes it to be "stuck") but their ```displayError()``` method is
not displayed.
## Steps to Reproduce

Provide detailed steps for reproducing the issue.

1. Open ```MarioGame.json``` and edit one of its tags to contain a type (ie. change "physicsProfile" to "physicssProfile")
2. Open up the application by running Main
3. Switch to mario, press "select," and press "1player"
4. Observe as the screen remains frozen without rendering the level.

## Failure Logs

Include any relevant print statements or stack traces.
* We do not have print statements in our code, but I wrote a test inside "" to demonstrate the bug:

## Hypothesis for Fixing the Bug

Describe the test you think will verify this bug and the code fix you believe will solve this issue.

* This bug does not affect any aspect of our code from a functionality point of view, so it is hard to write
a test for. This bug occurs also because a JFX element is not being displayed, which is even more
difficult to test because nothing in the program state is changing, it just is not being displayed
correctly to outsiders. 
* One way to write a test for this is to write a TestFX test which would search for the alert's "OK"
button and attempt to press it. It currently isn't displaying, so the test should fail until we correctly display the alert.

* Note: We realize now in writing the actual code for this bug fix that this is a more substantial edit
of our code than a simple bug fix. In hindsight, we should have done this work before the code
freeze and understand that this is a significant change to our exception handling. However, we considered
doing a smaller bug fix in terms of lines of code and reach of edits, but decided to design clean code
by implementing inheritance among other things. Our reasoning for this is that we shouldn't make our
project worse by slapping on a hastily-scrapped together bug fix for a problem, but rather have the bug 
fix improve our design and make our code better and cleaner. For this reason, we decided to take the
more drastic approach in this bug fix at risk of violating the code freeze. We hope that you understand
our reasoning behind this, and we completely accept checking out the commit from our initial freeze
rather than this one when evaluating our project.

The code to fix this bug is to add a try/catch block to the ```ProgramLauncher``` class. This try/catch block will
display the error on catching it within our game loop, and will reboot the entire application if it catches
such an error. The reason for placing the try/catch block so high in our code is that many entities will call
the same parser, resulting in many alerts being displayed, spamming out the user and resulting in a worse experience.
This way, we have one hierarchical exception management system which will make the executive call to display the error
(so the user knows what to fix in data) and then reboot the app. The following method is where the try/catch
will occur. This missingFileException

```java
try {
    step(); 
} catch (DisplayExceptions ex) {
    animation.pause();
    ex.displayAlert();
    stageManager.reboot();
} catch(Exception ex) {
    createAlert();
    stageManager.reboot();
}
```
```java
private void createAlert() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Unexpected Error");
    alert.setHeaderText("Unexpected Error. Please check data files and run again.");
    alert.show();
}
```

In addition, we add a try/catch block to the stagemanager's ```reboot()``` method (which calls JavaFX's start method and so is required to catch a generic Exception)
which is called here simply to prevent nested try/catch blocks from occurring in the above code.