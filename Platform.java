import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public final class Platform extends ConcreteTile {
    static final int collisionLayer = Level.LAYER_ENVIRONMENT;
    static final int collisionMask = Level.LAYER_PLAYER | Level.LAYER_ENEMIES | Level.LAYER_ITEM;
    static final String filename = "platform-air.png";

    public Platform()
    {
        super(collisionLayer, collisionMask, filename);
    }

    public static void register()
    {
        Tile.register(Platform.class);
    }
}
