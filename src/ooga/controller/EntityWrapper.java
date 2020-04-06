package ooga.controller;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import ooga.model.EntityModel;
import ooga.util.EntityParser;
import ooga.view.application.EntityView;

public class EntityWrapper {
  private EntityModel myModel;
  private EntityView myView;
  private EntityParser myParser;

  public EntityWrapper(String entityName){
    myParser = new EntityParser(entityName);
    myModel = new EntityModel(this);
    myView = new EntityView(this);
  }

  public void update(double elapsedTime){
    myModel.update(elapsedTime);
    myView.update(myModel.getX(), myModel.getY());
  }

  public void handleKeyInput(KeyEvent event) {
    myModel.handleKeyInput(event);
  }

  public EntityParser getParser(){return myParser;}

  public Node getRender(){return myView.getRender();}

  public void handleKeyReleased() {myModel.handleKeyReleased();}
}
