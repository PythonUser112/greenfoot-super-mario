import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class LoadButton extends Button
{
    public void action() {
        String level = Greenfoot.ask("Level to load?");
        getWorldOfType(LevelEditor.class).loadLevel(level);
    }
}
