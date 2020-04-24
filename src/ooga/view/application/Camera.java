package ooga.view.application;

import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jdk.swing.interop.SwingInterOpUtils;
import ooga.controller.EntityWrapper;

public class Camera {
  private double xPosition;
  private double yPosition;
  private Rectangle viewPort;
  private List<EntityWrapper> target;
  private Stage myStage;
  private Pane myLevel;
  private int scrollingStatusX;
  private int scrollingStatusY;
  private double sumY;
  private double sumX;


  public Camera(Stage stage, Pane level, List<EntityWrapper> focus, int scrollIntX, int scrollIntY){
    scrollingStatusX = scrollIntX;
    scrollingStatusY = scrollIntY;
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

  public void update(List<Node> menu){
//    viewPort.setX(boundPosition(target.getBoundsInParent().getMinX()-myStage.getWidth()/2, 0, (-1*myLevel.getTranslateX())+2));
    //note: try to get level width working
    getCenterOfPlayers();
    viewPort.setX(boundPosition(scrollingStatusX * (sumX-myStage.getWidth()/2), 0, (999999)));
    viewPort.setY(boundPosition(scrollingStatusY * (sumY-myStage.getHeight()/2), -999999, 0));

//    if (target.getBoundsInParent().getMaxY() < myStage.getHeight()/2) {
//      viewPort.setY(boundPosition(target.getBoundsInParent().getMinY() - myStage.getHeight() / 2, -999999, 720));
//    }
//    else {
//      viewPort.setY(0);
//    }
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
      }
    }
  }

  private void getCenterOfPlayers(){
    sumY = 0;
    sumX = 0;
    for(int i = 0; i < target.size(); i++){
      sumY += target.get(i).getRender().getBoundsInParent().getCenterY();
      sumX += target.get(i).getRender().getBoundsInParent().getCenterX();
    }
    sumY /= target.size();
    sumX /= target.size();
  }

}
