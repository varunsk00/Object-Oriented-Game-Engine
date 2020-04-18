package ooga.view.gui.userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import ooga.util.LevelParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PlayerSelect extends BorderPane {
    private final String RESOURCES_PACKAGE = "src/ooga/view/gui/resources/";
    private final String BG = "_player_select.png";
    private final String LOGO = "_logo.png";
    private final double HUNDRED_PERCENT = 100.0;
    private String gameName;
    private int playerNum;
    private boolean P1Pressed;
    private boolean P2Pressed;
    private Button P1Button;
    private Button P2Button;
    private VBox myButtons;
    private LevelParser myGameParser;

    public PlayerSelect(String name) throws FileNotFoundException {
        this.gameName = name;
        setBackground();
        setLogo();
        renderButtons();
        myButtons.setAlignment(Pos.CENTER);
        myButtons.setSpacing(10.0); //FIXME: MAGIC NUMBER
        myButtons.setTranslateY(-100.0); //FIXME: MAGIC NUMBER
        setBottom(myButtons);
    }

    private void setBackground() throws FileNotFoundException {
        Image image = loadImage(BG);
        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(HUNDRED_PERCENT,HUNDRED_PERCENT,true,true,false,true));
        Background bg = new Background(myBI);
        this.setBackground(bg);
    }

    private void setLogo() throws FileNotFoundException {
        Image image = loadImage(LOGO);
        ImageView logo = new ImageView(image);
        this.setCenter(logo);
    }

    private Image loadImage(String type) throws FileNotFoundException {
        return new Image(new FileInputStream(RESOURCES_PACKAGE + gameName.toLowerCase() + type));
    }

    private void renderButtons() {
        myButtons = new VBox();
        P1Button = makeButton("1 Player", event -> P1Pressed = true);
        P2Button = makeButton("2 Players", event -> P2Pressed = true);
        myButtons.getChildren().addAll(P1Button, P2Button);
        formatButton(P1Button);
        formatButton(P2Button);
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(key);
        tempButton.setOnAction(e);
        return tempButton;
    }

    public void disableP1Button() {
        P1Button.setDisable(true);
    }

    public void disableP2Button() {
        P2Button.setDisable(true);
    }

    private void formatButton(Button tempButton) {
        myButtons.setVgrow(tempButton, Priority.ALWAYS);
    }

    public boolean getP1Pressed() {
        return P1Pressed;
    }

    public void setP1PressedOff() {
        P1Pressed = false;
    }

    public boolean getP2Pressed() {
        return P2Pressed;
    }

    public void setP2PressedOff() {
        P2Pressed = false;
    }

    public boolean isPlayerSelected() {
        return P1Pressed || P2Pressed;
    }

    public int playerNumber() { //FIXME: STREAMLINE
        if (getP1Pressed()){
            playerNum = 1;
        }
        if (getP2Pressed()){
            playerNum = 2;
        }
        return playerNum;
    }

    public void resetButtons() {
        setP1PressedOff();
        setP2PressedOff();
    }

    public void handleMultiplayer(String gameName){
        this.myGameParser = new LevelParser(gameName + "Level");
        myGameParser.updateJSONValue("players", String.valueOf(playerNumber()));
    }
}
