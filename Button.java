import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

abstract public class Button extends Actor
{
    public final void act()
    {
        if(Greenfoot.mouseClicked(this)) {
            action();
        }
    }

    abstract void action();
}
