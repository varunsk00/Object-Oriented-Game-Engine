package ooga.view.gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GamePreview extends Rectangle {
    private String gameName;
    private Image gameImage;
    private double xPos;
    private boolean isPressed;
    private Paint gamePreviewColor;

    public GamePreview(Paint color) {
        this.setHeight(100);
        this.setWidth(100);
        this.setY(275);
        this.setFill(color);
        this.setOnMouseClicked(e -> isPressed=true);
        this.gamePreviewColor = color;
    }
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }
    public double getXPos() {
        return this.xPos;
    }
    public boolean getGamePressed(){
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
