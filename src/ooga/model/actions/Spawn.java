package ooga.model.actions;

import ooga.model.EntityModel;

public class Spawn extends Action {
    private String entityType;
    public Spawn(String value){
        this.entityType = value;
    }
    @Override
    public void execute(EntityModel entityModel) {
        //entityModel.spawnEntity(entityType);
    }
}
