package ooga.model.actions;

import ooga.model.EntityModel;

public abstract class Action {
  protected String param;
  public Action(String parameter){param = parameter;}

  public abstract void execute(EntityModel entity);
}
