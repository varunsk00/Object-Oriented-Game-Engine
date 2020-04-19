package ooga.model.actions;

import ooga.model.EntityModel;

public class InfiniteJump extends Jump{
    protected long pasttime;
    protected long cooldown;
    private double yVelocity;

    public InfiniteJump(String parameter){
        super(parameter);
        yVelocity = Double.parseDouble(param);
    }

    public InfiniteJump(String parameter, String cdown) {
        super(parameter);
        cooldown = Long.parseLong(cdown);
        pasttime = System.currentTimeMillis();
    }

    @Override
    public void execute(EntityModel entity) {
        if(System.currentTimeMillis() - pasttime >= cooldown) {
            soundBoard.playSoundEffect(entity.getEntityID() + "_Jump");
            entity.setYVelocity(yVelocity);
            entity.setBoundedBelow(false);
        }
    }
}
