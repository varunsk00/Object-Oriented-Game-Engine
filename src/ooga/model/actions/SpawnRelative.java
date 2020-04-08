package ooga.model.actions;

import ooga.model.EntityModel;

public class SpawnRelative extends Spawn {
  public SpawnRelative(String parameter) {
    super(parameter);
  }

  @Override
  public void execute(EntityModel entityModel) {
    if(System.currentTimeMillis() - pasttime >= 100) {
      entityModel.spawnRelative(param);
    }
    pasttime = System.currentTimeMillis();
  }
}

