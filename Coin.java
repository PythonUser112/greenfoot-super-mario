import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Coin extends Item
{
    static final double frameTime = 0.2;
    private double currentTime = 0;
    private boolean frameDirection = true;
    private int frame = 1;

    @Override
    public final void process(double delta)
    {
        super.process(delta);
        currentTime += delta;
        if(currentTime >= frameTime) {
            int transparency = getImage().getTransparency();
            currentTime -= frameTime;
            if(frameDirection) {
                if(frame++ == 3) {
                    frame = 2;
                    frameDirection = false;
                }
            }
            else {
                if(frame-- == 1) {
                    frame = 2;
                    frameDirection = true;
                }
            }
            setImage("coin" + frame + ".png");
            getImage().setTransparency(transparency);
            getImage().scale(getImage().getWidth() * 2, getImage().getHeight() * 2);
        }
    }
}
