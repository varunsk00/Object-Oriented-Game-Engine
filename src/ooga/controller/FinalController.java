package ooga.controller;

import java.util.ArrayList;
import java.util.List;

//FIXME: REMOVE EVENTUALLY; UNUSED
public class FinalController implements Controller {
  private ViewManager myViewAPI;
  private List<EntityWrapper> entityList;

  public FinalController(){
    //myViewAPI = new ViewController(this);
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

  @Override
  public List<EntityWrapper> getEntityList() {
    return null;
  }

  @Override
  public void removeEntity(EntityWrapper node) {

  }
}
