package ooga.view.gui.userinterface;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import ooga.util.GameParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class TitleScreen extends BorderPane {
    private final String IMAGES_PACKAGE = "src/resources/";
    private final String IMAGES = "/images/";
    private final String BG = "_player_select.png";
    private final String LOGO = "_logo.png";
    private final double HUNDRED_PERCENT = 100.0;
    private String gameName;
    private List<String> selectOptions;
    private Button[] allButtons;
    private VBox myButtons;
    private boolean[] playerBooleans;
    private boolean loadPressed;
    private int num;

    public TitleScreen() {} //Empty Constructor for step

    public TitleScreen(String name, List<String> buttonLabels) throws FileNotFoundException {
        this.gameName = name;
        this.selectOptions = buttonLabels;
        this.allButtons = new Button[selectOptions.size()];
        this.playerBooleans = new boolean[selectOptions.size()];
        this.myButtons = new VBox();
        myButtons.setId("titleButtons");
        this.num = 0;
        setBackground();
        setLogo();
        renderButtons();
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
        return new Image(new FileInputStream(IMAGES_PACKAGE + gameName.toLowerCase() + IMAGES + gameName.toLowerCase() + type));
    }

    private void renderButtons() { //supports infinite amount of player buttons + Load Button
        for(int i=0; i< allButtons.length; i++){
            String label = selectOptions.get(i);
            int index = i;
            if (label.contains("Player")){ //JSON Parsing
                allButtons[i] = makeButton(label, event -> playerBooleans[index] = true); }
            else{
                allButtons[i] = makeButton(label, event -> {
                    playerBooleans[index] = true;
                    loadPressed = true;
                });
            }
            myButtons.getChildren().add(allButtons[i]);
        }
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(key);
        tempButton.setOnAction(e);
        formatButton(tempButton);
        return tempButton;
    }

    private void formatButton(Button tempButton) {
        myButtons.setVgrow(tempButton, Priority.ALWAYS);
    }

    public void setLoadOff() {
        loadPressed = false;
    }

    public boolean isPlayerSelected() {
        boolean ret = false;
        if(playerBooleans != null){
            for(int i = 0; i<playerBooleans.length;i++){
                if(playerBooleans[i]){
                    this.num = (i +1);
                    ret = playerBooleans[i]; } } }
        return ret;
    }

    public boolean isLoadSavedGame() { return loadPressed; }

    public void resetButtons() {
        for(int i = 0; i<playerBooleans.length; i++){
            playerBooleans[i] = false;
            loadPressed = false; }
    }

    public void handleMultiplayer(String gameName){
        GameParser myGameParser = new GameParser(gameName, null, false);
        if (myGameParser.supportsMultiplayer()){
            myGameParser.updateJSONValue("playerCount", String.valueOf(num));
        }
    }
}
