package ooga.model.levels;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Camera {
  private double xPosition;
  private double yPosition;
  private Rectangle viewPort;

  public Camera(Scene scene, Pane level, Node focus){
    xPosition = 0;
    yPosition = 0;
    viewPort = new Rectangle();
    viewPort.widthProperty().bind(scene.widthProperty());
    viewPort.heightProperty().bind(scene.heightProperty());

    viewPort.xProperty().bind(Bindings.createDoubleBinding(() -> boundPosition(focus.getLayoutX(), 0, level.getWidth()-scene.getWidth()), focus.layoutXProperty(), scene.widthProperty()));
    viewPort.yProperty().bind(Bindings.createDoubleBinding(() -> boundPosition(focus.getLayoutX(), 0, level.getHeight()-scene.getHeight()), focus.layoutYProperty(), scene.heightProperty()));

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
}
