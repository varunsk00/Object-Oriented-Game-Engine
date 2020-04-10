package ooga.view.application;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jdk.swing.interop.SwingInterOpUtils;

public class Camera {
  private double xPosition;
  private double yPosition;
  private Rectangle viewPort;
  private Node target;
  private Stage myStage;
  private Pane myLevel;

  public Camera(Stage stage, Pane level, Node focus){
    xPosition = 0;
    yPosition = 0;
    viewPort = new Rectangle();
    viewPort.widthProperty().bind(stage.widthProperty());
    viewPort.heightProperty().bind(stage.heightProperty());

    target = focus;
    myStage = stage;
    myLevel = level;

    level.setClip(viewPort);

    level.translateXProperty().bind(viewPort.xProperty().multiply(-1));
    level.translateYProperty().bind(viewPort.yProperty().multiply(-1));
  }

  private double boundPosition(double value, double min, double max){
    if(value < min) return min;
    if(value > max) return max;
    return value;
  }

  public Rectangle getViewPort(){return viewPort;}

  public void update(){
    viewPort.setX(boundPosition(target.getBoundsInParent().getMinX()-myStage.getWidth()/2, 0, (-1*myLevel.getTranslateX())+2));
    //note: try to get level width working
  }
}
