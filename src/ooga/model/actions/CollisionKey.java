package ooga.model.actions;

public class CollisionKey {

  private String ID;
  private String orientation;

  public CollisionKey(String id, String orient) {
    this.ID = id;
    this.orientation = orient;
  }

  public String getID() {
    return ID;
  }

  public String getOrientation() {
    return orientation;
  }

  public boolean equals(CollisionKey targetCollisionKey){
    return this.ID.equals(targetCollisionKey.getID()) && this.orientation.equals(targetCollisionKey.getOrientation());
  }
}
