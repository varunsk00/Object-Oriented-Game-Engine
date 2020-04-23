package ooga.model.actions;

import ooga.model.EntityModel;

public class LoseHealth extends Action {

  public LoseHealth(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    System.out.println("I DO BE DYING DOE");
//    entity.loseHealth(Double.parseDouble(param));
  }
}
