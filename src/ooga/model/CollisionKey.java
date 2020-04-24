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
    if (targetCollisionKey.getClass().equals(this.getClass())) {
      return (this.ID.equals(((CollisionKey) targetCollisionKey).getID()) &&
          this.orientation.equals(((CollisionKey) targetCollisionKey).getOrientation())
          || (this.ID.equals(((CollisionKey) targetCollisionKey).getID())) &&
          ((CollisionKey) targetCollisionKey).getOrientation().equals("A"));
    }
    else {
      return false;
    }
  }
}
