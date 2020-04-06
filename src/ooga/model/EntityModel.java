package ooga.model;

import java.util.Stack;
import javafx.scene.input.KeyEvent;
import ooga.controller.EntityWrapper;
import ooga.model.actions.AccelerateX;
import ooga.model.actions.Action;
import ooga.model.controlschemes.ControlScheme;

public class EntityModel {
  private EntityWrapper myEntity;
  private double xPos;
  private double yPos;
  private double xVel;
  private double yVel;
  private double xVelMax = 100;
  private double yVelMax = 100;
  private boolean onGround = false;
  private ControlScheme controlScheme;
  private Stack<Action> actionStack;

  public EntityModel(EntityWrapper entityWrapper) {
    myEntity = entityWrapper;
    controlScheme = myEntity.getParser().parseControls();
    actionStack = new Stack<>();
  }

  public void update(double elapsedTime){
    //TODO: change this ground status checker to be implemented in collisions with the top of a block
    checkGroundStatus();

    //FIXME: BUG
    // - Description: Pressing down D or A and then pressing another key will stop acceleration until D or A is pressed again
    // - Replication: Hold down D and then press another key while holding down D. Acceleration will stop even if D is still pressed down
    // - Comments: Use sout(xVel) for debugging
    for(Action action : controlScheme.getCurrentAction()){
      actionStack.push(action);
    }
    while(!actionStack.isEmpty()){
      actionStack.pop().execute(this);
    }
    limitSpeed();
    setX(xPos + xVel * elapsedTime);
    setY(yPos + yVel * elapsedTime);
//    System.out.println(getY());

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
//    if(yVel > yVelMax){
//      setYVelocity(yVelMax);
//    }
  }

  private void checkGroundStatus(){
    if(getY() < 200 /* 300 - this.getHeight()*/){
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

  public double getXVelocity(){return xVel;}

  public double getYVelocity(){return yVel;}

  public void setXVelocity(double newXVelocity){xVel = newXVelocity;}

  public void setYVelocity(double newYVelocity){yVel = newYVelocity;}

  public boolean isOnGround(){
    return onGround;
  }

  public void setOnGround(boolean groundStatus){
    onGround = groundStatus;
  }

  public Stack<Action> getActionStack() {
    return actionStack;
  }

  public void spawnEntity(String param) {myEntity.spawnEntity(param);}
}
