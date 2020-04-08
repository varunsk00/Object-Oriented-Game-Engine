package ooga.model.actions;

import ooga.model.EntityModel;

public class Spawn extends Action {
    protected long pasttime;
    protected long cooldown;

    public Spawn(String parameter) {
        super(parameter);
        pasttime = System.currentTimeMillis();
        cooldown = 100;
    }

    public Spawn(String parameter, String cdown) {
        super(parameter);
        cooldown = Long.parseLong(cdown);
        pasttime = System.currentTimeMillis();
    }

    @Override
    public void execute(EntityModel entityModel) {
        if(System.currentTimeMillis() - pasttime >= cooldown) {
            entityModel.spawnEntity(param);
            pasttime = System.currentTimeMillis();
        }
    }
}
