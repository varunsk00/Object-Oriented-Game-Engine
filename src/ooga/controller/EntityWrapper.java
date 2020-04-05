package ooga.controller;

import ooga.model.EntityModel;
import ooga.view.EntityView;

public class EntityWrapper {
  private EntityModel myModel;
  private EntityView myView;

  public EntityWrapper(){
    myModel = new EntityModel();
    myView = new EntityView();
  }

  public void update(){
//    myModel.update();
//    myView.update(myModel.getX(), myModel.getY());
  }
}
