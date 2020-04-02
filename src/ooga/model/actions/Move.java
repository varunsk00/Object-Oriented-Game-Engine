package ooga.model.actions;

public class Move extends Action{
    private double XVel;
    private double YVel;
    public Move(double xvel, double yvel){
        this.XVel = xvel;
        this.YVel = yvel;
    }
    @Override
    public void execute() {
        //EntityModel.setXVel(XVel);
        //EntityModel.setYVel(YVel);
    }
}
