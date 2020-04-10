package ooga.model.levels;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ooga.controller.Controller;

public abstract class LevelBuilder {
  protected Pane level;
  protected Controller myController;

  public LevelBuilder(Controller controller){
    myController = controller;
    level = new Pane();
  };

  public Pane generateLevel(){return level;}

  public abstract void updateLevel(Rectangle camera, Pane level);
}
