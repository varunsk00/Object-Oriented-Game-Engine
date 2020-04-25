package ooga.model.actions;

import javafx.beans.property.BooleanProperty;
import ooga.model.EntityModel;

public class SetBoundedTop extends Action {
  private boolean boundedTop;

  public SetBoundedTop(String parameter) {
    super(parameter);
    boundedTop = Boolean.parseBoolean(param);
  }

  @Override
  public void execute(EntityModel entity) { entity.setBoundedTop(boundedTop); }
}
