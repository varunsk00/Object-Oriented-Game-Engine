package ooga.controller;

import java.util.ArrayList;
import java.util.List;

public class FinalController {
  private ViewController myViewAPI;
  private List<EntityWrapper> entityList;

  public FinalController(){
    myViewAPI = new ViewController(this);
    entityList = new ArrayList<>();
  }

  public void spawnEntity(String param) {
  }
}
