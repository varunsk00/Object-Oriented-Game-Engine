package ooga.model.levels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ooga.controller.Controller;
import ooga.controller.EntityWrapper;
import ooga.model.actions.SetX;
import ooga.model.actions.SetY;

public class InfiniteLevelBuilder extends LevelBuilder {

  public InfiniteLevelBuilder(Controller controller) {
    super(controller);
  }

  @Override
  public void updateLevel(Rectangle camera, Pane level) {
    boolean pipespawned = false;
    Iterator<EntityWrapper> iterator = myController.getEntityList().iterator();
    while(iterator.hasNext()){
      EntityWrapper entity = iterator.next();
      if(entity.getRender().getBoundsInParent().getMaxX() < camera.getBoundsInParent().getMinX()-(camera.getWidth()*2)
          || entity.getRender().getBoundsInParent().getMinX() > camera.getBoundsInParent().getMaxX()*2+(camera.getWidth()*2)
          || entity.getRender().getBoundsInParent().getMinY() > camera.getBoundsInParent().getMaxY()*2+(camera.getHeight()*2)
          || entity.getRender().getBoundsInParent().getMaxY() < camera.getBoundsInParent().getMinY()*2-(camera.getHeight()*2)){
//        level.getChildren().remove(entity);
//        iterator.remove();
      }
      if(entity.getRender().getBoundsInParent().getMinX() > camera.getBoundsInParent().getMaxX()){
        pipespawned = true;
      }
    }
    if(!pipespawned){
      List<EntityWrapper> newPipe = createPipe(camera);
      for(EntityWrapper newPipePiece : newPipe){
        level.getChildren().add(newPipePiece.getRender());
        myController.addEntity(newPipePiece);
      }
    }
  }

  private List<EntityWrapper> createPipe(Rectangle camera){
    //Spawn actions
    EntityWrapper pipeLip1 = new EntityWrapper("Pipelip", myController);
    EntityWrapper pipeLip2 = new EntityWrapper("Pipelip", myController);
//    EntityWrapper pipeBody1 = new EntityWrapper("Pipebody", myController);
//    EntityWrapper pipeBody2 = new EntityWrapper("Pipebody", myController);

    double newPipeX = Math.random()*camera.getWidth()+camera.getBoundsInParent().getMaxX();
    double newPipeY = Math.random()*(camera.getHeight()-pipeLip1.getRender().getBoundsInParent().getHeight()*2)+pipeLip1.getRender().getBoundsInParent().getHeight();
    double gapWidth = Math.random()*(camera.getHeight()-newPipeY-pipeLip1.getRender().getBoundsInParent().getHeight())+newPipeY;
//    double pipeBodyOffset = newPipeX+(pipeLip1.getRender().getBoundsInParent().getWidth()-pipeBody1.getRender().getBoundsInParent().getWidth())/2;

//    System.out.println(newPipeX + "\t" + newPipeY);

    pipeLip1.getModel().getActionStack().push(new SetX("" + newPipeX));
    pipeLip1.getModel().getActionStack().push(new SetY("" + (newPipeY-pipeLip1.getRender().getBoundsInParent().getHeight())));

//    pipeBody1.getModel().getActionStack().push(new SetX("" + pipeBodyOffset));
//    pipeBody1.getModel().getActionStack().push(new SetY("" + (pipeLip1.getRender().getBoundsInParent().getMinX()-pipeBody1.getRender().getBoundsInParent().getHeight())));

    pipeLip2.getModel().getActionStack().push(new SetX("" + newPipeX));
    pipeLip2.getModel().getActionStack().push(new SetY("" + (newPipeY+gapWidth)));

//    pipeBody2.getModel().getActionStack().push(new SetX("" + pipeBodyOffset));
//    pipeBody2.getModel().getActionStack().push(new SetY("" + (pipeLip2.getRender().getBoundsInParent().getMaxX())));

    List<EntityWrapper> output = new ArrayList<EntityWrapper>();
    output.add(pipeLip1);
    output.add(pipeLip2);
//    output.add(pipeBody1);
//    output.add(pipeBody2);
    return output;
  }
}
