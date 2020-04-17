package ooga.view.gui.userinterface;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GamePreview extends StackPane {
    private String gameName;
    private ImagePattern frame;
    private ImagePattern gameGif;
    private Rectangle cart;
    private Rectangle gif;
    private double xPos;
    private boolean isPressed;
    private Paint gamePreviewColor;
    private boolean isClickable;


    public GamePreview(Paint color, String name) throws FileNotFoundException {
        this.setHeight(100);
        this.setWidth(100);
        cart = new Rectangle(100, 100);
        this.setLayoutY(275);
//        this.setY(275);
        this.setOnMouseClicked(e -> handleClick());
        gif = new Rectangle(80, 60);
        gif.setTranslateY(-10);
        this.gamePreviewColor = color;
        this.gameGif = new ImagePattern((new Image(new FileInputStream("src/resources/" + name + ".gif"))));
        this.frame = new ImagePattern((new Image(new FileInputStream("src/resources/gamecartridge.png"))));
        cart.setFill(frame);
        gif.setFill(gameGif);
        this.getChildren().add(cart);
        this.getChildren().add(gif);
//        this.setFill(frame);
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
    public void resetGameName(){
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
    public Paint getColor() {
        return this.gamePreviewColor;
    }
}
