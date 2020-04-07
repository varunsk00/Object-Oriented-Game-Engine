package ooga.model;

import java.util.HashMap;
import java.util.Map;
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
  private Map<String, Action> myActions;

  public EntityModel(EntityWrapper entityWrapper) throws Exception {
    myEntity = entityWrapper;
    controlScheme = myEntity.getParser().parseControls();
    actionStack = new Stack<>();
    myActions = new HashMap<String, Action>();
  }

  public void handleKeyInput(KeyEvent event) {
    controlScheme.handleKeyInput(event);
  }

  public void update(){
    for(Action action : controlScheme.getCurrentAction()){
      actionStack.push(action);
    }
    while(!actionStack.isEmpty()){
      actionStack.pop().execute(this);
    }
  }

  public double getX(){return xPos;}

  public double getY(){return yPos;}


  public void setX(double newX){xPos = newX;}

  public void setY(double newY){yPos = newY;}

  public void handleKeyReleased() {
    controlScheme.handleKeyReleased();
  }

  public void addAction(String key, Action action) {
    myActions.put(key, action);
  }
}
