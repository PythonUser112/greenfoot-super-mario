import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public sealed class ConcreteTile extends Tile permits Brick, Ground, Platform, QuestionBlock
{
    public ConcreteTile(int collisionLayer, int collisionMask, String filename)
    {
        super(collisionLayer, collisionMask, filename);
    }

    public static void registerTiles()
    {
        Brick.register();
        Ground.register();
        Platform.register();
        QuestionBlock.register();
    }

    public static void register() {
        System.err.println("Some 'Tile' has no register function!");
        Greenfoot.stop();
    }
}
