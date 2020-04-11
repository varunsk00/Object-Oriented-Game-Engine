package ooga.model.actions;

import ooga.model.EntityModel;
import ooga.view.gui.managers.AudioVideoManager;

public abstract class Action {
  protected String param;
  protected AudioVideoManager soundBoard;
  public Action(String parameter){
    param = parameter;
    soundBoard = new AudioVideoManager();
  }
  public abstract void execute(EntityModel entity);

  @Override
  public boolean equals(Object o){
    if(o instanceof Action){
      return(o.getClass().toString().equals(this.getClass().toString()) && ((Action)o).getParam().equals(this.param));
    }
    return false;
  }

  public String getParam(){return param;}
}
