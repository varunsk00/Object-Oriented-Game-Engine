package ooga.model.actions;

import ooga.model.EntityModel;

public class SpawnAndBind extends Spawn {
  public SpawnAndBind(String parameter) {
    super(parameter);
  }

  public SpawnAndBind(String parameter, String cdown){
    super(parameter, cdown);
  }

  @Override
  public void execute(EntityModel entityModel) {
    if(System.currentTimeMillis() - pasttime >= cooldown) {
      soundBoard.playSoundEffect(param);
      entityModel.spawnAndBind(param);
      pasttime = System.currentTimeMillis();
    }
  }
}
