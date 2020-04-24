package ooga.view.application.menu;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private TextField gameResult;
    private final String DEFAULT_MENU_TEXT = "Game in Progress";

    public InGameMenu() {
        this.savePressed = false;
        this.controlsPressed = false;
        this.exitPressed = false;
        this.resumePressed = false;
        this.rebootPressed = false;
        this.gameResult = new TextField(DEFAULT_MENU_TEXT);
        renderButtons();
        getChildren().addAll(myButtons, gameResult);
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
        return controlsPressed;
    }

    public void setControlsOff() {
        controlsPressed = false;
    }

    private void renderButtons() { //FIXME: MAGIC STRINGS
        myButtons = new VBox();
        Button ResumeButton = makeButton("Play Game", event -> resumePressed = true);
        Button SaveButton = makeButton("Save Game", event -> savePressed = true);
        Button ControlsButton = makeButton("Configuration", event -> controlsPressed = true);
        Button ExitButton = makeButton("Game Select", event -> exitPressed = true);
        Button RestartButton = makeButton("Reboot System", event -> rebootPressed = true);
        myButtons.getChildren().addAll(ResumeButton, SaveButton, ControlsButton, ExitButton, RestartButton);
        formatButton(ResumeButton);
        formatButton(SaveButton);
        formatButton(ControlsButton);
        formatButton(ExitButton);
        formatButton(RestartButton);
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(key);
        tempButton.setOnAction(e);
        return tempButton;
    }

    private void formatButton(Button tempButton) {
        myButtons.setVgrow(tempButton, Priority.ALWAYS);
    }

    public void updateGameResult(String gameResult) {
        TextField textField = new TextField(gameResult);
        textField.setEditable(false);
        getChildren().set(getChildren().size() - 1, textField);
    }




}
