package ooga.model.actions;

import ooga.model.EntityModel;

public class ChangeImage extends Action{


  public ChangeImage(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.changeImage(param);
  }
}
