import greenfoot.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

abstract public class Level extends TextWorld
{
    public static final String levelDirectory = "levels/";

    // Kollisionserkennung
    public static final int LAYER_ENVIRONMENT = 1;
    public static final int LAYER_PLAYER      = 2;
    public static final int LAYER_ITEM        = 4;
    public static final int LAYER_ENEMIES     = 8;

    // Skalierung
    public static final int SELECTOR_SCALE = 1;
    public static final int TILE_SCALE = 3;

    // Bewegungsverhalten
    public static final int GRAVITY = 4 * TILE_SCALE; // 4 Pixel PRO SEKUNDE IM QUADRAT!!!
    public static final int MOVESPEED = 64 * TILE_SCALE; // 4 Blöcke / 64 Pixel PRO SEKUNDE!!!
    public static final int JUMPHEIGHT = 2 * TILE_SCALE;

    private GreenfootSound currentBackground;
    private int frame = 0;
    public int globx = -8 * TILE_SCALE;
    public int globy = -8 * TILE_SCALE;

    private int newx = globx;
    private int newy = globy;

    public Level(String background)
    {
        super(1024, 600, 1, false, Color.RED);
        currentBackground = new GreenfootSound(background);
        currentBackground.playLoop();
        currentBackground.setVolume(0);
        loadLevel(getClass().getName().toLowerCase());
        addObject(new Mario(), 0, 0);
        Greenfoot.start();
    }

    public Level()
    {
        super(1024, 600, 1, false, Color.RED, false, 50, 1000 / 24);
        setPaintOrder(Mario.class, Tile.class, Item.class);
        setActOrder(Mario.class, Enemy.class);
    }

    public void restart()
    {
        for(Actor actor : getObjects(MarioObject.class)) {
            removeObject(actor);
        }
        frame = 0;
        loadLevel(getClass().getName().toLowerCase());
        addObject(new Mario(), 0, 0);
    }

    public void loadLevel(String level)
    {
        globx = -8 * TILE_SCALE;
        globy = -8 * TILE_SCALE;
        Tile.registerAll();
        String filename = levelDirectory + level + ".txt";
        Path path = Path.of(filename);
        ArrayList<Tile> objects = new ArrayList();
        try {
            String[] fileContent = Files.readString(path).trim().split("\n");
            for(int i = 0; i < fileContent.length; i++) {
                if(!fileContent[i].isBlank()) {
                    String[] tile = fileContent[i].split(",");
                    int tileX = Integer.parseInt(tile[0].trim()) * 16 * TILE_SCALE;
                    int tileY = Integer.parseInt(tile[1].trim()) * 16 * TILE_SCALE;
                    String tileName = tile[2].trim();
                    if(Tile.hasTile(tileName)) {
                        Tile instance = Tile.instanceTile(tileName, tileX, tileY);
                        addObject(instance, 0, 0);
                    }
                    else {
                        System.err.print("No such tile: ");
                        System.err.print(tileName);
                        System.err.print(" (File ");
                        System.err.print(filename);
                        System.err.print(", Line ");
                        System.err.print(i);
                        System.err.println(")");
                    }
                }
            }
        } catch (IOException e) {
            System.err.print(getClass().getName());
            System.err.println(" has no file!");
            return;
        }
        showTitle(20);
    }

    @Override
    public void action()
    {
        globx = newx;
        globy = newy;
    }

    public void reposition(int x, int y)
    {
        newx = x - getWidth() / 2;
        newy = y - getHeight() / 2;
    }

    public void titleTransition()
    {
        frame++;
        if(currentBackground != null) {
            currentBackground.setVolume(4 * frame);
        }
    }

    void instanceLabel(Label label)
    {
        label.startGrow();
    }

    void disposeLabel(Label label)
    {
        label.startShrink();
    }

    void instanceTitle(Label title)
    {
        title.startGrow();
    }

    void disposeTitle(Label title)
    {
        title.startShrink();
    }
}
