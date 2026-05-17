import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Coin extends Item
{
    static final double frameTime = 0.2;
    private double currentTime = 0;
    private int frame = 1;

    @Override
    public final void process(double delta)
    {
        super.process(delta);
        currentTime += delta;
        if(currentTime >= frameTime) {
            currentTime -= frameTime;
            if(frame++ == 3) {
                frame = 1;
            }
            setImage("coin" + frame + ".png");
        }
    }
}
