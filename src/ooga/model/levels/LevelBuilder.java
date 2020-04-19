package ooga.model.levels;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import ooga.controller.Controller;

public abstract class LevelBuilder {
  protected BorderPane level;
  protected Controller myController;

  public LevelBuilder(Controller controller){
    myController = controller;
    level = new BorderPane();
  };

  public BorderPane generateLevel(){return level;}

  public abstract void updateLevel(Rectangle camera, BorderPane level);
}
