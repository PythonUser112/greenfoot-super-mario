import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Gumba extends Enemy
{
    static final int speed = 1;

    Gumba(boolean direction, boolean underground)
    {
        super("gumba-" + (underground ? "blue-" : ""), direction, speed);
    }
}
