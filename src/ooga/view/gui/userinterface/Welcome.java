package ooga.view.gui.userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Welcome extends VBox {
    private final String TITLE_SCREEN_IMAGE = "src/resources/title.gif";
    private TextFlow welcome;
    private Text message;
    private VBox myButton;
    private Boolean playPressed;

    private ImageView welcomeImage;

    public Welcome() throws FileNotFoundException {
        this.setId("welcome");
        welcomeImage = new ImageView(new Image(new FileInputStream(TITLE_SCREEN_IMAGE)));
        this.welcome = new TextFlow();
        this.message = new Text("Welcome to \uD83C\uDD71ob's Object Oriented Arcade!");
        this.playPressed = false;
        welcome.setId("welcomeBanner");
        message.setId("welcomeMessage");
        createWelcomeScreen();
    }

    private void createWelcomeScreen(){
        renderButton();
        welcome.getChildren().add(message);
        getChildren().addAll(welcome, welcomeImage, myButton);
        setAlignment(Pos.CENTER);
    }

    private void renderButton() {
        myButton = new VBox();
        myButton.setId("welcomeButton");
        Button playButton = makeButton("Play", event -> playPressed = true);
        playButton.setId("play");
        myButton.getChildren().add(playButton);
        formatButton(playButton);
    }

    public void setPlayPressedOff() {
        playPressed = false;
    }

    public Boolean getPlayPressed() {
        return playPressed;
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(key);
        tempButton.setOnAction(e);
        return tempButton;
    }

    private void formatButton(Button tempButton) {
        myButton.setVgrow(tempButton, Priority.ALWAYS);
    }
}
