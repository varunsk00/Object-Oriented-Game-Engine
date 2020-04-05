package ooga.controller;

import apis.view.ViewExternalAPI;
import javafx.scene.input.KeyEvent;

public class ViewController implements ViewExternalAPI {
  private Controller myController;

  public ViewController(Controller controller){
    myController = controller;
  }

  @Override
  public void updateEntityPosition(int id, double newx, double newy) {

  }

  @Override
  public void removeEntity(int id) {

  }

  @Override
  public void addEntity() {

  }

  @Override
  public void updateEntity(int id, String newValue) {

  }

}
