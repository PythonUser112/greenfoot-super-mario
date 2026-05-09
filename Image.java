import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public final class Image extends Actor
{
    private boolean fading = false;
    private int fadeTime = 0;;

    public Image(String filename)
    {
        setImage(new GreenfootImage(filename + ".png"));
    }

    public void act()
    {
        if(fading) {
            if(fadeTime++ == 15) {
                getImage().setTransparency(0);
                fading = false;
                return;
            }
            getImage().setTransparency(255 - 17 * fadeTime);
        }
    }

    public boolean isFading()
    {
        return fading;
    }

    public void fade()
    {
        fading = true;
    }
}
