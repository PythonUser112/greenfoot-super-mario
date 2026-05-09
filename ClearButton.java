import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class ClearButton extends Button
{
    public void action() {
        getWorld().removeObjects(getWorld().getObjects(Tile.class));
        getWorldOfType(Level.class).globx = -8;
        getWorldOfType(Level.class).globy = -8;
    }
}
