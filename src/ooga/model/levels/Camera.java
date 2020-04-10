package ooga.model.levels;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
//    viewPort.xProperty().bind(focus.layoutXProperty());
//    viewPort.yProperty().bind(focus.layoutYProperty());
    target = focus;
    myStage = stage;
    myLevel = level;
//    viewPort.xProperty().bind(Bindings.createDoubleBinding(() -> boundPosition(focus.getBoundsInParent().getMinX(), 0, level.getWidth()-scene.getWidth()), scene.widthProperty()));
//    viewPort.yProperty().bind(Bindings.createDoubleBinding(() -> boundPosition(focus.getBoundsInParent().getMinY(), 0, level.getHeight()-scene.getHeight()), scene.heightProperty()));
    level.setClip(viewPort);
    level.translateXProperty().bind(viewPort.xProperty().multiply(-1));
    level.translateYProperty().bind(viewPort.yProperty().multiply(-1));
  }

  private double boundPosition(double value, double min, double max){
//    System.out.println(value + "\t" + min + "\t" + max);
    if(value < min) return min;
    if(value > max) return max;
    return value;
  }

  public Rectangle getViewPort(){return viewPort;}

  public void update(){
//    System.out.println(boundPosition(target.getBoundsInParent().getMinX()-myScene.getWidth()/2, 0, myLevel.getWidth()-myScene.getWidth()));
//    System.out.println(boundPosition(target.getBoundsInParent().getMinX()-myScene.getWidth()/2, 0, myLevel.getWidth()-myScene.getWidth()));
    viewPort.setX(boundPosition(target.getBoundsInParent().getMinX()-myStage.getWidth()/2, 0, myLevel.getWidth()));
  }
}
