package ooga.view.application.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

public class InGameMenu extends VBox{
    private boolean rightPressed;
    private boolean controlsPressed;
    private boolean resumePressed;
    private boolean exitPressed;
    private VBox myButtons;

    /**
     * Constructor that sets Resource Bundle and initializes all initial states of buttons      *
     * * Button states are initially False; ComboBox states have a defined initial String      *
     * * @param language the current language passed in from ParserController      * @throws
     * FileNotFoundException in case the File does not exist
     */
    public InGameMenu(String currentGame) {
        this.rightPressed = false;
        this.controlsPressed = false;
        this.exitPressed = false;
        this.resumePressed = false;
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

    public boolean getRightPressed() {
        return rightPressed;
    }

    public void setRightOff() {
        rightPressed = false;
    }

    public boolean getControlsPressed() {
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
        Button ResumeButton = makeButton(" Resume ", event -> resumePressed = true);
        Button DownButton = makeButton("Setting 2", event -> rightPressed = true);
        Button ControlsButton = makeButton(" Controls ", event -> controlsPressed = true);
        Button ExitButton = makeButton("Go Home", event -> exitPressed = true);
        myButtons.setTranslateX(590);
        myButtons.setSpacing(70); //FIXME: MAGIC NUMBER
        myButtons.getChildren().addAll(ResumeButton, DownButton, ControlsButton, ExitButton);
        formatButton(ResumeButton);
        formatButton(DownButton);
        formatButton(ControlsButton);
        formatButton(ExitButton);
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
