package ooga.model.actions;

import ooga.model.EntityModel;

public class Die extends Action {

  public Die(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    System.out.println("asdfasdf");
    entity.despawnEntity();
  }
}
