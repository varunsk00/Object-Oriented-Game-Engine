package ooga.controller;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import ooga.model.EntityModel;
import ooga.util.EntityJSONParser;
import ooga.util.EntityParser;
import ooga.view.EntityView;

public class EntityWrapper {
  private EntityModel myModel;
  private EntityView myView;
  private EntityJSONParser myParser;

  private Controller myController;


  public EntityWrapper(String entityName, Controller controller) {
    myController = controller;
    myParser = new EntityJSONParser(entityName);

    myModel = new EntityModel(this);
    myView = new EntityView(this);
  }

  public void update(double elapsedTime){
    myModel.update(elapsedTime);
    myView.update(myModel.getX(), myModel.getY(), myModel.getForwards());
  }

  public void handleKeyInput(KeyEvent event) {myModel.handleKeyInput(event); }

  public EntityJSONParser getParser(){return myParser;}

  public EntityModel getModel(){return myModel;}

  public Node getRender(){return myView.getRender();}

  public void handleKeyReleased(KeyEvent keyEvent) {myModel.handleKeyReleased(keyEvent);}

  public EntityWrapper spawnEntity(String param) {
    EntityWrapper newEntity = new EntityWrapper(param, myController);
    myController.addEntity(newEntity);
    return newEntity;
  }

  public void setWidth(double newWidth) {
    myView.setWidth(newWidth);
  }

  public void setHeight(double newHeight) {
    myView.setHeight(newHeight);
  }
}
