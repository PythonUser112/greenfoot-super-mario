import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;

public abstract sealed class Tile extends CollisionObject permits ConcreteTile {
    public static HashMap<String, Class> subclasses = new HashMap();
    private int fadeFrame = 0;
    private boolean fading = true;
    public final String filename;

    public Tile(int collisionLayer, int collisionMask, String filename)
    {
        super(collisionLayer, collisionMask);
        this.filename = filename;
        setImage(filename);
        getImage().setTransparency(0);
        getImage().scale(getImage().getWidth() * 2, getImage().getHeight() * 2);
        setCollisionShape(new BoxShape(this));
    }

    public static final Tile instanceTile(String klass, int x, int y)
    {
        try {
            Tile instance = (Tile) subclasses.get(klass).newInstance();
            instance.setPosition(x, y);
            return instance;
        } catch(InstantiationException e) {
            System.err.println(e);
        } catch(IllegalAccessException e) {
            System.err.println(e);
        }
        Greenfoot.stop();
        return new Brick();
    }

    public static final void register(Class klass)
    {
        Tile.subclasses.put(klass.getName(), klass);
    }

    public static final void registerAll()
    {
        ConcreteTile.registerTiles();
    }

    public static final boolean hasTile(String klass)
    {
        return subclasses.getOrDefault(klass, null) != null;
    }

    @Override
    public void process(double _delta)
    {
        if(fading && fadeFrame++ < 15) {
            getImage().setTransparency(fadeFrame * 17);
        }
    }
}
