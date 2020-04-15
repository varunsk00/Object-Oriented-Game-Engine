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
  private static final String DEFAULT = "DEFAULT";
  private static final double COLLISION_THRESHOLD = 0.0001;

  private static final Map<Integer, String> targetEntitySideMap = Map.ofEntries(
      Map.entry(ZERO, RIGHT),
      Map.entry(ONE, LEFT),
      Map.entry(TWO, BOTTOM),
      Map.entry(THREE, TOP)
      );

  public CollisionEngine() {
    //TODO: do I need a parameter to the constructor?
    //if this doesnt need to construct anything, consider making it static? -- alex
  }

  public void produceCollisionActions(EntityModel subjectEntity, EntityModel targetEntity) {
    Map<CollisionKey, Action> subjectEntityCollisionMap = subjectEntity.getCollisionMap();
    if (!subjectEntity.equals(targetEntity) && detectCollision(subjectEntity, targetEntity)) {

      String targetEntityID = targetEntity.getEntityID();
      String targetEntityCollisionSide = determineTargetEntityCollisionSide(subjectEntity,
          targetEntity);

      CollisionKey targetEntityCollisionKey = new CollisionKey(targetEntityID,
          targetEntityCollisionSide);
//      System.out.println("Subject: " + subjectEntity.getEntityID() + "-----------" + "Target: " + targetEntityID);
      for (CollisionKey collisionMapKey : subjectEntityCollisionMap.keySet()) {
        if (targetEntityCollisionKey.equals(collisionMapKey)) {
          Action collisionAction = subjectEntityCollisionMap.get(collisionMapKey);
          collisionAction.execute(subjectEntity);
        }
      }
    } else {
      //TODO: Add a try catch in case something doesn't have a Default noncollision
      CollisionKey noCollisionKey = new CollisionKey(DEFAULT, DEFAULT);
      for (CollisionKey collisionMapKey : subjectEntityCollisionMap.keySet()) {
        if (noCollisionKey.equals(collisionMapKey)) {
          Action collisionAction = subjectEntityCollisionMap.get(collisionMapKey);
          subjectEntity.getActionStack().push(collisionAction);//.execute(subjectEntity);
        }
      }
    }
  }

  private String determineTargetEntityCollisionSide(EntityModel subjectEntity, EntityModel targetEntity){
    double[] subjectLeftRightTopBottom = new double[]{subjectEntity.getX(), subjectEntity.getX() + subjectEntity.getWidth(), subjectEntity.getY(), subjectEntity.getY() + subjectEntity.getHeight()};
    double[] targetRightLeftBottomTop = new double[]{targetEntity.getX() + targetEntity.getWidth(), targetEntity.getX(), targetEntity.getY() + targetEntity.getHeight(), targetEntity.getY()};

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
    return sideIndex;
  }

  private boolean detectCollision(EntityModel subjectEntity, EntityModel targetEntity){
    return subjectEntity.getX() - (targetEntity.getX() + targetEntity.getWidth()) < COLLISION_THRESHOLD  &&
        targetEntity.getX() - (subjectEntity.getX() + subjectEntity.getWidth()) < COLLISION_THRESHOLD &&
        subjectEntity.getY() - (targetEntity.getY() + targetEntity.getHeight()) < COLLISION_THRESHOLD &&
        targetEntity.getY() - (subjectEntity.getY() + subjectEntity.getHeight()) < COLLISION_THRESHOLD;
  }
}
