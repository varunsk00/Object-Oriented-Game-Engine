package ooga.model.controlschemes;

import ooga.model.actions.Action;

public abstract class ControlScheme {
  protected Action currentAction;

  public abstract Action getCurrentAction();
}
