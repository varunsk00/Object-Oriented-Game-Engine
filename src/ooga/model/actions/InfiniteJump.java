package ooga.model.actions;

import ooga.model.EntityModel;

public class InfiniteJump extends Jump{
    private double yVelocity;

    public InfiniteJump(String parameter){
        super(parameter);
        yVelocity = Double.parseDouble(param);
    }

    @Override
    public void execute(EntityModel entity) {
        entity.setYVelocity(yVelocity);
        soundBoard.playSoundEffect(param);
        entity.setOnGround(false);

    }
}
