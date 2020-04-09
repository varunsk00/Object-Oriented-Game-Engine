package ooga.model;


import java.sql.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.util.Pair;

public class CollisionEngine {

  private static final int ZERO = 0;
  private static final int ONE = 1;
  private static final int TWO = 2;
  private static final int THREE = 3;
  private static final String LEFT = "Left";
  private static final String RIGHT = "Right";
  private static final String TOP = "Top";
  private static final String BOTTOM = "Bottom";

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
      String targetEntityCollisionSide = determineTargetEntityCollisionSide(subjectEntity, targetEntity);


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
    double minimumDistance = subjectSides[ZERO] * targetSides[ZERO];

    for(int i = 0; i < subjectSides.length; i++){
        double currentDistance = subjectSides[i] * targetSides[i];
        if(currentDistance < minimumDistance){
          minimumDistance = currentDistance;
          sideIndex = i;
      }
    }
    return sideIndex;
  }

  private boolean detectCollision(EntityModel subjectEntity, EntityModel targetEntity){
    return subjectEntity.getX() < targetEntity.getX() + targetEntity.getWidth() &&
        subjectEntity.getX() + subjectEntity.getWidth() > targetEntity.getX() &&
        subjectEntity.getY() < targetEntity.getY() + targetEntity.getHeight() &&
        subjectEntity.getY() + subjectEntity.getHeight() > targetEntity.getY();
  }
}
