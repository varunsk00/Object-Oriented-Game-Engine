package ooga.view.gui;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GamePreview extends Rectangle {
    private String gameName;
    private double xPos;

    public GamePreview(Paint color) {
        this.setHeight(50);
        this.setWidth(50);
        this.setY(275);
        this.setFill(color);
        //this.gameName = color.toString();
        this.setOnMouseClicked(e -> this.gameName = color.toString());
        //this.setOnMouseClicked(e -> System.out.println(color));
    }
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }
    public double getXPos() {
        return this.xPos;
    }
    public String getGameName(){
        return gameName;
    }
    public void resetGameName(){
        gameName = null;
    }
}
