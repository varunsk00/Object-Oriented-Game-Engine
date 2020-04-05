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

  public void update(){
    actionStack.add(controlScheme.getCurrentAction());
    while(!actionStack.isEmpty()){
      actionStack.pop().execute(this);
    }
  }

  public double getX(){return xPos;}

  public double getY(){return yPos;}

  public void setxPos(double xPos)
  { this.xPos = xPos;}

  public void setyVel(double yPos){this.yPos = yPos;}

  public void setXVel(double xVel) {
    this.xVel = xVel;
  }

  public void setYVel(double yVel) {
    this.yVel = yVel;
  }

}
