import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

abstract public class Item extends CollisionObject
{
    static final int collisionLayer = Level.LAYER_ITEM;
    static final int collisionMask = Level.LAYER_PLAYER | Level.LAYER_ENVIRONMENT;
    private int fadeFrame = 0;
    private boolean fading = true;

    public Item()
    {
        super(collisionLayer, collisionMask);
        getImage().setTransparency(0);
        getImage().scale(getImage().getWidth() * 2, getImage().getHeight() * 2);
        setCollisionShape(new BoxShape(this));
    }

    public void dropOff()
    {
        
    }

    @Override
    public void process(double delta)
    {
        if(fading && fadeFrame++ < 15) {
            getImage().setTransparency(fadeFrame * 17);
        }
    }
}
