import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract sealed class AbstractTile extends Tile permits GumbaSpawn
{
    final CollisionObject klass;

    AbstractTile(String filename, CollisionObject klass)
    {
        super(0, 0, filename); // Keine Collisions
        this.klass = klass;
    }

    public void addedToWorld(World world)
    {
        if(world.getClass() != LevelEditor.class) {
            world.addObject(klass, 0, 0);
            klass.setX(x);
            klass.setY(y);
            world.removeObject(this);
        }
    }

    public static void registerTiles()
    {
        GumbaSpawn.register();
    }
}
