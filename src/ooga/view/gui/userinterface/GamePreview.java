package ooga.view.gui.userinterface;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ooga.exceptions.ParameterInvalidException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GamePreview extends StackPane {
    private final String RESOURCES_PATH = "src/resources/";
    private final String GAME_CARTRIDGE_IMG_PATH = "src/resources/gamecartridge.png";
    private final String DEFAULT_GIF = "defaultGIF.gif";
    private final int CARTRIDGE_SIZE = 100;
    private final int CARTRIDGE_LAYOUT = 275;
    private final int GIF_BODY_WIDTH = 80;
    private final int GIF_BODY_HEIGHT = 60;
    private final int ALLIGNMENT_VAL = -10;
    private String gameName;
    private ImagePattern gameCartridgeImage;
    private ImagePattern gamePreviewGif;
    private Rectangle gameCartridge;
    private Rectangle gamePreviewGifBody;
    private double xPos;
    private boolean isPressed;
    private boolean isClickable;


    public GamePreview(String name) throws FileNotFoundException {
        this.setId("GamePreview");
        this.setWidth(CARTRIDGE_SIZE);
        this.setLayoutY(CARTRIDGE_LAYOUT);
        this.setOnMouseClicked(e -> handleClick());
        try {
            this.gamePreviewGif = new ImagePattern((new Image(new FileInputStream(RESOURCES_PATH + name))));
        }
        catch (FileNotFoundException e) {
            new ParameterInvalidException(e, "GameSelectImage");
            this.gamePreviewGif = new ImagePattern((new Image(new FileInputStream(RESOURCES_PATH + DEFAULT_GIF))));
        }
        this.gameCartridgeImage = new ImagePattern((new Image(new FileInputStream(GAME_CARTRIDGE_IMG_PATH))));
        initVisuals();
    }
    private void initVisuals() {
        gameCartridge = new Rectangle(CARTRIDGE_SIZE, CARTRIDGE_SIZE);
        gamePreviewGifBody = new Rectangle(GIF_BODY_WIDTH, GIF_BODY_HEIGHT);
        gamePreviewGifBody.setTranslateY(ALLIGNMENT_VAL);
        gameCartridge.setFill(gameCartridgeImage);
        gamePreviewGifBody.setFill(gamePreviewGif);
        this.getChildren().add(gameCartridge);
        this.getChildren().add(gamePreviewGifBody);
    }
    private void handleClick() {
        if (isClickable) {
            this.isPressed = true;
        }
    }
    public void setClickable(boolean b) {
        this.isClickable = b;
    }
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }
    public double getXPos() {
        return this.xPos;
    }
    public boolean isGamePressed(){
        return this.isPressed;
    }
    public void resetGameButton(){
        this.isPressed = false;
    }
    public void setGameName(String name){
        gameName = name;
    }
    public String getGameName() {
        return this.gameName;
    }
    public void chooseGame() {
        this.isPressed = true;
    }
}
