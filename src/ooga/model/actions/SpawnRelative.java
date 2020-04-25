package ooga.model.actions;

import ooga.model.EntityModel;

public class SpawnRelative extends Spawn {
  public SpawnRelative(String parameter) {
    super(parameter);
  }

  public SpawnRelative(String parameter, String cdown){
    super(parameter, cdown);
  }

  @Override
  public void execute(EntityModel entityModel) {
    if(System.currentTimeMillis() - pasttime >= cooldown) {
      soundBoard.playSoundEffect(param);
      entityModel.spawnRelative(param);
      pasttime = System.currentTimeMillis();
    }
  }
}

