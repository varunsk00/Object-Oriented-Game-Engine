package ooga.model.levels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ooga.controller.Controller;
import ooga.controller.EntityWrapper;
import ooga.model.actions.SetX;
import ooga.model.actions.SetY;

public class InfiniteLevelBuilder extends LevelBuilder {
  private int entityNum=6;
  private int entityLim=24;
  boolean pipespawned = false;
  private ArrayList<EntityWrapper> proceduralEntitiesList;
  public InfiniteLevelBuilder(Controller controller) {
    super(controller);
    proceduralEntitiesList = new ArrayList<>();
  }

  @Override
  public void updateLevel(Rectangle camera, BorderPane level) {
//    Iterator<EntityWrapper> iterator = myController.getEntityList().iterator();
    Iterator<EntityWrapper> iterator = proceduralEntitiesList.iterator();
    while(iterator.hasNext()){
      EntityWrapper entity = iterator.next();
      if(entity.getModel().getX() < camera.getBoundsInParent().getMinX()-(camera.getWidth())) {
//          || entity.getRender().getBoundsInParent().getMinX() > camera.getBoundsInParent().getMaxX()*2+(camera.getWidth()*2)
//          || entity.getRender().getBoundsInParent().getMinY() > camera.getBoundsInParent().getMaxY()*2+(camera.getHeight()*2)
//          || entity.getRender().getBoundsInParent().getMaxY() < camera.getBoundsInParent().getMinY()*2-(camera.getHeight()*2)){
        entityNum -=1;
//        System.out.println(entity.getModel().getX());
//        System.out.println(camera.getBoundsInParent().getMinX()-(camera.getWidth()));
//        System.out.println(entityNum);
        //level.getChildren().remove(entity);
        iterator.remove();
        //myController.getEntityList().remove(entity);
        myController.removeEntity(entity);
        proceduralEntitiesList.remove(entity);
        pipespawned = false;
      }
      }
//      if(entity.getRender().getBoundsInParent().getMinX() > camera.getBoundsInParent().getMaxX()){
//        pipespawned = true;
//        System.out.println("true");
//      }

    if(entityNum < entityLim-4){
      List<EntityWrapper> newPipe = createPipe(camera);
      for(EntityWrapper newPipePiece : newPipe){
        //level.getChildren().add(newPipePiece.getRender());
        myController.addEntity(newPipePiece);
        entityNum++;
      }
    }
  }

  private List<EntityWrapper> createPipe(Rectangle camera){
    //Spawn actions
    EntityWrapper pipeLip1 = new EntityWrapper("Pipelip", myController);
    EntityWrapper pipeLip2 = new EntityWrapper("Pipelip", myController);

    EntityWrapper pipeBody1 = new EntityWrapper("Pipebody", myController);
    EntityWrapper pipeBody2 = new EntityWrapper("Pipebody", myController);

//    double newPipeX = Math.random()*camera.getWidth()+camera.getBoundsInParent().getMaxX();
    double newPipeX = Math.random()*camera.getWidth()+camera.getBoundsInParent().getMaxX();
    double newPipeY = Math.random()*(camera.getHeight()-pipeLip1.getRender().getBoundsInParent().getHeight()*2)+pipeLip1.getRender().getBoundsInParent().getHeight();
    double gapWidth = Math.random()*(camera.getHeight()-newPipeY-pipeLip1.getRender().getBoundsInParent().getHeight())+newPipeY;

    double pipeBodyOffset = newPipeX+(pipeLip1.getRender().getBoundsInParent().getWidth()-pipeBody1.getRender().getBoundsInParent().getWidth())/2;

    pipeLip1.getModel().getActionStack().push(new SetX("" + newPipeX));
    pipeLip1.getModel().getActionStack().push(new SetY("" + (newPipeY-pipeLip1.getRender().getBoundsInParent().getHeight())));
    pipeBody1.getModel().getActionStack().push(new SetX("" + pipeBodyOffset));
//    pipeBody1.getModel().getActionStack().push(new SetY("" + (pipeLip1.getRender().getBoundsInParent().getMinX()-pipeBody1.getRender().getBoundsInParent().getHeight())));
    pipeBody1.getModel().getActionStack().push(new SetY("" + (newPipeY)));
    pipeBody1.setHeight(720-newPipeY);

    pipeLip2.getModel().getActionStack().push(new SetX("" + newPipeX));
    pipeLip2.getModel().getActionStack().push(new SetY("" + (newPipeY-200)));

    pipeBody2.getModel().getActionStack().push(new SetX("" + pipeBodyOffset));
//    pipeBody2.getModel().getActionStack().push(new SetY("" + (pipeLip2.getRender().getBoundsInParent().getMaxX())));
  //  pipeBody2.getModel().getActionStack().push(new SetY("" + (newPipeY-300)));
    List<EntityWrapper> output = new ArrayList<EntityWrapper>();
    pipeLip1.getModel().setX(newPipeX);
    pipeLip1.getModel().setY(newPipeY-pipeLip1.getRender().getBoundsInParent().getHeight());
    pipeLip2.getModel().setX(newPipeX);
    pipeLip2.getModel().setY(newPipeY-200);
    pipeBody1.getModel().setX(pipeBodyOffset);
 //   pipeBody1.getModel().setY(newPipeY);
    pipeBody2.getModel().setX(pipeBodyOffset);
    pipeBody2.getModel().setY(newPipeY-300);
    pipeBody2.getModel().setY(0);
    pipeBody2.setHeight(newPipeY-200);

    output.add(pipeLip1);
    output.add(pipeLip2);
    output.add(pipeBody1);
    output.add(pipeBody2);
    System.out.println("new spawn x");
    System.out.println(newPipeX);
    proceduralEntitiesList.add(pipeLip1);
    proceduralEntitiesList.add(pipeLip2);
    proceduralEntitiesList.add(pipeBody1);
    proceduralEntitiesList.add(pipeBody2);
    return output;
  }
}
