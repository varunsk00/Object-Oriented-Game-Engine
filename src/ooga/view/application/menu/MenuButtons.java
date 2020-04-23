package ooga.view.application.menu;

import com.thoughtworks.xstream.XStream;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import ooga.view.application.games.Game;
import ooga.view.application.games.GameDataManager;

public class MenuButtons  {
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean resumePressed;
    private boolean downPressed;
    private VBox myButtons;
    private Game currentGame;
    private XStream xStream;

    /**
     * Constructor that sets Resource Bundle and initializes all initial states of buttons      *
     * * Button states are initially False; ComboBox states have a defined initial String      *
     * * @param language the current language passed in from ParserController      * @throws
     * FileNotFoundException in case the File does not exist
     */
    public MenuButtons(Game currentGame) {
        this.rightPressed = false;
        this.leftPressed = false;
        this.downPressed = false;
        this.resumePressed = false;
        this.currentGame = currentGame;
        xStream = new XStream();
        //gameDataManager = new GameDataManager();
        renderButtons();
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
        Button ResumeButton = makeButton("Resume", event -> resumePressed = true);
        Button DownButton = makeButton("Save Game", event -> {
            System.out.println("save button");
            //xStream.toXML(currentGame);
//            GameDataManager.save(currentGame, "savedGameData");
        });
        Button LeftButton = makeButton("Setting 3", event -> leftPressed = true);
        Button RightButton = makeButton("Setting 4", event -> rightPressed = true);
        Text instructions = new Text("Press Q to Quit");
        myButtons.setSpacing(70); //FIXME: MAGIC NUMBER
        myButtons.setTranslateX(640); //FIXME: MAGIC NUMBER
        myButtons.setTranslateY(180); //FIXME: MAGIC NUMBER
        myButtons.getChildren().addAll(ResumeButton, DownButton, RightButton, LeftButton, instructions);
        formatButton(ResumeButton);
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
