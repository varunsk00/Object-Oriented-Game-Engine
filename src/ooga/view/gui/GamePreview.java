package ooga.view.gui;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GamePreview extends Rectangle {
    private String gameName;
    private double xPos;
    private boolean isPressed;

    public GamePreview(Paint color) {
        this.setHeight(100);
        this.setWidth(100);
        this.setY(275);
        this.setFill(color);
        //this.gameName = color.toString();
        this.setOnMouseClicked(e -> isPressed=true);
        //this.setOnMouseClicked(e -> System.out.println(color));
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
}
