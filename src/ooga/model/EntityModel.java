package ooga.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import ooga.controller.EntityWrapper;
import ooga.model.actions.AbsoluteVelocityX;
import ooga.model.actions.Action;
import ooga.model.controlschemes.ControlScheme;

public class EntityModel {
  private EntityWrapper myEntity;
  private boolean forwards;
  private SimpleBooleanProperty forwardsProperty;
  private SimpleDoubleProperty xProperty;
  private SimpleDoubleProperty yProperty;
  private double entityWidth;
  private double entityHeight;
  private double xPos;
  private double yPos;
  private double xVel;
  private double yVel;

  private double health;
  private double xVelMax;
  private double yVelMax;
  private boolean levelAdvancementStatus;
  private int nextLevelIndex;
  private String entityID;
  private boolean fixedEntity;
  private boolean permeableEntity;
  private boolean boundedBelow;
  private boolean boundedLeft;
  private boolean boundedRight;
  private boolean boundedTop;
  private boolean collidable;
  private boolean isDead;

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
    forwardsProperty = new SimpleBooleanProperty(true);
    forwardsProperty.addListener(((observable, oldValue, newValue) -> {
      forwards = newValue;
    }));
    xProperty = new SimpleDoubleProperty(xPos);
    xProperty.addListener(((observable, oldValue, newValue) -> {
      xPos = (double) newValue;
    }));
    yProperty = new SimpleDoubleProperty(yPos);
    yProperty.addListener((((observable, oldValue, newValue) -> {
      yPos = (double) newValue;
    })));
    boundedLeft = false;
    boundedRight = false;
    boundedTop = false;
    boundedBelow = false;
    isDead = false;
    collidable = true;
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
    fixedEntity = myEntity.getParser().readFixed();
    permeableEntity = myEntity.getParser().readPermeable();
  }

  public void update(double elapsedTime){
    //TODO: change this ground status checker to be implemented in collisions with the top of a block
    for (Action action : controlScheme.getCurrentAction()) {
      actionStack.push(action);
    }
    while(!actionStack.isEmpty()){
      actionStack.pop().execute(this);
    }
    limitSpeed();
    limitBounds();
    setX(xPos + xVel * elapsedTime);
    setY(yPos + yVel * elapsedTime);
    boundedLeft = false;
    boundedRight = false;
    boundedBelow = false;
    boundedTop = false;
  }

  private void limitBounds() {
    if(boundedRight){
      if(xVel>0){xVel=0;}
    }
    if(boundedLeft){
      if(xVel<0){xVel=0;}
    }
    if(boundedBelow){
      if (yVel > 0) {yVel=0;}
    }
    if(boundedTop){
      if (yVel < 0) {yVel = 0;}
    }
  }

  public void handleKeyInput(String key) {
    controlScheme.handleKeyInput(key);
  }

  public void handleKeyReleased(String key) {
    controlScheme.handleKeyReleased(key);
  }

  public void handleControllerInputPressed(String key) {
    if (key != null) {
      controlScheme.handleKeyInput(key);
    }
  }
  public void handleControllerInputReleased(String key) {
    if (key != null) {
      controlScheme.handleKeyReleased(key);
    }
  }

  private void limitSpeed(){
    if(Math.abs(xVel) > xVelMax){
      setXVelocity(Math.signum(xVel) * xVelMax);
    }
    if(Math.abs(yVel)>yVelMax){
      setYVelocity(Math.signum(yVel)*yVelMax);
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

  public boolean getBoundedBelow(){
    return boundedBelow;
  }

  public String getEntityID(){
    return this.entityID;
  }

  public void setBoundedBelow(boolean groundStatus){
    this.boundedBelow = groundStatus;
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
    Action updateVelocity = new AbsoluteVelocityX("" + (this.getXVelocity() + newEntity.getModel().getXVelocity()));
    updateVelocity.execute(newEntity.getModel());
  }


  public void spawnAndBind(String param) {
    EntityWrapper newEntity = spawnEntity(param);
    newEntity.getModel().getxProperty().bind(myEntity.getRender().xProperty());
    newEntity.getModel().getyProperty().bind(myEntity.getRender().yProperty());
    newEntity.getModel().getForwardsProperty().bindBidirectional(forwardsProperty);
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

  public void setBoundedLeft(boolean value){boundedLeft = value;}

  public void setBoundedRight(boolean value){boundedRight = value;}

  public boolean getFixed(){return fixedEntity;}

  public void setIsDead(boolean dead) {
    this.isDead = dead;
  }

  public boolean getIsDead() {
    return isDead;
  }

  public void setBoundedTop(boolean value) {boundedTop = value;}

  public boolean isPermeable(){return permeableEntity;}

  public void changeImage(String param) {
    myEntity.changeImage(param);
  }

  public void updateScore(double newScore){myEntity.updateScore(newScore);}

  public double getScore(){return myEntity.getScore();}

  public void setCollidable(boolean parseBoolean) {collidable = parseBoolean;}

  public boolean getCollidable(){return collidable;}

  public void setOpacity(double parseDouble) {myEntity.getRender().setOpacity(parseDouble);}

  public void setPermeable(boolean parseBoolean) {permeableEntity = parseBoolean;}

  public SimpleBooleanProperty getForwardsProperty(){return forwardsProperty;}

  public SimpleDoubleProperty getxProperty(){return xProperty;}

  public SimpleDoubleProperty getyProperty(){return yProperty;}
}

