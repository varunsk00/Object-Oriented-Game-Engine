package ooga.model.actions;

import ooga.controller.EntityWrapper;
import ooga.model.EntityModel;

public abstract class Action {
  public abstract void execute(EntityModel entity);
}
