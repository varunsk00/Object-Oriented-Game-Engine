package ooga.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;

public class TestController {

  private Scene myCurrentScene;
  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  public TestController(Scene testScene){
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

  }

  private void step (double elapsedTime) {

    /* potential update code for Entity
    for (EntityWrapper currentEntity : EntityList) {
      for (EntityWrapper targetEntity : EntityList) {
        if(currentEntity.equals(targetEntity)){
          continue;
        }
        else {
          EntityWrapper.update();
        }
      }
    }
     */
  }


}
