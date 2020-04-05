package ooga.model.actions;

import ooga.model.EntityModel;

public class Move implements Action{
    private double XVel;
    private double YVel;

    public Move(double xvel, double yvel){
        this.XVel = xvel;
        this.YVel = yvel;
    }
    @Override
    public Action execute(EntityModel entityModel) {
        entityModel.setXVel(XVel);
        entityModel.setYVel(YVel);
        return null; //TODO: hook up to action interface and action factory
    }

    @Override
    public double returnValue() {
        return 0;
    }
}
