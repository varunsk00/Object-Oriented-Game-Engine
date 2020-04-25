package ooga.controller;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.swing.text.html.parser.Entity;
import ooga.model.EntityModel;
import ooga.util.EntityJSONParser;
import ooga.view.entity.EntityView;

/**
 * This class combines the EntityModel and the EntityView into one class so that the wrapper can access both.
 * */
public class EntityWrapper {
  private EntityModel myModel;
  private EntityView myView;
  private EntityJSONParser myParser;
  private double score;
  private GameController myController;
  private String EntityID;

  /**
   * Constructor for wrapper
   * @param entityName : entity name
   * @param controller: game controller
   */
  public EntityWrapper(String entityName, GameController controller) {
    myController = controller;
    EntityID = entityName;
    String[] gameAndName = entityName.split("\\.");
    myParser = new EntityJSONParser(gameAndName[0], gameAndName[1]);
    myModel = new EntityModel(this);
    myView = new EntityView(this);
  }

  /**
   * Updates the view and the model
   * @param elapsedTime  : time elapsed
   */
  public void update(double elapsedTime){
    myModel.update(elapsedTime);
    myView.update(myModel.getX(), myModel.getY(), myModel.getForwards());
  }

  /**
   * handles key inputs
   * @param key : key pressed
   */
  public void handleKeyInput(String key) {myModel.handleKeyInput(key); }

//<<<<<<< HEAD
//  /**
//   * handles controller input
//   * @param key : button on controller pressed
//   */
//  public void handleControllerInputPressed(String key) {myModel.handleControllerInputPressed(key); }
//
//  /**
//   * handles controller input
//   * @param key : button on controller pressed
//   */
//  public void handleControllerInputReleased(String key) { myModel.handleControllerInputReleased(key);}
//
//  /**
//   * get EntityJSONParser
//   * @return json parser
//   */
//=======
//>>>>>>> f9e42f026c2b2cc0c74cc24e57b3795dfad85066
  public EntityJSONParser getParser(){return myParser;}

  /**
   * get entity model
   * @return entity model
   */
  public EntityModel getModel(){return myModel;}

  /**
   * get game controller
   * @return game controller
   */
  public GameController getController(){return myController;}

  /**
   * get image view of entity
   * @return image view
   */
  public ImageView getRender(){return myView.getRender();}

  /**
   * handles when key is released
   * @param key : released key
   */
  public void handleKeyReleased(String key) {myModel.handleKeyReleased(key);}

  /**
   * spawns an entity so it shows up on screen
   * @param param : entity param
   * @return entity that was spawned
   */
  public EntityWrapper spawnEntity(String param) {
    EntityWrapper newEntity = new EntityWrapper(param, myController);
    myController.addEntity(newEntity);
    return newEntity;
  }

  /**
   * removes entity from screen
   */
  public void despawnEntity() {
    myController.removeEntity(this);
  }

  /**
   * get entity id
   * @return entity id
   */
  public String getEntityID(){
    return this.EntityID;
  }

  /**
   * sets width of image
   * @param newWidth : new width
   */
  public void setWidth(double newWidth) {
    myView.setWidth(newWidth);
  }

  /**
   * sets width of height
   * @param newHeight : new height
   */
  public void setHeight(double newHeight) {
    myView.setHeight(newHeight);
  }

  /**
   * changes the image of the view
   * @param param : new image
   */
  public void changeImage(String param) {myView.changeImage(param);}

  /**
   * update the score
   * @param newValue : new value of score
   */
  public void updateScore(double newValue){score = newValue;}

  /**
   * returns the score
   * @return the score
   */
  public double getScore(){return score;}

  /**
   * changes level
   * @param levelIndex level index to change to
   */
  public void changeLevel(int levelIndex) {myController.changeLevel(levelIndex, this);}
}
