package ooga.model.actions;

import ooga.model.EntityModel;

public class Die extends Action {
    private String entityType;
    public Die(String value){
        this.entityType = value;
    }
    @Override
    public void execute(EntityModel entityModel) {
        //entityModel.deleteEntity(entityType);
    }
}
