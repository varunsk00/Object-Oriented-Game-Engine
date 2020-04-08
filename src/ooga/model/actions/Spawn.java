package ooga.model.actions;

import ooga.model.EntityModel;

public class Spawn extends Action {
    protected long pasttime;

    public Spawn(String parameter) {
        super(parameter);
        pasttime = System.currentTimeMillis();
    }

    @Override
    public void execute(EntityModel entityModel) {
        if(System.currentTimeMillis() - pasttime >= 500) {
            entityModel.spawnEntity(param);
        }
        pasttime = System.currentTimeMillis();
    }
}
