import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class LevelEditor extends Level
{
    private int dragStartX = -1;
    private int dragStartY = -1;
    private boolean mousePressed;
    private ArrayList<Tile> newTiles = new ArrayList();
    private Tile currentTile;
    private boolean tileReleased = true;

    public LevelEditor()
    {
        super();
        showTitle(10);
        addObject(new LoadButton(), getWidth() / 2 - 60, getHeight() - 30);
        addObject(new ClearButton(), getWidth() / 2, getHeight() - 30);
        addObject(new SaveButton(), getWidth() / 2 + 60, getHeight() - 30);
        Greenfoot.start();
        Tile.registerAll();
        TileSelector.buildAll(this);
    }

    public void action() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse == null) {
            return;
        }
        if(!tileReleased) {
            if(Greenfoot.isKeyDown("escape")) {
                tileReleased = true;
                removeObject(currentTile);
                if(newTiles.size() > 0) {
                    currentTile = newTiles.get(newTiles.size() - 1);
                    newTiles.removeLast();
                }
                else {
                    currentTile = null;
                }
            }
            else if(!Greenfoot.mouseClicked(null)) {
                currentTile.setPosition((int) Math.round((mouse.getX() + globx) / 32.0) * 32, (int) Math.round((mouse.getY() + globy) / 32.0) * 32);
                return;
            }
            else {
                tileReleased = true;
            }
        }
        else {
            if(Greenfoot.isKeyDown("z")) {
                if(currentTile != null) {
                    removeObject(currentTile);
                    if(newTiles.size() > 0) {
                        currentTile = newTiles.get(newTiles.size() - 1);
                        newTiles.removeLast();
                    }
                    else {
                        currentTile = null;
                    }
                    while(Greenfoot.isKeyDown("z"));
                }
            }
        }
        if(Greenfoot.mousePressed(null)) {
            mousePressed = true;
        }
        else if(Greenfoot.mouseClicked(null)) {
            dragStartX = -1;
            dragStartY = -1;
            mousePressed = false;
        }
        if(mousePressed) {
            int x = mouse.getX();
            int y = mouse.getY();
            if(dragStartX > 0) {
                globx -= x - dragStartX;
                globy -= y - dragStartY;
            }
            dragStartX = x;
            dragStartY = y;
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

    public void loadLevel(String level)
    {
        super.loadLevel(level);
        newTiles = new ArrayList();
        currentTile = null;
    }

    public void saveLevel(String level)
    {
        globx = 0;
        globy = 0;
        String filename = levelDirectory + level + ".txt";
        Path path = Path.of(filename);
        ArrayList<Tile> objects = new ArrayList();
        try {
            String content = "";
            java.util.List<Tile> tiles = getObjects(Tile.class);
            for(int i = 0; i < tiles.size(); i++) {
                content += tiles.get(i).x / 16;
                content += ", ";
                content += tiles.get(i).y / 16;
                content += ", ";
                content += tiles.get(i).getClass().getName();
                content += "\n";
            }
            Files.writeString(path, content);
        } catch (IOException e) {
            System.err.print(getClass().getName());
            System.err.println(" has no file!");
            return;
        }
    }

    public void newTile(String name)
    {
        if(currentTile != null) {
            newTiles.addLast(currentTile);
        }
        currentTile = Tile.instanceTile(name, 0, 0);
        addObject(currentTile, Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
        tileReleased = false;
    }
}
