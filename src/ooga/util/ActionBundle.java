package ooga.util;

import java.util.ArrayList;
import java.util.List;
import ooga.model.actions.Action;

public class ActionBundle {
  private List<Action> actionList;
  private String myId;

  public ActionBundle(){
    myId = "";
    actionList = new ArrayList<>();
  }

  //list actions
  //id (probability, duration, keybind)

  public void addAction(Action newAction){actionList.add(newAction);}

  public List<Action> getActions(){return actionList;}

  public void setId(String newID){myId = newID;}

  public String getId(){return myId;}
}
