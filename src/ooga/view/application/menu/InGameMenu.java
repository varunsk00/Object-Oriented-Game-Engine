package ooga.view.application.menu;

import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;

public class InGameMenu extends VBox{
    private boolean savePressed;
    private boolean controlsPressed;
    private boolean resumePressed;
    private boolean exitPressed;
    private boolean rebootPressed;
    private VBox myButtons;

    /**
     * Constructor that sets Resource Bundle and initializes all initial states of buttons      *
     * * Button states are initially False; ComboBox states have a defined initial String      *
     * * @param language the current language passed in from ParserController      * @throws
     * FileNotFoundException in case the File does not exist
     */
    public InGameMenu() {
        this.savePressed = false;
        this.controlsPressed = false;
        this.exitPressed = false;
        this.resumePressed = false;
        this.rebootPressed = false;
        renderButtons();
        getChildren().add(myButtons);
    }

    /**
     * @return the JavaFX HBox that contains all the buttons
     */
    public VBox getVBox() {
        return myButtons;
    }

    public boolean getResumePressed() {
        return resumePressed;
    }

    public void setResumeOff() {
        resumePressed = false;
    }


    public boolean getExitPressed() {
        return exitPressed;
    }

    public void setExitOff() {
        exitPressed = false;
    }

    public boolean getSavePressed() {
        return savePressed;
    }

    public void setSaveOff() {
        savePressed = false;
    }

    public boolean getRebootPressed() {
        return rebootPressed;
    }

    public void setRebootOff() {
        rebootPressed = false;
    }

    public boolean getControlsPressed() {
        System.out.println("CHECKED");
        return controlsPressed;
    }

    public void setControlsOff() {
        controlsPressed = false;
    }

    /**
     * Creates and initializes all Buttons based on Regex Values
     */
    private void renderButtons() {
        myButtons = new VBox();
        Button ResumeButton = makeButton("Play Game", event -> resumePressed = true);
        Button SaveButton = makeButton("Save Game", event -> savePressed = true);
        Button ControlsButton = makeButton("Configuration", event -> controlsPressed = true);
        Button ExitButton = makeButton("Game Select", event -> exitPressed = true);
        Button RestartButton = makeButton("Reboot System", event -> rebootPressed = true);
        //myButtons.setTranslateX(590);
        //myButtons.setSpacing(70); //FIXME: MAGIC NUMBER
        myButtons.getChildren().addAll(ResumeButton, SaveButton, ControlsButton, ExitButton, RestartButton);
        formatButton(ResumeButton);
        formatButton(SaveButton);
        formatButton(ControlsButton);
        formatButton(ExitButton);
        formatButton(RestartButton);
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(key);
        //tempButton.setMaxWidth(Double.MAX_VALUE);
        tempButton.setOnAction(e);
        return tempButton;
    }

    private void formatButton(Button tempButton) {
        myButtons.setVgrow(tempButton, Priority.ALWAYS);
    }
}
