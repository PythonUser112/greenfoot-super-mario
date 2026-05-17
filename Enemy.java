import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

abstract public class Enemy extends MarioObject
{
    static final int collisionLayer = Level.LAYER_ENEMIES;
    static final int collisionMask = Level.LAYER_ENVIRONMENT | Level.LAYER_ENEMIES;

    static final double frameTime = 0.4;
    private double currentTime = 0;
    private int frame = 1;

    private final String baseFilename;
    private int speed;
    private double dy;
    private boolean dying;

    Enemy(String baseFilename, boolean direction, int speed)
    {
        super(collisionLayer, collisionMask);
        this.baseFilename = baseFilename;
        setImage(baseFilename + "1.png");
        setCollisionShape(new BoxShape(this));        
        if(!direction) {
            speed = -speed;
            getImage().mirrorHorizontally();
        }
        this.speed = speed;
    }

    public void die()
    {
        dying = true;
        frame = 0;
        setImage(baseFilename + "die.png");
    }

    @Override
    public final void process(double delta)
    {
        super.process(delta);
        currentTime += delta;
        if(currentTime >= frameTime) {
            if(!dying) {
                currentTime -= frameTime;
                frame = 3 - frame;
                setImage(baseFilename + frame + ".png");
            }
            else {
                if(frame++ == 16) {
                    getWorld().removeObject(this);
                    return;
                }
                if((frame % 2) == 1) {
                    setImage(baseFilename + "die.png");
                }
                else {
                    setImage(baseFilename + "1.png");
                }
            }
        }
        if(!dying) {
            if(isOnGround()) {
                dy = 0;
            }
            else { 
                dy += Level.GRAVITY * delta;
            }
            if(moveAndSlide(speed, (int) Math.round(dy))) {
                speed = -speed;
                getImage().mirrorHorizontally();
                currentTime = 0;
            }
        }else {
        }
    }
}
