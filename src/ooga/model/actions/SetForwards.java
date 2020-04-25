package ooga.model.actions;

import javafx.beans.property.BooleanProperty;
import ooga.model.EntityModel;

public class SetForwards extends Action {
  private boolean forwards;

  public SetForwards(String parameter) {
    super(parameter);
    forwards = Boolean.parseBoolean(param);
  }

  @Override
  public void execute(EntityModel entity) {
    entity.setForwards(forwards);
  }
}
