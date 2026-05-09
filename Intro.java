import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Intro extends World
{
    private boolean change = false;
    private Image title;
    private GreenfootSound background;
    private int frame = 0;

    public Intro()
    {
        super(1024, 600, 1);
        Greenfoot.start();
        title = new Image("title");
        addObject(title, getWidth() / 2, getHeight() / 2);
        background = new GreenfootSound("title.wav");
        background.setVolume(45);
        background.playLoop();
        Greenfoot.setSpeed(40);
    }

    public void act()
    {
        if(change) {
            if(!title.isFading()) {
                background.stop();
                if(Greenfoot.isKeyDown("Escape")) {
                    Greenfoot.setWorld(new LevelEditor());
                }
                else {
                    Greenfoot.setWorld(new Level1());
                }
            }
            else {
                frame++;
                background.setVolume(45 - 3 * frame);
            }
        }
        if(Greenfoot.isKeyDown("space")) {
            change = true;
            title.fade();
        }
    }
}
