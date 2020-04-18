package ooga.controller;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javax.swing.text.html.parser.Entity;
import ooga.model.EntityModel;
import ooga.util.EntityJSONParser;
import ooga.view.entity.EntityView;

public class EntityWrapper {
  private EntityModel myModel;
  private EntityView myView;
  private EntityJSONParser myParser;

  private Controller myController;

  private String EntityID;


  public EntityWrapper(String entityName, Controller controller) {
    myController = controller;
    EntityID = entityName;
    myParser = new EntityJSONParser(entityName);

    myModel = new EntityModel(this);
    myView = new EntityView(this);
  }

  public EntityWrapper(EntityWrapper copyEntity){
    myModel = copyEntity.myModel;
    myView = new EntityView(copyEntity);
    myController = copyEntity.myController;
    EntityID = copyEntity.EntityID;
//    myParser = copyEntity.myParser;
  }

  public void update(double elapsedTime){
    myModel.update(elapsedTime);
    myView.update(myModel.getX(), myModel.getY(), myModel.getForwards());
  }

  public void handleKeyInput(String key) {myModel.handleKeyInput(key); }

  public void handleControllerInputPressed(String key) {myModel.handleControllerInputPressed(key); }

  public void handleControllerInputReleased(String key) { myModel.handleControllerInputReleased(key);}

  public EntityJSONParser getParser(){return myParser;}

  public EntityModel getModel(){return myModel;}


  public Controller getController(){return myController;}

  public Node getRender(){return myView.getRender();}

  public void handleKeyReleased(String key) {myModel.handleKeyReleased(key);}

  public EntityWrapper spawnEntity(String param) {
    EntityWrapper newEntity = new EntityWrapper(param, myController);
    myController.addEntity(newEntity);
    return newEntity;
  }

  public String getEntityID(){
    return this.EntityID;
  }

  public void setX(double newX){myModel.setX(newX);}
  public void setWidth(double newWidth) {
    myView.setWidth(newWidth);
  }

  public void setHeight(double newHeight) {
    myView.setHeight(newHeight);
  }


}
