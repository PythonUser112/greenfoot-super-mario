import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public final class Brick extends ConcreteTile {
    static final int collisionLayer = Level.LAYER_ENVIRONMENT;
    static final int collisionMask = Level.LAYER_PLAYER | Level.LAYER_ENEMIES | Level.LAYER_ITEM;
    static final String filename = "platform-brick.png";

    public Brick()
    {
        super(collisionLayer, collisionMask, filename);
    }

    public static void register() {
        Tile.register(Brick.class);
    }
}
