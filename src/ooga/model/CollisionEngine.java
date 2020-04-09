package ooga.model;



public class CollisionEngine {

  public CollisionEngine() {
    //TODO: do I need a parameter to the constructor?
  }

  public void produceCollisionActions(EntityModel subjectEntity, EntityModel targetEntity) {
    if(!subjectEntity.equals(targetEntity) && detectCollision(subjectEntity, targetEntity)){
      String collisionSide = determineCollisionSide(subjectEntity, targetEntity);
    }
  }

  private String determineCollisionSide(EntityModel subjectEntity, EntityModel targetEntity){
    return "sTreing";
  }

  private boolean detectCollision(EntityModel subjectEntity, EntityModel targetEntity){
    return subjectEntity.getX() < targetEntity.getX() + targetEntity.getWidth() &&
        subjectEntity.getX() + subjectEntity.getWidth() > targetEntity.getX() &&
        subjectEntity.getY() < targetEntity.getY() + targetEntity.getHeight() &&
        subjectEntity.getY() + subjectEntity.getHeight() > targetEntity.getY();
  }
}
