package ooga.model.actions;

import ooga.model.EntityModel;

public class Spawn extends Action {
    private static final long DEFAULT_COOLDOWN = 100;
    protected long pasttime;
    protected long cooldown;

    public Spawn(String parameter) {
        super(parameter);
        pasttime = System.currentTimeMillis();
        cooldown = DEFAULT_COOLDOWN;
    }

    public Spawn(String parameter, String cdown) {
        super(parameter);
        cooldown = Long.parseLong(cdown);
        pasttime = System.currentTimeMillis();
    }

    @Override
    public void execute(EntityModel entityModel) {
        if(System.currentTimeMillis() - pasttime >= cooldown) {
            soundBoard.playSoundEffect(entityModel.getEntityID());
            entityModel.spawnEntity(param);
            pasttime = System.currentTimeMillis();
        }
    }
}
