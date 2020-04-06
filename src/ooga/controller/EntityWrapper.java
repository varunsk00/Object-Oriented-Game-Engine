package ooga.controller;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import ooga.model.EntityModel;
import ooga.util.EntityParser;
import ooga.view.EntityView;

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
    System.out.println(event.getCode().toString());
    myModel.handleKeyInput(event);
  }

  public EntityParser getParser(){return myParser;}
  public EntityModel getModel(){return myModel;}

  public Node getRender(){return myView.getRender();}

  public void handleKeyReleased() {myModel.handleKeyReleased();}
}
