import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public sealed class GumbaSpawn extends AbstractTile permits GumbaLeftSpawn, GumbaRightSpawn, GumbaBlueLeftSpawn, GumbaBlueRightSpawn
{
    GumbaSpawn(boolean direction, boolean underground)
    {
        super("gumba-" + (underground ? "blue-" : "")  + (direction ? 2 : 1) + ".png", new Gumba(direction, underground));
    }

    public static void register()
    {
        Tile.register(GumbaLeftSpawn.class);
        Tile.register(GumbaRightSpawn.class);
        Tile.register(GumbaBlueLeftSpawn.class);
        Tile.register(GumbaBlueRightSpawn.class);
    }
}
