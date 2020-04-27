package ooga.model.actions;

import ooga.model.EntityModel;

public class LoseHealth extends Action {

  private int health;

  public LoseHealth(String parameter) {
    super(parameter);
    health = Integer.parseInt(parameter);
  }

  @Override
  public void execute(EntityModel entity) {entity.loseHealth(health);}
}
