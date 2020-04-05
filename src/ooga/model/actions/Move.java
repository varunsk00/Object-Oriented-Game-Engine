package ooga.model.actions;

import ooga.model.EntityModel;

public class Move extends Action{
    private double XVel;
    private double YVel;
    public Move(double xvel, double yvel){
        this.XVel = xvel;
        this.YVel = yvel;
    }
    @Override
    public void execute(EntityModel entityModel) {
//        entityModel.setXVel(XVel);
//        entityModel.setYVel(YVel);
    }
}
