package ooga.controller;

import javafx.scene.input.KeyEvent;
import ooga.model.EntityModel;
import ooga.util.EntityParser;
import ooga.view.application.EntityView;

public class EntityWrapper {
  private EntityModel myModel;
  private EntityView myView;
  private EntityParser myParser;

  public EntityWrapper(String entityName){
    myModel = new EntityModel(this);
    myView = new EntityView(this);
    myParser = new EntityParser(entityName);
  }

  public void update(){
    myModel.update();
    myView.update(myModel.getX(), myModel.getY());
  }

  public void handleKeyInput(KeyEvent event) {
    myModel.handleKeyInput(event);
  }

  public EntityParser getParser(){return myParser;}
}
