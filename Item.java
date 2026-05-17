import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

abstract public class Item extends MarioObject
{
    static final int collisionLayer = Level.LAYER_ITEM;
    static final int collisionMaskWithoutEnv = Level.LAYER_PLAYER;
    static final int collisionMaskWithEnv = Level.LAYER_PLAYER | Level.LAYER_ENVIRONMENT;
    private int fadeFrame = 0;
    private boolean fading = true;
    private boolean dropoff = true;

    public Item(boolean collidesWithEnvironment)
    {
        super(collisionLayer, collidesWithEnvironment ? collisionMaskWithEnv : collisionMaskWithoutEnv);
        getImage().setTransparency(0);
    }

    public void dropOff()
    {
        dropoff = true;
    }

    @Override
    public void process(double delta)
    {
        if(fading && fadeFrame++ < 15) {
            getImage().setTransparency(fadeFrame * 17);
        }
        if(dropoff && fadeFrame++ < 15) {
            setY(getY() - Level.TILE_SCALE);
        }
        if(fadeFrame == 15) {
            dropoff = false;
            fading = false;
            fadeFrame = 0;
        }
    }
}
