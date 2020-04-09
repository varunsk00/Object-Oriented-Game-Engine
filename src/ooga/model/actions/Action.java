package ooga.model.actions;

import ooga.model.EntityModel;
import ooga.view.gui.AVManager;

public abstract class Action {
  protected String param;
  protected AVManager soundBoard;
  public Action(String parameter){
    param = parameter;
    soundBoard = new AVManager();
  }
  public abstract void execute(EntityModel entity);
}
