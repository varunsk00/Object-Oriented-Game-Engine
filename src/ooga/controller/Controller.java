package ooga.controller;

import java.util.ArrayList;
import java.util.List;

public class Controller {
  private ViewController myViewAPI;
  private List<EntityWrapper> entityList;

  public Controller(){
    myViewAPI = new ViewController(this);
    entityList = new ArrayList<>();
  }

}
