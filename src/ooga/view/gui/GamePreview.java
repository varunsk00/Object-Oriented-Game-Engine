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
        this.setOnMouseClicked(e -> System.out.println(gameName));
    }
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }
    public double getXPos() {
        return this.xPos;
    }

}
