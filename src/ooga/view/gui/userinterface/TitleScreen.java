package ooga.view.gui.userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import ooga.util.GameParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class TitleScreen extends BorderPane {
    private final String IMAGES_PACKAGE = "src/ooga/view/gui/resources/";
    private final String RESOURCES_PACKAGE = "ooga.view.gui.userinterface.resources.";
    private final String TITLE_SCREEN = "titleScreenButtons";
    private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + TITLE_SCREEN);
    private final String BG = "_player_select.png";
    private final String LOGO = "_logo.png";
    private final double HUNDRED_PERCENT = 100.0;
    private String gameName;
    private boolean onePressed;
    private boolean twoPressed;
    private Button Button1;
    private Button Button2;
    private VBox myButtons;
    private GameParser myGameParser;

    public TitleScreen() {
    }

    public TitleScreen(String name) throws FileNotFoundException {
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
        return new Image(new FileInputStream(IMAGES_PACKAGE + gameName.toLowerCase() + type));
    }

    private void renderButtons() { //TODO: REFACTOR
        myButtons = new VBox();
        Button1 = makeButton(myResources.getString(gameName + "1"), event -> onePressed = true);
        if (!(myResources.getString(gameName + "2").equals("NOBUTTON"))){
            Button2 = makeButton(myResources.getString(gameName + "2"), event -> twoPressed = true);
        }
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(key);
        tempButton.setOnAction(e);
        myButtons.getChildren().add(tempButton);
        formatButton(tempButton);
        return tempButton;
    }

    private void formatButton(Button tempButton) {
        myButtons.setVgrow(tempButton, Priority.ALWAYS);
    }

    public boolean getOnePressed() {
        return onePressed;
    }

    public void setOneOff() {
        onePressed = false;
    }

    public boolean getTwoPressed() {
        return twoPressed;
    }

    public void setTwoOff() {
        twoPressed = false;
    }

    public boolean isPlayerSelected() {
        return onePressed || twoPressed;
    }

    public int playerNumber() { //FIXME: STREAMLINE
        if (getTwoPressed()){
            return 2; }
        return 1;
    }

    public void resetButtons() {
        setOneOff();
        setTwoOff();
    }

    public void handleMultiplayer(String gameName){ //TODO: need to ask Varun about this
        this.myGameParser = new GameParser(gameName);
        if (myGameParser.supportsMultiplayer()){
            myGameParser.updateJSONValue("players", String.valueOf(playerNumber()));
        }
    }
}
