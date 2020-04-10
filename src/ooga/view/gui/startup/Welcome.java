package ooga.view.gui.startup;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Welcome extends VBox {
    private Color bgColor = Color.LIGHTGREY;
    private BackgroundFill bgFill;
    private Background bg;
    private TextFlow welcome;
    private Text message;
    private Text play;
    private VBox myButton;
    private Boolean playPressed;

    private ImageView welcomeImage;

    public Welcome() throws FileNotFoundException {
        welcomeImage = new ImageView(new Image(new FileInputStream("src/resources/title.gif")));
        this.playPressed = false;
        this.bgFill = new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY);
        this.bg = new Background(bgFill);
        this.welcome = new TextFlow();
        this.message = new Text("Welcome to \uD83C\uDD71ob's Object Oriented Arcade!");
        this.play = new Text("Play");
        createWelcomeScreen();
    }

    private void createWelcomeScreen(){
        renderButton();
        setBackground(bg);
        message.setFont(Font.font("Comic Sans", 50));
        welcome.getChildren().add(message);
        welcome.setTextAlignment(TextAlignment.CENTER);
        setSpacing(100); //FIXME: MAGIC NUMBER
        getChildren().addAll(welcome, welcomeImage, myButton);
        setAlignment(Pos.CENTER);
    }

    private void renderButton() {
        myButton = new VBox();
        Button playButton = makeButton("Play", event -> playPressed = true);
        myButton.getChildren().add(playButton);
        formatButton(playButton);
        myButton.setAlignment(Pos.CENTER);
    }

    public void setPlayPressedOff() {
        playPressed = false;
    }

    public Boolean getPlayPressed() {
        return playPressed;
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(key);
        tempButton.setScaleX(3.0); //FIXME: MAGIC NUMBER
        tempButton.setScaleY(3.0); //FIXME: MAGIC NUMBER
        tempButton.setOnAction(e);
        return tempButton;
    }

    private void formatButton(Button tempButton) {
        myButton.setVgrow(tempButton, Priority.ALWAYS);
    }
}
