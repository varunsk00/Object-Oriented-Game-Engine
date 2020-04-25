package ooga.view.application;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.controller.EntityWrapper;

public class Camera {
  private Rectangle viewPort;
  private List<EntityWrapper> target;
  private Stage myStage;
  private int scrollingStatusX;
  private int scrollingStatusY;
  private double centerY;
  private double centerX;


  public Camera(Stage stage, Pane level, List<EntityWrapper> focus, int scrollIntX, int scrollIntY){
    scrollingStatusX = scrollIntX;
    scrollingStatusY = scrollIntY;
    viewPort = new Rectangle();
    viewPort.widthProperty().bind(stage.widthProperty());
    viewPort.heightProperty().bind(stage.heightProperty());

    target = focus;
    myStage = stage;

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

  public void update(List<Node> menu){
    getCenterOfPlayers();
    viewPort.setX(boundPosition(scrollingStatusX * (centerX -myStage.getWidth()/2), 0, Double.POSITIVE_INFINITY));
    viewPort.setY(boundPosition(scrollingStatusY * (centerY -myStage.getHeight()/2), Double.NEGATIVE_INFINITY, 0));
    for(Node item: menu){
      item.setTranslateX(viewPort.getX());
      item.setTranslateY(viewPort.getY());
    }
    boundPlayers();
  }

  private void boundPlayers() {
    for(EntityWrapper player : target){
      if(player.getRender().getBoundsInParent().getMinX() <= viewPort.getBoundsInParent().getMinX()){
        player.getModel().setBoundedLeft(true);
      } else if(player.getRender().getBoundsInParent().getMaxX() >= viewPort.getBoundsInParent().getMaxX()){
        player.getModel().setBoundedRight(true);
      } else if(player.getRender().getBoundsInParent().getMaxY() >= viewPort.getBoundsInParent().getMaxY()){
        player.getModel().setBoundedBelow(true);
      } else if(player.getRender().getBoundsInParent().getMinY() <= viewPort.getBoundsInParent().getMinY()){
        player.getModel().setBoundedTop(true);
      }
    }
  }


  private void getCenterOfPlayers(){
    centerY = 0;
    centerX = 0;
    for(int i = 0; i < target.size(); i++){
      centerY += target.get(i).getRender().getBoundsInParent().getCenterY();
      centerX += target.get(i).getRender().getBoundsInParent().getCenterX();
    }
    centerY /= target.size();
    centerX /= target.size();
  }

}
