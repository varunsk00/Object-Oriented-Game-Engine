package ooga.model;


import java.sql.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.util.Pair;
import ooga.model.actions.Action;
import ooga.model.actions.CollisionKey;
import ooga.model.actions.MoveX;

public class CollisionEngine {

  private static final int ZERO = 0;
  private static final int ONE = 1;
  private static final int TWO = 2;
  private static final int THREE = 3;
  private static final String LEFT = "W";
  private static final String RIGHT = "E";
  private static final String TOP = "N";
  private static final String BOTTOM = "S";

  private static final Map<Integer, String> targetEntitySideMap = Map.ofEntries(
      Map.entry(ZERO, RIGHT),
      Map.entry(ONE, LEFT),
      Map.entry(TWO, BOTTOM),
      Map.entry(THREE, TOP)
      );

  public CollisionEngine() {
    //TODO: do I need a parameter to the constructor?
  }

  public void produceCollisionActions(EntityModel subjectEntity, EntityModel targetEntity) {
    if(!subjectEntity.equals(targetEntity) && detectCollision(subjectEntity, targetEntity)){
      Map<CollisionKey, Action> subjectEntityCollisionMap = subjectEntity.getCollisionMap();

      String targetEntityID = "Brick";//targetEntity.getID(); //FIXME: Change to the method to get a targetEntity ID
      String targetEntityCollisionSide = determineTargetEntityCollisionSide(subjectEntity, targetEntity);

      System.out.println("Collision!");
      System.out.println(targetEntityCollisionSide);
      CollisionKey targetEntityCollisionKey = new CollisionKey(targetEntityID, targetEntityCollisionSide);

      Action collisionAction = subjectEntityCollisionMap.get(targetEntityCollisionKey);
      System.out.println(collisionAction);
      //subjectEntity.getActionStack().push(collisionAction);
      /*if(subjectEntity.getX()%2 == 0) {
        subjectEntity.getActionStack().push(new MoveX("-100"));
      }*/

    }
  }

  private String determineTargetEntityCollisionSide(EntityModel subjectEntity, EntityModel targetEntity){
    double[] subjectLeftRightTopBottom = new double[]{subjectEntity.getX(), subjectEntity.getX() + subjectEntity.getWidth(), subjectEntity.getY(), subjectEntity.getY() + subjectEntity.getHeight()};
    double[] targetRightLeftBottomTop = new double[]{targetEntity.getX() + targetEntity.getWidth(), targetEntity.getX(), targetEntity.getY() + targetEntity.getHeight(), targetEntity.getY()};
    System.out.println("subject " + subjectLeftRightTopBottom[0] + " " + subjectLeftRightTopBottom[1] + " " + subjectLeftRightTopBottom[2] + " " + subjectLeftRightTopBottom[3]);
    System.out.println("target " + targetRightLeftBottomTop[0] + " " + targetRightLeftBottomTop[1] + " " + targetRightLeftBottomTop[2] + " " + targetRightLeftBottomTop[3]);

    int sideIndex = determineSideIndex(subjectLeftRightTopBottom, targetRightLeftBottomTop);

    return targetEntitySideMap.get(sideIndex);
  }

  private int determineSideIndex(double[] subjectSides, double[] targetSides){
    int sideIndex = ZERO;
    double minimumDistance = Math.abs(subjectSides[ZERO] - targetSides[ZERO]);

    for(int i = 0; i < subjectSides.length; i++){
        double currentDistance = Math.abs(subjectSides[i] - targetSides[i]);
        if(currentDistance < minimumDistance){
          minimumDistance = currentDistance;
          sideIndex = i;
      }
    }
    System.out.println(minimumDistance + " " + sideIndex);
    return sideIndex;
  }

  private boolean detectCollision(EntityModel subjectEntity, EntityModel targetEntity){
    return subjectEntity.getX() < targetEntity.getX() + targetEntity.getWidth() &&
        subjectEntity.getX() + subjectEntity.getWidth() > targetEntity.getX() &&
        subjectEntity.getY() < targetEntity.getY() + targetEntity.getHeight() &&
        subjectEntity.getY() + subjectEntity.getHeight() > targetEntity.getY();
  }
}
