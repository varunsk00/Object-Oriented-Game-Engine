package ooga.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FinalController implements Controller {
  private ViewController myViewAPI;
  private List<EntityWrapper> entityList;

  public FinalController(){
    myViewAPI = new ViewController(this);
    entityList = new ArrayList<>();
  }

  private void step(double elapsedTime){
    for(EntityWrapper entity : entityList){
      entity.update(elapsedTime);
    }
  }
  
  @Override
  public void addEntity(EntityWrapper newEntity) {
    entityList.add(newEntity);
  }
}
