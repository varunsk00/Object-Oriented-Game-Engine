package ooga.controller;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyEvent;
import ooga.view.EntityView;

public class Controller {
  private ViewController myViewAPI;
  private List<EntityWrapper> entityList;

  public Controller(){
    myViewAPI = new ViewController(this);
    entityList = new ArrayList<>();
  }

}
