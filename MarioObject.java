import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class MarioObject extends CollisionObject
{
    public MarioObject(int collisionLayer, int collisionMask)
    {
        super(collisionLayer, collisionMask);
        getImage().scale(getImage().getWidth() * Level.TILE_SCALE, getImage().getHeight() * Level.TILE_SCALE);
        setCollisionShape(new BoxShape(this));
    }

    public void setImage(String filename)
    {
        int transparency = getImage().getTransparency();
        super.setImage(filename);
        getImage().scale(getImage().getWidth() * Level.TILE_SCALE, getImage().getHeight() * Level.TILE_SCALE);
        getImage().setTransparency(transparency);
    }
}
