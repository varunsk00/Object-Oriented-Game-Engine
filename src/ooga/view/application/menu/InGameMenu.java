package ooga.view.application.menu;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class InGameMenu extends VBox{
    private final String RESOURCES_PACKAGE = "resources.guiText";
    private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE);
    private final String DEFAULT_MENU_TEXT = myResources.getString("defaultStatus");
    private final int SCENE_WIDTH = 1280;
    private final int SCROLL_DURATION = 10;
    private boolean savePressed;
    private boolean controlsPressed;
    private boolean resumePressed;
    private boolean exitPressed;
    private boolean rebootPressed;
    private VBox myButtons;
    private Text gameResult;

    public InGameMenu() {
        this.savePressed = false;
        this.controlsPressed = false;
        this.exitPressed = false;
        this.resumePressed = false;
        this.rebootPressed = false;
        renderButtons();
        this.gameResult = new Text(DEFAULT_MENU_TEXT);
        this.gameResult.setId("status");
        scrollText(this.gameResult);
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

    private void renderButtons() {
        myButtons = new VBox();
        Button resumeButton = makeButton(myResources.getString("Play"), event -> resumePressed = true);

        Button saveButton = makeButton(myResources.getString("Save"), event -> savePressed = true);
        Button controlsButton = makeButton(myResources.getString("Config"), event -> controlsPressed = true);
        Button exitButton = makeButton(myResources.getString("Exit"), event -> exitPressed = true);
        Button restartButton = makeButton(myResources.getString("Reboot"), event -> rebootPressed = true);
        myButtons.getChildren().addAll(resumeButton, saveButton, controlsButton, exitButton, restartButton);
        formatButton(resumeButton);
        formatButton(saveButton);
        formatButton(controlsButton);
        formatButton(exitButton);
        formatButton(restartButton);
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(key);
        tempButton.setOnAction(e);
        String id = key.toLowerCase().replace(" ", "");
        tempButton.setId(id);
        return tempButton;
    }

    private void formatButton(Button tempButton) {
        myButtons.setVgrow(tempButton, Priority.ALWAYS);
    }

    public void updateGameResult(String status) {
        this.gameResult = new Text(status);
        this.gameResult.setId("status");
        if(!getStatus().equals(DEFAULT_MENU_TEXT)) {
            this.gameResult = moveToCenter(gameResult); }
        scrollText(this.gameResult);
        getChildren().set(getChildren().size() - 1, this.gameResult);
    }

    private Text moveToCenter(Text oldMessage){
        Text newMessage = new Text(oldMessage.getText());
        newMessage.setId("centerStatus");
        return newMessage;
    }

    public String getStatus() {
        return gameResult.getText();
    }

    private void scrollText(Text status){
        KeyValue initKeyValue = new KeyValue(status.translateXProperty(), SCENE_WIDTH);
        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
        KeyValue endKeyValue = new KeyValue(status.translateXProperty(), -2.5*status.getLayoutBounds().getWidth());
        KeyFrame endFrame = new KeyFrame(Duration.seconds(SCROLL_DURATION), endKeyValue);
        Timeline timeline = new Timeline(initFrame, endFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }




}
