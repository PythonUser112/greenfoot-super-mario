import greenfoot.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

abstract public class Level extends TextWorld
{
    public int globx = -16;
    public int globy = -16;
    public static final String levelDirectory = "levels/";

    // Kollisionserkennung
    public static final int LAYER_ENVIRONMENT = 1;
    public static final int LAYER_PLAYER      = 2;
    public static final int LAYER_ITEM        = 4;
    public static final int LAYER_ENEMIES     = 8;

    private GreenfootSound currentBackground;
    private int frame = 0;

    public Level(String background)
    {
        super(1024, 600, 1, false, Color.RED);
        currentBackground = new GreenfootSound(background);
        currentBackground.playLoop();
        currentBackground.setVolume(0);
        loadLevel(getClass().getName().toLowerCase());
    }

    public Level()
    {
        super(1024, 600, 1, false, Color.RED);
        setPaintOrder(Mario.class, Tile.class, Item.class);
    }

    public void loadLevel(String level)
    {
        globx = -16;
        globy = -16;
        Tile.registerAll();
        String filename = levelDirectory + level + ".txt";
        Path path = Path.of(filename);
        ArrayList<Tile> objects = new ArrayList();
        try {
            String[] fileContent = Files.readString(path).trim().split("\n");
            for(int i = 0; i < fileContent.length; i++) {
                if(!fileContent[i].isBlank()) {
                    String[] tile = fileContent[i].split(",");
                    int tileX = Integer.parseInt(tile[0].trim()) * 32;
                    int tileY = Integer.parseInt(tile[1].trim()) * 32;
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
        showTitle(10);
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
