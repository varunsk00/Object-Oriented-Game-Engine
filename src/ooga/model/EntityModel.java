package ooga.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javafx.scene.input.KeyEvent;
import ooga.controller.EntityWrapper;
import ooga.model.actions.AccelerateX;
import ooga.model.actions.Action;
import ooga.model.actions.CollisionKey;
import ooga.model.controlschemes.ControlScheme;

public class EntityModel {
  private EntityWrapper myEntity;
  private boolean forwards;
  private double entityWidth;
  private double entityHeight;
  private double xPos;
  private double yPos;
  private double health;
  private double xVelMax;
  private double yVelMax;
  private boolean levelAdvancementStatus;
  private int nextLevelIndex;
  private String entityID;
  private boolean onGround;

  private double xVel;
  private double yVel;

  private ControlScheme controlScheme;
  private Stack<Action> actionStack;
  private Map<String, Action> myActions;
  private Map<CollisionKey, Action> myCollisions;

  public EntityModel(EntityWrapper entityWrapper) {
    myEntity = entityWrapper;
    entityID = entityWrapper.getEntityID();
    controlScheme = myEntity.getParser().parseControls();
    myCollisions = myEntity.getParser().parseCollisions();
    loadStats();
    actionStack = new Stack<>();
    myActions = new HashMap<String, Action>();
    forwards = true;
  }

  private void loadStats() {
    entityWidth = myEntity.getParser().readWidth();
    entityHeight = myEntity.getParser().readHeight();
    xPos = myEntity.getParser().readXPosition();
    yPos = myEntity.getParser().readYPosition();
    xVelMax = myEntity.getParser().readMaxXVelocity();
    yVelMax = myEntity.getParser().readMaxYVelocity();
    health = myEntity.getParser().readHealth();
    xVelMax = myEntity.getParser().readXVelMax();
    yVelMax = myEntity.getParser().readYVelMax();
    onGround = myEntity.getParser().readGrounded();
  }

  public void update(double elapsedTime){
    //TODO: change this ground status checker to be implemented in collisions with the top of a block

    for(Action action : controlScheme.getCurrentAction()){
      System.out.println(action);
      actionStack.push(action);
    }
    while(!actionStack.isEmpty()){
      actionStack.pop().execute(this);
    }
    limitSpeed();
    setX(xPos + xVel * elapsedTime);
    setY(yPos + yVel * elapsedTime);
  }

  public void handleKeyInput(String key) {
    controlScheme.handleKeyInput(key);
  }

  public void handleKeyReleased(String key) {
    controlScheme.handleKeyReleased(key);
  }

  public void handleControllerInputPressed(String key) {
    if (key != null) {
      System.out.println(key);
      controlScheme.handleKeyInput(key);
    }
  }
  public void handleControllerInputReleased(String key) {
    if (key != null) {
      System.out.println("ENTITYMODEL");
      controlScheme.handleKeyReleased(key);
    }
  }

  private void limitSpeed(){
    if(Math.abs(xVel) > xVelMax){
      setXVelocity(Math.signum(xVel) * xVelMax);
    }
  }

  public double getX(){return xPos;}

  public double getY(){return yPos;}

  public void setX(double newX){xPos = newX;}

  public void setY(double newY){yPos = newY;}

  public void setLevelAdvancementStatus(boolean newStatus){levelAdvancementStatus = newStatus;}

  public void setNextLevelIndex(int levelIndex){nextLevelIndex = levelIndex;}

  public boolean getLevelAdvancementStatus(){return levelAdvancementStatus;}

  public int getNextLevelIndex(){return nextLevelIndex;}


  public double getWidth(){return entityWidth;}

  public double getHeight(){return entityHeight;}


  public double getXVelocity(){return xVel;}

  public double getYVelocity(){return yVel;}

  public void setXVelocity(double newXVelocity){xVel = newXVelocity; }

  public void setYVelocity(double newYVelocity){yVel = newYVelocity; }

  public boolean isOnGround(){
    return onGround;
  }
  public String getEntityID(){
    return this.entityID;
  }

  public void setOnGround(boolean groundStatus){
    this.onGround = groundStatus;
  }

  public Stack<Action> getActionStack() {
    return actionStack;
  }

  public Map<CollisionKey, Action> getCollisionMap() {
    return myCollisions;
  }

  public void spawnRelative(String param){
    EntityWrapper newEntity = spawnEntity(param);
    newEntity.getModel().setX(this.getX() + this.getWidth()/2);
    newEntity.getModel().setY(this.getY());
    newEntity.getModel().setForwards(this.getForwards());
  }

  public EntityWrapper spawnEntity(String param) {
    EntityWrapper newEntity = myEntity.spawnEntity(param);
    return newEntity;
  }


  public boolean getForwards() {return forwards;}

  public void setForwards(boolean direction) {
    forwards = direction;
  }

  public void setWidth(double newWidth){
    entityWidth = newWidth;
    myEntity.setWidth(newWidth);
  }

  public void setHeight(double newHeight){
    entityHeight = newHeight;
    myEntity.setHeight(newHeight);
  }
}
