package ooga.model;

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

  @Override
  public boolean equals(Object targetCollisionKey) {
    return(targetCollisionKey instanceof CollisionKey &&
        (this.ID.equals(((CollisionKey) targetCollisionKey).getID()) &&
          (this.orientation.equals(((CollisionKey) targetCollisionKey).getOrientation()) ||
              ((CollisionKey) targetCollisionKey).getOrientation().equals("A"))));
  }
}
