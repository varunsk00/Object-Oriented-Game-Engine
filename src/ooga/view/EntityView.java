package ooga.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import ooga.controller.EntityWrapper;

public class EntityView {
  private ImageView myImage;
  private EntityWrapper myEntity;

  public EntityView(EntityWrapper entityWrapper){
    myEntity = entityWrapper;
    myImage  = myEntity.getParser().generateImage();
  }

  public void update(double newX, double newY){
    myImage.setX(newX);
    myImage.setY(newY);
  };

  public Node getRender(){
    return myImage;
  }
}
