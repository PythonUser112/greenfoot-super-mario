import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SaveButton extends Button
{
    public void action() {
        String level = Greenfoot.ask("Level to save?");
        getWorldOfType(LevelEditor.class).saveLevel(level);
    }
}
