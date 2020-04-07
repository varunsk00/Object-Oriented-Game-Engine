package ooga.model.actions;

import ooga.model.EntityModel;

public class Spawn extends Action {
    public Spawn(String parameter) {
        super(parameter);
    }

    @Override
    public void execute(EntityModel entityModel) {
        entityModel.spawnEntity(param);
    }
}
