package ooga.model.actions;

import ooga.model.EntityModel;

public class SetOpacity extends Action {
  private double opacity;

  public SetOpacity(String parameter) {
    super(parameter);
    opacity = Double.parseDouble(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setOpacity(opacity);
  }
}
