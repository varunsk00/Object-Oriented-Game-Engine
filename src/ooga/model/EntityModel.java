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
  private double xVelMax = 100;
  private double yVelMax = 100;
  private String entityID;

  private double xVel;
  private double yVel;

  private boolean onGround = true;
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
    health = myEntity.getParser().readHealth();
  }

  public void update(double elapsedTime){
    //TODO: change this ground status checker to be implemented in collisions with the top of a block
    //checkGroundStatus();

    for(Action action : controlScheme.getCurrentAction()){
      actionStack.push(action);
    }
    while(!actionStack.isEmpty()){
      actionStack.pop().execute(this);
    }
    limitSpeed();
    setX(xPos + xVel * elapsedTime);
    setY(yPos + yVel * elapsedTime);
  }

  public void handleKeyInput(KeyEvent event) {
    controlScheme.handleKeyInput(event);
  }

  public void handleKeyReleased(KeyEvent event) {
    controlScheme.handleKeyReleased(event);
  }

  private void limitSpeed(){
    if(Math.abs(xVel) > xVelMax){
      setXVelocity(Math.signum(xVel) * xVelMax);
    }
  }

  private void checkGroundStatus(){
    if(getY() < 225/* 300 - this.getHeight()*/){
      onGround = false;
    }
    else{
      onGround = true;
    }
  }

  public double getX(){return xPos;}

  public double getY(){return yPos;}

  public void setX(double newX){xPos = newX;}

  public void setY(double newY){yPos = newY;}

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
    newEntity.getModel().setX(this.getX());
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
