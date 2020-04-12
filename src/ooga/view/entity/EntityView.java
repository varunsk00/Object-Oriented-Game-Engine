package ooga.view.entity;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import ooga.controller.EntityWrapper;

public class EntityView {
  private ImageView myImage;
  private EntityWrapper myEntity;

  public EntityView(EntityWrapper entityWrapper){
    myEntity = entityWrapper;
    myImage  = myEntity.getParser().generateImage();
    myEntity.getModel().setX(myImage.getX());
    myEntity.getModel().setY(myImage.getY());
  }

  public void update(double newX, double newY, boolean forwards){ //FIXME: SAY WE SWITCH FOR JUMP, REFACTOR
    myImage.setX(newX);
    myImage.setY(newY);
    myImage.setScaleX(forwards ? 1 : -1);
  };

  public Node getRender(){
    return myImage;
  }

  public void setWidth(double newWidth) {
    myImage.setFitWidth(newWidth);
  }

  public void setHeight(double newHeight) {
    myImage.setFitHeight(newHeight);
  }
}
