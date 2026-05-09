import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Mario extends CollisionObject
{
    static final int collisionLayer = Level.LAYER_PLAYER;
    static final int collisionMask = Level.LAYER_ENVIRONMENT | Level.LAYER_ENEMIES | Level.LAYER_ITEM;

    public Mario()
    {
        super(collisionLayer, collisionMask);
        getImage().scale(32, 32);
    }
}
