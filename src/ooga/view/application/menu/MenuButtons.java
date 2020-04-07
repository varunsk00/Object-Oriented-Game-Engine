package ooga.view.application.menu;

import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

public class MenuButtons  {
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean upPressed;
    private boolean downPressed;
    private VBox myButtons;

    /**
     * Constructor that sets Resource Bundle and initializes all initial states of buttons      *
     * * Button states are initially False; ComboBox states have a defined initial String      *
     * * @param language the current language passed in from ParserController      * @throws
     * FileNotFoundException in case the File does not exist
     */
    public MenuButtons(String currentGame) {
        this.rightPressed = false;
        this.leftPressed = false;
        this.downPressed = false;
        this.upPressed = false;
        renderButtons();
    }

    /**
     * @return the JavaFX HBox that contains all the buttons
     */
    public VBox getVBox() {
        return myButtons;
    }

    public boolean getUpPressed() {
        return upPressed;
    }

    public void setUpOff() {
        upPressed = false;
    }


    public boolean getDownPressed() {
        return downPressed;
    }

    public void setDownOff() {
        downPressed = false;
    }

    public boolean getRightPressed() {
        return rightPressed;
    }

    public void setRightOff() {
        rightPressed = false;
    }

    public boolean getLeftPressed() {
        return leftPressed;
    }

    public void setLeftOff() {
        leftPressed = false;
    }

    /**
     * Creates and initializes all Buttons based on Regex Values
     */
    private void renderButtons() {
        myButtons = new VBox();
        Button UpButton = makeButton("Setting 1", event -> upPressed = true);
        Button DownButton = makeButton("Setting 2", event -> downPressed = true);
        Button LeftButton = makeButton("Setting 3", event -> leftPressed = true);
        Button RightButton = makeButton("Setting 4", event -> rightPressed = true);
        Text instructions = new Text("Press Q to Quit");
        myButtons.setSpacing(100); //FIXME: MAGIC NUMBER
        myButtons.setTranslateX(640); //FIXME: MAGIC NUMBER
        myButtons.setTranslateY(180); //FIXME: MAGIC NUMBER
        myButtons.getChildren().addAll(UpButton, DownButton, RightButton, LeftButton, instructions);
        formatButton(UpButton);
        formatButton(DownButton);
        formatButton(RightButton);
        formatButton(LeftButton);
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(key);
        tempButton.setMaxWidth(Double.MAX_VALUE);
        tempButton.setOnAction(e);
        return tempButton;
    }

    private void formatButton(Button tempButton) {
        myButtons.setVgrow(tempButton, Priority.ALWAYS);
    }
}
