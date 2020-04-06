package ooga.model;

import java.util.Stack;
import javafx.scene.input.KeyEvent;
import ooga.controller.EntityWrapper;
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
  private ControlScheme controlScheme;
  private Stack<Action> actionStack;

  public EntityModel(EntityWrapper entityWrapper) {
    myEntity = entityWrapper;
    controlScheme = myEntity.getParser().parseControls();
    actionStack = new Stack<>();
  }

  public void handleKeyInput(KeyEvent event) {
    controlScheme.handleKeyInput(event);
  }

  public void update(double elapsedTime){
    for(Action action : controlScheme.getCurrentAction()){
      actionStack.push(action);
    }
    while(!actionStack.isEmpty()){
      actionStack.pop().execute(this);
    }
    limitSpeed();
    setX(xPos + xVel * elapsedTime);
    setY(yPos + yVel * elapsedTime);
//    System.out.println(xVel);

  }

  private void limitSpeed(){
    if(Math.abs(xVel) > xVelMax){
      setXVelocity(Math.signum(xVel) * xVelMax);
    }
    if(yVel > yVelMax){
      setYVelocity(yVelMax);
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

  public void handleKeyReleased() {
    controlScheme.handleKeyReleased();
  }
}
