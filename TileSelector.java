import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class TileSelector extends Button
{
    final String tilename;

    private TileSelector(World world, String tilename)
    {
        this.tilename = tilename;
        Tile tile = Tile.instanceTile(tilename, 0, 0);
        setImage(tile.filename);
        getImage().scale(getImage().getWidth() * Level.SELECTOR_SCALE, getImage().getHeight() * Level.SELECTOR_SCALE);
    }

    public static void buildTileSelector(World world, String tilename, int height)
    {
        TileSelector selector = new TileSelector(world, tilename);
        world.addObject(selector, world.getWidth() - 16 * Level.SELECTOR_SCALE, height);
    }

    public static void buildAll(World world)
    {
        int length = Tile.subclasses.keySet().size() + 1;
        int i = 1;
        for (String tile : Tile.subclasses.keySet()) {
            buildTileSelector(world, tile, (i++ * world.getHeight() / length));
        }
    }

    public void action()
    {
        getWorldOfType(LevelEditor.class).newTile(tilename);
    }
}
